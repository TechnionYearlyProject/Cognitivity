import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { LocalStorageService } from '../../services/local-storage';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Test, Question, OpenQuestion, TypeQuestion, QuestionPosition, Block } from '../../models';
import { TestService } from '../../services/database-service/index';

@Component({
  selector: 'app-test-preview',
  templateUrl: './test-preview.component.html',
  styleUrls: ['./test-preview.component.css']
})
export class TestPreviewComponent implements OnInit {

  constructor(
    private localStorageService: LocalStorageService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private testService: TestService
  ) { }
  test: Test;
  testId: number;
  currIndex: number;
  hideNextButton: boolean = true;

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

    var block : Block = {
      questions: [question, question2]
    }

    var block2: Block = {
      questions: [question3]
    }

    var block3: Block = {
      questions: [question5]
    }
    this.testId = this.route.snapshot.params['testId'];
    this.test = this.localStorageService.get('test'+this.testId);
    this.test = {
      blocks: [block,block2,block3]
    }
    this.test.name='ff';
    this.test.lastAnswered='2018-01-01';
    this.test.lastModified='2018-01-01';
    this.test.numberOfFiledCopies=0;
    this.test.numberOfQuestions=0;
    this.test.numberOfSubjects=0;
    this.test.state=0;
    this.test.blocks=[];
    console.log(await this.testService.saveCognitiveTest(this.test));
    this.currIndex = 0;
  }

  showBlock(index) {
    return index == this.currIndex;
  }

  isFinished(e) {
    this.hideNextButton = !e;
  }

  finishPreview() {
    this.router.navigate(['/dashboard']);
  }

  nextBlock() {
    this.currIndex++;
    this.hideNextButton = true;
    if (this.currIndex == this.test.blocks.length) {
      this.finishPreview();
    }
  }

}
