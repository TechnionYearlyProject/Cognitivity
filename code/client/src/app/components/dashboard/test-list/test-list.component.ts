import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Test } from '../../../models'
import { TestService, TestManagerService } from '../../../services/database-service'
import { AuthService } from '../../../services/auth-service';




@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.css']
})

/*
this component holds all the tests created in the system.
*/
export class TestListComponent implements OnInit {
  //the actual list of the tests objects.
  testList: Test[];
  filteredTestList: Test[];
  filter = {
    option: '',
    text: ''
  }
  //an object to represent the current manager. it hold the current logged in user's credentials.
  email;
  managerId;
  //default constructor.
  constructor(
    private testService: TestService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private tmService: TestManagerService
  ) {}

  //default ngOnInit function, gets the user's credentials while initialized.
  async ngOnInit() {
    try {
      this.email = this.authService.getCurrentManagerEmail();
      this.managerId = await this.tmService.getManagerId(this.email);
      this.testList = await this.testService.findTestsForTestManager(this.managerId);
      this.filteredTestList = [];
      this.testList.forEach((test) => {
          this.filteredTestList.push(test);
      });

    } catch(err) {
      console.log(err);
    }
  }

  /*
  Input - number of a specific test in the tests list.
  Output - returns the status of the test.
  */
  showStatus(statusCode: number) {
    switch(statusCode) {
      case 0: return 'Active';
      case 1: return 'Inactive';
    }
  }

  /*
  Input - the index of the test from the tests list.
  Output - send a color that'll match the active or inactive status of the test.
  */
  getColorForStatus(statusCode: number) {
    switch(statusCode) {
      case 0: return 'green';
      case 1: return 'red';
    }
  }

  /*
  Input - none. called by a click event from the HTML form.
  Output - adds a new test object to the tests list.
  */
  async addTest() {
   this.router.navigate(['/create-test']);
  }

  convertToDateString(date: string) {
    return new Date(date).toLocaleDateString();
  }

  async deleteTest(id: number) {
    if (confirm('Are you sure you want to delete the test?')) {
      console.log('deleted');
      console.log(await this.testService.deleteCognitiveTest(id));
      for (let i = 0; i < this.testList.length; i++) {
        if (this.testList[i].id == id) {
          this.testList.splice(i, 1);
          break;
        }
      }
    }
  }

  regex = /[\ ]*([A-Za-z0-9)(]+[\ ]*)+/;

  async copyTest(test) {
    let newName = prompt('Please enter a name for the test:');
    if (newName == null || newName == '' || !this.regex.test(newName)) {
      alert('A bad name. Please choose a name with only letters and numbers');
      return;
    }
    let arr = this.regex.exec(newName);
    if (arr[0] != newName) {
      alert('A bad name. Please choose a name with only letters and numbers');
      return;
    }
    console.log(newName.trim().replace(/\s\s+/g, ' '));
    for (let test of this.testList) {
      if (test.name.trim() == newName.trim().replace(/\s\s+/g, ' ')) {
        alert('Name already taken!');
        return;
      }
    }
    let newTest = JSON.parse(JSON.stringify(test));
    newTest.name = newName.trim().replace(/\s\s+/g, ' ');
    newTest.lastModified = Date.parse(new Date().toLocaleDateString());
    newTest.lastAnswered = null;
    console.log(await this.testService.saveCognitiveTest(newTest));
    this.testList = await this.testService.findTestsForTestManager(this.managerId);
  }

  getNumberOfTestsWithSameName(name: string) {
    let count: number = 0;
    for (let test of this.testList) {
      if (test.name.startsWith(name)) {
        count++;
      }
    }

    return count;
  }

  filterKeyDown(event){
      if(event.key === "Enter")
        this.filterTests();
  }

  filterTests(){
      this.filteredTestList = [];
      this.testList.forEach((test) => {
          let checkBy : string = '';
          switch(this.filter.option){
              case 'project':
                checkBy = test.project;
              break;
              case 'name':
                checkBy = test.name;
              break;
              case 'notes':
                checkBy = test.notes
              break;
          }
          if(checkBy.includes(this.filter.text.trim())){
              this.filteredTestList.push(test);
          }
      });
      return;
  }

}
