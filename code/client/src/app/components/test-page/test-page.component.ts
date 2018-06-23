import {Component, OnInit, ViewChild} from '@angular/core';
import {TestAnswersService, TestManagerService, TestService} from '../../services/database-service/index';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../services/auth-service';
import {Block, BlockAnswers, QuestionAnswerForDB, TestSubject, test_timing} from '../../models';
import {TestPageBlockComponent} from './block/block.component';
import {TimeMeasurer} from '../../Utils/index';

@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.css']
})
export class TestPageComponent implements OnInit {
  test: any;
  //variable to hold the blocks array
  blocks: Block[];

  blocksAnswers: BlockAnswers[];

  blocksLength: number;

  testId: number;

  subject: TestSubject;

  loaded: boolean = false;

  //the current test's index in the tests list.
  currIndex: number;
  //variable to indicate if we should hide the following button in the creation.
  hideNextButton: boolean = true;
  private timing: TimeMeasurer;//TimeMeasurer - instance of the timing class
  private timingResults: test_timing;//will contains all the results from the timing class.
  //private timingMeasurment: any;//TimeMeasurment - TimeMeasurment object thatll contain the results for the timing class
  private blocksLengthArray :number[];
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
    this.loaded = true;
    let testId = this.route.snapshot.params['testId'];
    if (isNaN(testId) || testId == '') {
      this.router.navigate(['/404']);
      testId = -1;
    }
    this.testId = testId;
    this.test = await this.testService.findCognitiveTestById(testId);
    if (this.test == null) {
      this.router.navigate(['/404']);
      return;
    }
    this.blocks = this.test.blocks;
    this.blocksLength = this.blocks.length;
    this.blocksAnswers = new Array<BlockAnswers>(this.blocksLength);
    this.currIndex = -1;

    // document.body.classList.add('background-white');


    //initializing the timing class
    //this.timingMeasurment = new TimeMeasurment();

    //generating the blocks lengths array for constructing the results object in the timing class.
    this.blocksLengthArray = new Array<number>(this.test.blocks.length);
    for(let i=0;i<this.test.blocks.length;i++){
      this.blocksLengthArray[i] = this.test.blocks[i].questions.length;
    }
    this.timing = new TimeMeasurer(this.test.id,this.blocks.length,this.blocksLengthArray);


    //after initializing the class we want to start the test measuring with timing_startTestMeasure() method
    this.timing.timing_startTestMeasure();
    this.loaded = true;
  }

  async finishTest() {
    this.loaded = false;
    this.timingResults = this.timing.getFullResults();
    for (let i = 0; i < this.blocksAnswers.length; i++) {
      for (let j = 0; j < this.blocksAnswers[i].answers.length; j++) {
        let timingOfQuestion = this.timingResults.resultArr[i].questionTimes[j].qTotTS;
        let timingOfQuestionConfidence = this.timingResults.resultArr[i].questionTimes[j].qConBarTotTS;
        console.log('finalAnswer: ' + this.blocksAnswers[i].answers[j].finalAnswer);
        let finalAnswer = JSON.parse(this.blocksAnswers[i].answers[j].finalAnswer);
        console.log('time is: ' + timingOfQuestion);
        let finalAnswerWithTimes = {
          finalAnswer: finalAnswer,
          answerTime: timingOfQuestion,
          confidenceTime: timingOfQuestionConfidence
        }
        let questionAnswerForDB: QuestionAnswerForDB = {
          finalAnswer: /*this.blocksAnswers[i].answers[j].finalAnswer*/JSON.stringify(finalAnswerWithTimes),
          testSubject: this.subject,
          cognitiveTest: this.test,
          question: this.test.blocks[i].questions[j]

        };
        console.log(questionAnswerForDB);
        await this.answerService.saveTestAnswer(questionAnswerForDB);
      }
    }
    this.timing.timing_stopTestMeasure();
    console.log(this.timing.getFullResults());
    this.loaded = true;
    this.router.navigate(['test-finish']);
    //when stopping the test, call timing_stopTestMeasure() to end the test measuring.


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

onFormCompletion(subject: TestSubject) {
  // will use the subject id here

  this.subject = subject;
  this.currIndex++;
}
  onBlockFinish() {
    this.hideNextButton = false;
    //when finishing the block we want to stop the timing measurment for the block.

    this.timing.timing_stopBlockMeasure(this.blocks[this.currIndex].id);
  }


}
