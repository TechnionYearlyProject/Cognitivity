import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Test, EmailsDist } from '../../../models'
import { TestService, TestManagerService, EmailsService } from '../../../services/database-service'
import { AuthService } from '../../../services/auth-service';
import { ViewChild } from '@angular/core';




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
    option: 'empty',
    text: ''
  }
  //an object to represent the current manager. it hold the current logged in user's credentials.
  email;
  managerId;
  file : string[] = null;
  link: string;
  chosen_file: boolean = false;
  loaded: boolean = false;
  @ViewChild('inputFile') myInputFile : any;
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
        this.filteredTestList = [];
        this.testList.forEach((test) => {
          this.filteredTestList.push(test);
        });
        this.loaded = true;
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
      this.testList = this.testList.filter((item) => item.id != id);
      this.filterTests(false)
      await this.testService.deleteCognitiveTest(id);
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
    this.loaded = false;
    let expandedTest = await this.testService.findCognitiveTestById(test.id)
    .then(async test => {
      let newTest = JSON.parse(JSON.stringify(test));
      newTest.name = newName.trim().replace(/\s\s+/g, ' ');
      newTest.lastModified = Date.parse(new Date().toLocaleDateString());
      newTest.lastAnswered = null;
      console.log(await this.testService.saveCognitiveTest(newTest));
      this.testList = await this.testService.findTestsForTestManager(this.managerId);
      this.filteredTestList = this.testList;
      this.loaded = true;
    });

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


chooseCategory : boolean = true;

  filterKeyDown(event){
      if(event.key === "Enter")
        this.filterTests();
  }


  // calledFromSubmit : whether the function called from the html file or not (filterKeyDown is a call from the html)
  filterTests(calledFromSubmit : boolean = true){
    console.log("in here");
    if(calledFromSubmit && this.filter.option == 'empty'){
        this.chooseCategory = false;
        return;
    }
    this.chooseCategory = true;
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
            if(!this.filter.text.trim() || (checkBy && checkBy.includes(this.filter.text.trim()))){
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
    this.myInputFile.nativeElement.value = "";
    this.chosen_file = false;
    this.link = "https://cognitivitywebsite.azurewebsites.net//test/" + test.id;
  }
  updateFile(event){
    if (event.target.files == null || event.target.files.length == 0){
      this.chosen_file = false;
      return;
    }

    if(event.target.files.length != 1){
        this.chosen_file = false;
        alert("only one file can be submitted each time");
        return;
    }
    this.chosen_file = true;
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

will_edit(index: number){
  if(confirm('Note! Updating the test will erase the result from the database for this test')){
    this.router.navigate(['/edit-test/'+this.filteredTestList[index].id]);
  }
}
}
