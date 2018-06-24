import { Component, OnInit } from '@angular/core';
import { Block, Test, QuestionInDB, Manager } from '../../models/index';
import { BlockComponent } from '../block/block.component';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from '../../services/session-service/index';
import { TestService, TestManagerService } from '../../services/database-service/index';
import { AuthService } from '../../services/auth-service/index';
import { QueryList, ViewChildren  } from '@angular/core';
import { EditBlockComponent } from '../edit-block/edit-block.component';

@Component({
  selector: 'app-edit-test',
  templateUrl: './edit-test.component.html',
  styleUrls: ['./edit-test.component.css'],
  host: {
    '(document:keydown)' : 'onPress($event)'
  }
})

/*
this main component is responsible to show the whole page of the test creation.
including the block creation and sub question creation.
*/
export class EditTestComponent implements OnInit {
  //this component helps us to get variables from the sub objects.
  @ViewChildren(EditBlockComponent) blocks: QueryList<EditBlockComponent>;
  //the actual list of the blocks.
  blocksList = []

  loaded: boolean = false;

  //object of a test , so we can save and import tests.
  test: Test;
  //for iterating over the blocks. we want to keep a question related to it's current block object.
  iterator: Array<any> = new Array();
  //the string to be shown on the test title.
  titleTest: string;
  //the project string
  projectTest: string;
  //the notes string
  notesTest: string;
  //The manager of the soon to be created test;
  manager: Manager = {id: -1, email: ''};

  emptyBlock: boolean = false;

  emptyTest: boolean = false;

  noTitle: boolean = false;

  blockListFromDB: Block[];

  indexBlock: number = -1;
    /*
   * Information for importing block Author: Mark, Date: 11.6.18
   */
  testList: Test[];// The list of all the test to choose from
  testBlockList: Block[];//
  chosenBlock: Block;
  testNameToImport: string = '';
  blockPreview: boolean = false;
  finished = false;
  //default constructor
  constructor(
    private router:Router,
    private route: ActivatedRoute,
    public questionDataService:SessionService,
    private testService: TestService,
    private authService: AuthService,
    private managerService: TestManagerService
  ) {}

  /*
  async is used for syncing the rec and send of the objects.
  the rest of the init takes care of recieving the sent data.
  */
  async ngOnInit() {
    let user = this.authService.getCurrentManagerEmail();
    let managerId = await this.managerService.getManagerId(user);
    this.manager.email = user;
    this.manager.id = managerId;
    let testId = this.route.snapshot.params['testId'];
    if (isNaN(testId) || testId == '') {
      this.router.navigate(['/dashboard']);
    }
    this.testService.findCognitiveTestById(testId).then((test) => {
        this.test = test;
        for (let i = 0; i < this.test.blocks.length; i++) {
            this.addEditBlock(this.test.blocks[i]);
        }
        this.loaded = true;
        this.titleTest = this.test.name;
        this.projectTest = this.test.project;
        this.notesTest = this.test.notes;
        this.blockListFromDB = this.test.blocks;
    });

    try {
      this.testList = await this.testService.findTestsForTestManager(managerId);
    } catch(err) {
      console.log(err);
    }
  }

  //this function adds a block to out list using the iterator.
  addBlock(){
    let blockToAdd = {block: null}
    this.iterator.splice(this.iterator.length, 0, blockToAdd);
  }

  addEditBlock(block){
    let blockToAdd = {block: block};
    this.iterator.splice(this.iterator.length, 0, blockToAdd);
  }

  /*
  Input - the current index of the block to move up.
  Output - the block is moved up in the list.
  */
  moveMeUp(currentIndex: number) {
    if(currentIndex != 0){
      let removed = this.iterator.splice(currentIndex, 1);
      this.iterator.splice(currentIndex - 1, 0, removed[0]);
      if (this.blockListFromDB[currentIndex]) {
        let removedBlock = this.blockListFromDB.splice(currentIndex, 1);
        this.blockListFromDB.splice(currentIndex - 1, 0, removedBlock[0]);
      }
    }
  }

  /*
  Input - the current index of the block to move down.
  Output - the block is moved down in the list.
  */
  moveMeDown(currentIndex: number) {
    if(currentIndex != this.blocksList.length-1){
      let removed = this.iterator.splice(currentIndex, 1);
      this.iterator.splice(currentIndex + 1, 0, removed[0]);
      if (this.blockListFromDB[currentIndex]) {
        let removedBlock = this.blockListFromDB.splice(currentIndex, 1);
        this.blockListFromDB.splice(currentIndex + 1, 0, removedBlock[0]);
      }
    }
  }

  /*
  getter for the list of questions.
  */
  getQuestions(index: number){
    this.blocksList = this.blocks.toArray();
  }

  /*
  Input - the index of the block to be deleted.
  Output - the block is removed from the list.
  */
  deleteBlock(index: number){
    this.iterator.splice(index,1);
    this.blockListFromDB.splice(index, 1);
  }

  regex = /[\ ]*([A-Za-z0-9)(]+[\ ]*)+/;
  /**
   * This function saves a test in the DB.
   * It iterates over all of the questions of all of the blocks
   * and collects them to a test object
   */
  async saveTest() {
    if (this.titleTest == '' || this.titleTest == null) {
      this.noTitle = true;
      return;
    } else {
      this.noTitle = false;
    }

    if (this.titleTest == null || this.titleTest == '' || !this.regex.test(this.titleTest)) {
      alert('A bad name. Please choose a name with only letters and numbers');
      return;
    }
    let arr = this.regex.exec(this.titleTest);
    if (arr[0] != this.titleTest) {
      alert('A bad name. Please choose a name with only letters and numbers');
      return;
    }

    let testList = await this.testService.findTestsForTestManager(this.manager.id);
    for (let test of testList) {
      if (test.name.trim() == this.titleTest.trim().replace(/\s\s+/g, ' ') && this.test.id != test.id) {
        alert('Name already taken!');
        return;
      }
    }


    let blocks = this.blocks.toArray();
    if (blocks.length == 0) {
      this.emptyTest = true;
      return;
    } else {
      this.emptyTest = false;
    }
    for(let i = 0; i < blocks.length; i++){
      if(blocks[i].getQuestions().length == 0){
        this.emptyBlock = true;
        this.indexBlock = i + 1;
        return;
      } else {
        this.emptyBlock = false;
      }
    }
    let blocksToDB: Block[] = [];
    let totalQuestionNum: number = 0;
    for (let block of blocks) {

      let questions: QuestionInDB[] = [];

      for (let questionInBlock of block.getQuestions()) {
        let questionInDB: QuestionInDB =
        {
          question: JSON.stringify(questionInBlock.question),
          questionPosition: questionInBlock.question.questionPosition,
          type: questionInBlock.question.type,
          pictureLink: questionInBlock.question.pictureLink
        }
        questions.push(questionInDB);
      }

      totalQuestionNum += questions.length;

      let blockInDB: Block =
      {
        questions: questions,
        numberOfQuestions: questions.length,
        tag: JSON.stringify(block.getTags()),
        randomize: block.randomize
      }

      blocksToDB.push(blockInDB);
    }
    let date = Date.parse(new Date().toLocaleDateString());
    let test: Test =
    {
      name: this.titleTest,
      blocks: blocksToDB,
      state: 0,
      numberOfQuestions: totalQuestionNum,
      numberOfFiledCopies: 0,
      numberOfSubjects: 0,
      testManager: this.manager
    }
    this.test.name = this.titleTest;
    this.test.blocks = blocksToDB;
    this.test.state = 0;
    this.test.numberOfQuestions = totalQuestionNum;
    this.test.numberOfFiledCopies = 0;
    this.test.numberOfSubjects = 0;
    this.test.testManager = this.manager;
    this.test.project = this.projectTest ? this.projectTest.trim() : null;
    this.test.notes = this.notesTest ? this.notesTest.trim() : null;
    console.log(await this.testService.updateCognitiveTest(this.test));
    this.router.navigate(['/dashboard']);

  }

  onPress(event: KeyboardEvent) {
    if (event.key == 'F5') {
      if (confirm('Do you want to refresh or go back? All progress will be lost!!')) {
        return true;
      } else {
        return false;
      }
    }
  }

  async clickTest(index: number){
    let testId = this.testList[index].id;
    let test = await this.testService.findCognitiveTestById(testId);
    this.testBlockList = test.blocks;
    this.testNameToImport = test.name;
}
addImportedBlock(index: number){
  this.testNameToImport = '';
  let blockToAdd = this.testBlockList[index];
  let inputBlock = {block: blockToAdd};
  this.iterator.splice(this.iterator.length, 0, inputBlock);
  //this.blocksList.splice(this.blocksList.length, this.testBlockList[index])
}
previewBlock(index: number){
  this.blockPreview = true;
  let blockToAdd = this.testBlockList[index];
  this.chosenBlock = blockToAdd;
}
isFinished(e) {
  this.finished = e;
  if(this.finished){
    this.blockPreview = false;
  }
}

prettifyTagList(block, index){
    if(block.tag == '[]'){
      return 'Block no. ' + index + ' doesn\'t have tags';
    }
    let tags = JSON.parse(block.tag);
    tags = tags.map(item => item.value);
    return tags.join(", ");
}

}
