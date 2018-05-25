import { Component, OnInit, ViewChild } from '@angular/core';
import { TestService, TestManagerService } from '../../services/database-service/index';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { Test, OpenQuestion, TypeQuestion, QuestionPosition, Block, RateQuestion, BlockAnswers, TimeMeasurment } from '../../models/index';
import { TestPageBlockComponent } from './block/block.component';
import { TimeMeasurer } from '../../Utils/index';
@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.css']
})
export class TestPageComponent implements OnInit {
  test : any;
  //variable to hold the blocks array
  blocks: Block[];

  blocksAnswers: BlockAnswers[];

  blocksLength: number;

  

   //the current test's index in the tests list.
   currIndex: number;
   //variable to indicate if we should hide the following button in the creation.
  hideNextButton: boolean = true;

  @ViewChild(TestPageBlockComponent) block: TestPageBlockComponent;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private testService: TestService,
    private tmService: TestManagerService,
    private authService: AuthService,
    private timing:any,//TimeMeasurer - instance of the timing class
    private timingMeasurment:any//TimeMeasurment - TimeMeasurment object thatll contain the results for the timing class
  ) {
   }

  async ngOnInit() {
    let email// = this.authService.getCurrentManagerEmail();
    let testId = this.route.snapshot.params['testId'];
    if (isNaN(testId) || testId == '') {
      //Here we will navigate to a 404 page
      //this.router.navigate(['/dashboard']);
    }
    this.test //= await this.testService.findCognitiveTestById(testId);
    if (this.test == null) {
      //Here we will navigate to a 404 page
      //this.router.navigate(['/dashboard']);
    }


    let question: OpenQuestion = {
      id: 1,
      questionText: "How are you?",
      answerText: "good",
      type: TypeQuestion.OpenQuestion,
      questionPosition: QuestionPosition.UpperMiddle,
    }

    let question2: RateQuestion = {
      id: 2,
      questionText: "Are you ok???",
      heightOfRate: 5,
      type: TypeQuestion.RateQuestion,
      questionPosition: QuestionPosition.UpperMiddle,
    }

    let question3: OpenQuestion = {
      id: 2,
      questionText: "Are you ok???",
      answerText: "yay",
      type: TypeQuestion.OpenQuestion,
      questionPosition: QuestionPosition.UpperMiddle,
    }

    //this.blocks = this.test.blocks;
    let block: Block = {
      questions: [{
        id: 1, 
        question: "lol what", 
        questionPosition: question.questionPosition,
        answer: JSON.stringify(question),
        type:question.type
      },{
        id: 2, 
        question: "lol what", 
        questionPosition: question2.questionPosition,
        answer: JSON.stringify(question2),
        type:question2.type
      }],

    }
    let block2: Block = {
      questions: [{
        id: 1, 
        question: "lol what", 
        questionPosition: question3.questionPosition,
        answer: JSON.stringify(question3),
        type:question3.type
      },{
        id: 2, 
        question: "lol what", 
        questionPosition: question2.questionPosition,
        answer: JSON.stringify(question2),
        type:question2.type
      }],

    }

    this.blocks = [block, block2]; //TODO: remove
    this.blocksLength = this.blocks.length;
    this.blocksAnswers = new Array<BlockAnswers>(this.blocksLength);
    this.currIndex = -1;

    document.body.classList.add('background-white');

    //initializing the timing class
    this.timing = new TimeMeasurer(this.timingMeasurment,this.test.id,this.blocks.length);
    //after initializing the class we want to start the test measuring with timing_startTestMeasure() method
    this.timing.timing_startTestMeasure();

  }

  finishTest() {
    console.log(this.blocksAnswers);
    //when stopping the test, call timing_stopTestMeasure() to end the test measuring.
    this.timing.timing_stopTestMeasure();
    //the results of the timing class sits in ----------this.timingMeasurment.testObject--------------
  }

  /*
  this function increments our block index (the index of the block were viewing)
  and checks if we finished (reached the last object in the blocks list)
  */
nextBlock() {
  this.hideNextButton = true;
  this.blocksAnswers[this.currIndex] = this.block.getQuestionAnswers();
  this.currIndex++;
  if (this.currIndex == this.blocks.length) {
    
    this.finishTest();
  }
}
showBlock(index) {
  return index == this.currIndex;
}

onBlockFinish() {
  this.hideNextButton = false;
  //when finishing the block we want to stop the timing measurment for the block.
  this.timing.timing_stopBlockMeasure(this.blocks[this.currIndex].id);
}

onFormCompletion(subjectId: number) {
  // will use the subject id here
  console.log("subject id is", subjectId);
  this.currIndex++;
}

}
