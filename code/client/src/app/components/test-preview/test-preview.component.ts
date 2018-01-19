import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { LocalStorageService } from '../../services/local-storage';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Test, Question, OpenQuestion, TypeQuestion, QuestionPosition, Block, QuestionInDB } from '../../models';
import { TestService } from '../../services/database-service/index';

@Component({
  selector: 'app-test-preview',
  templateUrl: './test-preview.component.html',
  styleUrls: ['./test-preview.component.css']
})

/*
this component takes place when the preview button is fired (to preview the whole test)
it uses the block viewer and indirectly the question viewer to view the whole test.
*/
export class TestPreviewComponent implements OnInit {
  
  //default constructor 
  constructor(
    private localStorageService: LocalStorageService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private testService: TestService
  ) { }

  //the test object , so we can save/import tests.
  test: Test;
  //the current test id.
  testId: number;
  //the current test's index in the tests list.
  currIndex: number;
  //variable to indicate if we should hide the following button in the creation.
  hideNextButton: boolean = true;

  //default initialization function.
  async ngOnInit() {
    var question: OpenQuestion = {
      questionText: 'hello friend?',
      type: TypeQuestion.OpenQuestion,
      answerText: 'hello',
      questionPosition: QuestionPosition.ButtomLeft
    }

    var question2: OpenQuestion = {
      questionText: 'what\'s new with you?',
      type: TypeQuestion.OpenQuestion,
      answerText: 'nothin\' much',
      questionPosition: QuestionPosition.UpperMiddle
    }

    var question3: OpenQuestion = {
      questionText: 'hihihihi?',
      type: TypeQuestion.OpenQuestion,
      answerText: 'nothin\' much',
      questionPosition: QuestionPosition.UpperMiddle
    }

    var question5: OpenQuestion = {
      questionText: 'hih?',
      type: TypeQuestion.OpenQuestion,
      answerText: 'nothin\' much',
      questionPosition: QuestionPosition.UpperMiddle
    }

    let question1: QuestionInDB = {
      question: 'tmp question',
      questionPosition: 0,
      answer: 'nooooo',
      questionType: QuestionPosition.ButtomLeft
    }

    var block : Block = {
      questions: [question1],
      numberOfQuestions: 1
    }

    var block2: Block = {
      questions: [question1],
      numberOfQuestions: 1
    }

    var block3: Block = {
      questions: [question1],
      numberOfQuestions: 1
    }
    this.testId = this.route.snapshot.params['testId'];
    this.test = {
      blocks: [block,block2,block3]
    }
    this.test.name='test test tset';
    this.test.lastAnswered='2018-01-01';
    this.test.lastModified='2018-01-01';
    this.test.numberOfFiledCopies=1;
    this.test.numberOfQuestions=2;
    this.test.numberOfSubjects=3;
    this.test.state=0;
    //this.test.blocks=[];
    this.test.testManager = {id: '1', email:'aa@aa.com'}
    console.log(await this.testService.saveCognitiveTest(this.test));
    this.currIndex = 0;
  }

  /*
  Input - an index of a specific block
  Output - returns a boolean result if the called block's id (from the HTML form) is the current id 
  were viewing. 
  */
  showBlock(index) {
    return index == this.currIndex;
  }

  /*
  to indicate if we finished previewing the test.
  */
  isFinished(e) {
    this.hideNextButton = !e;
  }

  //routing back to the dashboard after we finished previewing the test.
  finishPreview() {
    this.router.navigate(['/dashboard']);
  }

  /*
  this function increments our block index (the index of the block were viewing)
  and checks if we finished (reached the last object in the blocks list)
  */
  nextBlock() {
    this.currIndex++;
    this.hideNextButton = true;
    if (this.currIndex == this.test.blocks.length) {
      this.finishPreview();
    }
  }

}
