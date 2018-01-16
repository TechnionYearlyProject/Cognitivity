import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { LocalStorageService } from '../../services/local-storage';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Test, Question, OpenQuestion, TypeQuestion, QuestionPosition, Block } from '../../models';

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
    private router: Router
  ) { }
  test: Test;
  testId: number;
  currIndex: number;
  hideNextButton: boolean = true;

  ngOnInit() {
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
      questionList: [question, question2]
    }

    var block2: Block = {
      questionList: [question3]
    }

    var block3: Block = {
      questionList: [question5]
    }
    this.testId = this.route.snapshot.params['testId'];
    this.test = this.localStorageService.get('test'+this.testId);
    this.test = {
      blockList: [block,block2,block3]
    }
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
    if (this.currIndex == this.test.blockList.length) {
      this.finishPreview();
    }
  }

}
