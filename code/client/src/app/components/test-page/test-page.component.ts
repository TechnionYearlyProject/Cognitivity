import { Component, OnInit, ViewChild } from '@angular/core';
import { TestService, TestManagerService, TestAnswersService } from '../../services/database-service/index';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { Test, OpenQuestion, TypeQuestion, QuestionPosition, Block, RateQuestion, BlockAnswers, MultipleChoiceQuestion, TypeMultipleQuestion, DrillDownQuestion, TimeMeasurment, TestSubject, QuestionAnswerForDB } from '../../models';
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

  testId: number;
  
  subject: TestSubject;

   //the current test's index in the tests list.
   currIndex: number;
   //variable to indicate if we should hide the following button in the creation.
  hideNextButton: boolean = true;
  private timing:TimeMeasurer;//TimeMeasurer - instance of the timing class
  private timingMeasurment:any;//TimeMeasurment - TimeMeasurment object thatll contain the results for the timing class
  @ViewChild(TestPageBlockComponent) block: TestPageBlockComponent;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private testService: TestService,
    private tmService: TestManagerService,
    private authService: AuthService,
    private answerService: TestAnswersService
    
  ) {
   }

  async ngOnInit() {
    let email// = this.authService.getCurrentManagerEmail();
    let testId = this.route.snapshot.params['testId'];
    if (isNaN(testId) || testId == '') {
      //Here we will navigate to a 404 page
      //this.router.navigate(['/dashboard']);
    }
    this.testId = testId;
    this.test = await this.testService.findCognitiveTestById(testId);
    if (this.test == null) {
      //Here we will navigate to a 404 page
      //this.router.navigate(['/dashboard']);
    }

    console.log(this.test);

    this.blocks = this.test.blocks;
    this.blocksLength = this.blocks.length;
    this.blocksAnswers = new Array<BlockAnswers>(this.blocksLength);
    this.currIndex = -1;

    document.body.classList.add('background-white');

    //initializing the timing class
    //this.timing = new TimeMeasurer(this.timingMeasurment,this.test.id,this.blocks.length);
    //after initializing the class we want to start the test measuring with timing_startTestMeasure() method
    //this.timing.timing_startTestMeasure();

  }

  async finishTest() {

    console.log(this.blocksAnswers);
    for (let i = 0; i < this.blocksAnswers.length; i++) {
      for (let j = 0; j < this.blocksAnswers[i].answers.length; j++) {
        let questionAnswerForDB: QuestionAnswerForDB = {
          finalAnswer: this.blocksAnswers[i].answers[j].finalAnswer,
          testSubject: this.subject,
          cognitiveTest: this.test,
          question: this.test.blocks[i].questions[j]
          
        }
          console.log(questionAnswerForDB);
          await this.answerService.saveTestAnswer(questionAnswerForDB);
      }
    }
    //when stopping the test, call timing_stopTestMeasure() to end the test measuring.
    //this.timing.timing_stopTestMeasure();
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
    console.log('finished test in nextBlock');
    this.finishTest();
  }
}
showBlock(index) {
  return index == this.currIndex;
}

onBlockFinish() {
  this.hideNextButton = false;
  //when finishing the block we want to stop the timing measurment for the block.
  //this.timing.timing_stopBlockMeasure(this.blocks[this.currIndex].id);
}

onFormCompletion(subject: TestSubject) {
  // will use the subject id here
  console.log("subject id is", subject);
  this.subject = subject;
  this.currIndex++;
}

}
