import { Component, OnInit } from '@angular/core';
import { QuestionAnswer } from '../../models/index';

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
  constructor() { }
  test_name: string = 'My Test'; // The name of the test should get from the dashboard (the caller test) TODO: after connecting to the dashboard assign this as Input directive
  test_id: number;// The id of the test. Will be used in the service to collect the results of the test. TODO: after connecting to the dashboard assign this as Input directive
  columnDefs : any; //The definition of the table that hold all the information on the results
  ngOnInit() {
    this.define_table_results();
  }

  /* Here we define the columns that will be presented. */
  define_table_results(){
    this.columnDefs = [
      {headerName: 'Question ID', field: 'question_id'},
      {headerName: 'Subject ID', field: 'subject_id'},
      {headerName: 'Type of Question', field: 'question_type'},
      {headerName: 'Confidence in Answer', field: 'conf_value' },
      {headerName: 'Time Distraction', field: 'is_time_distraction'},
      {headerName: '# of Answer Changes', field: 'changes_of_answer'},
      {headerName: 'Time for Answer', field: 'time'},
      {headerName: 'TIme for confidense bar', field: 'time_conf'},
      {headerName: 'Answer', field: 'answer'},
      
    ];
  }

  
  rowData = [
    {question_id: 54, subject_id: 'Mark Erlikh',question_type: '1', conf_value: 'No conf', is_time_distraction: false, answer: 'Hi', changes_of_answer: 5, time: 20},
    {question_id: 55, subject_id: 'Mark Erlikh',question_type: '1', conf_value: 25, is_time_distraction: false, answer: 'Hi', changes_of_answer: 5, time: 20}
  ];
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
}

