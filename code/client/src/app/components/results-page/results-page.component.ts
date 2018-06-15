import {Component, OnInit} from '@angular/core';
import {ParsedQuestionAnswer, QuestionAnswerForDB, TypeQuestion} from '../../models/index';
import {TestAnswersService} from '../../services/database-service';
import {ActivatedRoute, Router} from '@angular/router';
import {GridOptions} from 'ag-grid/dist/lib/entities/gridOptions';

@Component({
  selector: 'app-results-page',
  templateUrl: './results-page.component.html',
  styleUrls: ['./results-page.component.css']
})
export class ResultsPageComponent implements OnInit {

  /*
   *
   * Author: Mark Erlikh Date: 19.5.18
   *
   */
  test_name: string; // The name of the test should get from the dashboard (the caller test)
  constructor(
    private answerTestService: TestAnswersService,
    private router: Router,
    private route: ActivatedRoute
  ) {

  }

  test_id: number;// The id of the test. Will be used in the service to collect the results of the test. TODO: after connecting to the dashboard assign this as Input directive
  columnDefs: any; //The definition of the table that hold all the information on the results
  answers: Array<ParsedQuestionAnswer> = new Array();
  private gridOptions: GridOptions;
  flag: boolean = false;

  async ngOnInit() {
    let testId = this.route.snapshot.params['testId'];
    this.test_name = this.route.snapshot.params['testName'];
    if (isNaN(testId) || testId == '') {
      this.router.navigate(['/dashboard']);
    }
    console.log('over here');
    let answers = await this.answerTestService.findAllAnswersForTest(testId);
    this.flag = true;
    this.parseAnswers(answers);
    console.log(answers);
  }

  /* Here we define the columns that will be presented. */
  parseAnswers(answers: QuestionAnswerForDB []) {
    for (let questionAnswer of answers) {
      let answerObject = JSON.parse(questionAnswer.finalAnswer);
      let typeQuestion = JSON.parse(questionAnswer.question.question).type;
      console.log(questionAnswer);
      let questionAnswerParsed;
      switch(typeQuestion){
          case TypeQuestion.MultipleChoice:
              questionAnswerParsed = {
                id: questionAnswer.id,
                question_id: questionAnswer.question.id,
                subject_id: questionAnswer.testSubject.id,
                name: questionAnswer.testSubject.name,
                question_type: typeQuestion,
                conf_value: answerObject.confidence,
                is_time_distraction: false,
                changes_of_answer: 0,
                time: 0,
                time_conf: 0,
                answer: (answerObject.answer.map(item => item + 1))
              };
              break;
          case TypeQuestion.DrillDownQuestion:
              let answerString;
              if (answerObject.secnodaryAnswer == '-1') {
                answerString = 'Main: ' + (parseInt(answerObject.primaryAnswer) + 1)
              } else {
                answerString = 'Main: ' + (parseInt(answerObject.primaryAnswer) + 1) + ' Secondary: ' + (parseInt(answerObject.secnodaryAnswer) + 1)
              }
              questionAnswerParsed = {
                id: questionAnswer.id,
                question_id: questionAnswer.question.id,
                subject_id: questionAnswer.testSubject.id,
                name: questionAnswer.testSubject.name,
                question_type: typeQuestion,
                conf_value: answerObject.confidence,
                is_time_distraction: false,
                changes_of_answer: 0,
                time: 0,
                time_conf: 0,
                answer: answerString
              }
              break;

          default:
              questionAnswerParsed = {
                id: questionAnswer.id,
                question_id: questionAnswer.question.id,
                subject_id: questionAnswer.testSubject.id,
                name: questionAnswer.testSubject.name,
                question_type: typeQuestion,
                conf_value: answerObject.confidence,
                is_time_distraction: false,
                changes_of_answer: 0,
                time: 0,
                time_conf: 0,
                answer: (parseInt(answerObject.answer) + 1)
              };
              break;
      }
      this.answers.push(questionAnswerParsed);
      console.log(this.answers);
    }
  }

  async deleteQuestionResult(answerId, questionId){
      console.log("answer id : " + answerId);
      console.log("question id : " + questionId);
    await this.answerTestService.deleteTestAnswer(questionId, answerId);
  }


}

/* Here we define a hard coded (for now) rows data */
// rowData = [
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 },
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 },
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 },
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 },
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 },
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 },
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 }
// ];
