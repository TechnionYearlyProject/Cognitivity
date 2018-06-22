import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Test, EmailsDist } from '../../../models'
import { TestService, TestManagerService, EmailsService } from '../../../services/database-service'
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
  file : string[] = null;
  link: string;

  loaded: boolean = false;
  //default constructor.
  constructor(
    private testService: TestService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private tmService: TestManagerService,
    private emailsService: EmailsService){}

  //default ngOnInit function, gets the user's credentials while initialized.
  async ngOnInit() {
    try {
      this.email = this.authService.getCurrentManagerEmail();
      this.managerId = await this.tmService.getManagerId(this.email);
      //this.testList = await this.testService.findTestsForTestManager(this.managerId);
      this.testService.findTestsForTestManager(this.managerId).then(testList => {
        this.loaded = true;
        this.testList = testList;
        console.log(this.testList[0]);
        this.filteredTestList = [];
        this.testList.forEach((test) => {
          this.filteredTestList.push(test);
        });
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

  // function for confirmation before calling to asynch function (which costs in page reload, even when confirmation fails)
  // confirmation(message: string, func, params) {
  //   if(confirm(message)) func(params);
  //   else()
  // }
  async deleteTest(id: number) {
    if (confirm('Are you sure you want to delete the test?')) {
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
    if (newName === null) {
      return;
    }
    if (newName == '' || !this.regex.test(newName)) {
      alert('A bad name. Please choose a name with only letters and numbers');
      return;
    }
    let arr = this.regex.exec(newName);
    if (arr[0] != newName) {
      alert('A bad name. Please choose a name with only letters and numbers');
      return;
    }
    for (let test of this.testList) {
      if (test.name.trim() == newName.trim().replace(/\s\s+/g, ' ')) {
        alert('Name already taken!');
        return;
      }
    }
    let expandedTest = await this.testService.findCognitiveTestById(test.id);
    let newTest = JSON.parse(JSON.stringify(expandedTest));
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

  async gen_link(){
    let emails: EmailsDist = {emails: this.file, link: this.link};
    await this.emailsService.sendLinks(emails);
  }
  genLinkForTest(test: Test){
    this.link = "http://localhost:4200/test/" + test.id;
  }
  updateFile(event){
    if(event.target.files.length != 1){
        alert("only one file can be submitted each time");
        return;
    }
    let fullFile = event.target.files[0];
    var reader = new FileReader();
    reader.onload = (event) => {
        try {
              this.file = reader.result.split('\n').map(x => x.trim()).filter(x => x.length > 0);
        } catch (ex) {
            alert('exeption when trying to parse json = ' + ex);
        }
    };
    reader.readAsText(fullFile);

}
}
