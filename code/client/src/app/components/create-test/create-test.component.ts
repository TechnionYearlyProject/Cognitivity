import { Component, OnInit } from '@angular/core';
import { Block, Test, QuestionInDB, Manager } from '../../models/index';
import { BlockComponent } from '../block/block.component';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from '../../services/session-service/index';
import { TestService, TestManagerService } from '../../services/database-service/index';
import { AuthService } from '../../services/auth-service/index';
import { QueryList, ViewChildren  } from '@angular/core';

@Component({
  selector: 'app-create-test',
  templateUrl: './create-test.component.html',
  styleUrls: ['./create-test.component.css']
})

/*
this main component is responsible to show the whole page of the test creation.
including the block creation and sub question creation.
*/
export class CreateTestComponent implements OnInit {
  //this component helps us to get variables from the sub objects.
  @ViewChildren(BlockComponent) blocks: QueryList<BlockComponent>;
  //the actual list of the blocks.
  blocksList = []
  //object of a test , so we can save and import tests.
  test: Test;
  //for iterating over the blocks. we want to keep a question related to it's current block object.
  iterator: Array<Object> = new Array();
  //the string to be shown on the test title.
  titleTest: string;
  //The manager of the soon to be created test;
  manager: Manager = {id: -1, email: ''};

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
    let user = this.authService.getCurrentManager();
    console.log(user);
    let managerId = await this.managerService.getManagerId(user.email);
    this.manager.email = user.email;
    this.manager.id = managerId;
    console.log(this.test);
  }

  //this function adds a block to out list using the iterator.
  addBlock(){
    this.iterator.splice(this.iterator.length, 0, new Object());
  }

  /*
  Input - the current index of the block to move up.
  Output - the block is moved up in the list.
  */
  moveMeUp(currentIndex: number) {
    if(currentIndex != 0){
      let removed = this.iterator.splice(currentIndex, 1);
      this.iterator.splice(currentIndex - 1, 0, removed[0]);
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
    }
  }

  /*
  getter for the list of questions.
  */
  getQuestions(index: number){
    this.blocksList = this.blocks.toArray();
    console.log(this.blocksList[index].getQuestions());
  }

  /*
  Input - the index of the block to be deleted.
  Output - the block is removed from the list.
  */
  deleteBlock(index: number){
    console.log('In delete');
    this.iterator.splice(index,1);
  }

  async saveTest() {
    let blocks = this.blocks.toArray();
    let blocksToDB: Block[] = [];
    let totalQuestionNum: number = 0;
    for (let block of blocks) {

      let questions: QuestionInDB[] = [];

      for (let questionInBlock of block.getQuestions()) {

        let questionInDB: QuestionInDB = 
        {
          question: questionInBlock.question.questionText,
          questionPosition: questionInBlock.question.questionPosition,
          questionType: questionInBlock.question.type,
          answer: JSON.stringify(questionInBlock.question)
        }

        questions.push(questionInDB);
      }

      totalQuestionNum += questions.length;

      let blockInDB: Block = 
      {
        questions: questions,
        numberOfQuestions: questions.length
      }

      blocksToDB.push(blockInDB);
    }
    
    let test: Test = 
    {
      name: this.titleTest,
      lastModified: Date.parse(new Date().toLocaleDateString()).toString(),
      lastAnswered: null,
      blocks: blocksToDB,
      state: 0,
      numberOfQuestions: totalQuestionNum,
      numberOfFiledCopies: 0,
      numberOfSubjects: 0,
      testManager: this.manager
    }
    console.log(await this.testService.saveCognitiveTest(test));
    this.router.navigate(['/dashboard']);

  }
  

}
