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

    var block : Block = {
      questionList: [question]
    }
    this.testId = this.route.snapshot.params['testId'];
    this.test = this.localStorageService.get('test'+this.testId);
    this.test = {
      blockList: [block]
    }
    this.currIndex = 0;
  }

  showBlock(index) {
    return index == this.currIndex;
  }

  isFinished(e) {
    this.hideNextButton = !e;
  }

}
