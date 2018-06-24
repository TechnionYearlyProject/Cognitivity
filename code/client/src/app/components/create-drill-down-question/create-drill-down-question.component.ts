import { Component, OnInit } from '@angular/core';
import { SecondaryQuestionObject } from '../../models/index';
import { Input } from '@angular/core';

@Component({
  selector: 'app-create-drill-down-question',
  templateUrl: './create-drill-down-question.component.html',
  styleUrls: ['./create-drill-down-question.component.css']
})
export class CreateDrillDownQuestionComponent implements OnInit {
  /*
    --------------- drill down question details ---------------
  */
  //saves flag for knowing if the main answer was already submitted
  typedMainAnswer: boolean = false;
  //saces flag for knowing if the secondary answer was already submitted
  typedSecondaryAnswer: boolean = false;
  //current main answer that is submitted
  currentMainAnswer?: string;
  //flag for the state when the user pressed a main answer
  showMainAnswer: boolean;
  //the index of the answer that the user wants to see
  indexOfMainanswerToShow: number = -1;
  //an array of all the main answers
  mainAnswers?: Array<string> = new Array();
  //the marking of all the main answers
  markedMainCorrectAnswer?: Array<boolean> = new Array();
  //an array of all the secondary questions
  secondaryQuestionList? : Array<SecondaryQuestionObject> = new Array();
  //array of the answers to the current secondary question
  secondaryAnswers?: Array<string> = new Array();
  //flag for the state when the secondary question is being submitted
  submitSecondaryQuestion?: boolean = false;
  //current secondary question that the user inputs
  currentSecondaryQuestion?: string = '';
  //current secondary answer that the user inputs
  currentSecondaryAnswer?: string = '';
  //array of all secondary question
  secondaryQuestions?: Array<string> = new Array();
  //edit mode of editing the whole secondary question
  editionQuestionSecondary?: boolean = false;
  //array of the marked secondary answers
  markedSecondaryCorrectAnswer?: Array<boolean> = new Array();
  //edit mode of a secondary answer
  editionModeSecondary: boolean = false;
  //index of an answer that being edited
  indexAnswerInEditSecondary: number = -1;
  //view mode of a secondary question
  viewSecondary: boolean = false;
  //answers of the secondary that is being viewd
  viewAnswers: Array<string> = new Array();
  //answers of the secondary question that is being
  questionView: string = '';
  //secondaryAnswers?: Array<Array<string>>;
  editionModeMain?: boolean;
  //flag for the state when the user creates a secondary question
  secondaryAnswerMode?: boolean = false;
  //index of the main answer to edit
  indexAnswerInEditMain: number = -1;
  //true if answer already exists
  answerExistsInArray: boolean = false;

  @Input() question: any;
  constructor() { }

  ngOnInit() {
    if(this.question != null){
      this.mainAnswers = Object.assign([], this.question.answersForMain);
      this.markedMainCorrectAnswer = new Array(this.mainAnswers.length);
      for(let i = 0 ; i < this.markedMainCorrectAnswer.length; i++){
        if(i == this.question.correctMainQuestion){
          this.markedMainCorrectAnswer[i] = true;
        }else{
          this.markCorretSecondaryAnswer[i] = false;
        }
      }
      let real_size = 0;
      let size_to_iterate = this.question.answersForMain.length;
      for(let i = 0; i < size_to_iterate; i++ ){
        if(this.question.secondaryQuestionsText[i] != null){
          let obj_to_insert = {
            index: i,
            questionText: this.question.secondaryQuestionsText[i],
            markedAnswer: this.question.correctAnswerSecondary[i],
            answers: Object.assign([], this.question.answersForSecondary[i])
          }
          this.secondaryQuestionList.splice(this.secondaryQuestionList.length, 0, obj_to_insert);
        }
      }
    }
  }

  /*

    The function adds an answer for the main question
  */
  addMainAnswer(){
    // answer already exists
    if(this.currentMainAnswer != null && this.currentMainAnswer.length >= 1
        && !this.isSpacePrefix(this.currentMainAnswer)){
      if(-1 != this.mainAnswers.indexOf(this.currentMainAnswer)){
        this.answerExistsInArray = true;
        return;
      }

      this.mainAnswers.push(this.replaceSpacesByOneSpace(this.currentMainAnswer));
      this.markedMainCorrectAnswer.push(false);
      this.currentMainAnswer = '';
      this.submitSecondaryQuestion = false;
      this.typedMainAnswer = false;
    } else {
      this.typedMainAnswer = true;
    }
    this.answerExistsInArray = false;
  }
  /*
    The function returns TRUE if there are answers to the main question,
    FALSE otherwise
  */
  haveMainAnswers(): boolean {
    return this.mainAnswers.length >= 1;
  }

  /*
    The function displays the main answer that was chosen
  */
  markMainAnswer(index: number){
    this.secondaryAnswerMode = false;
    if(this.indexOfMainanswerToShow == index){
      this.showMainAnswer = false;

      this.indexOfMainanswerToShow = -1;
    }else{
      this.showMainAnswer = true;
      this.indexOfMainanswerToShow = index;
    }
    this.secondaryAnswerMode = false;
    this.editionModeSecondary = false;
    this.editionQuestionSecondary = false;
    this.viewSecondary = false;
    this.currentSecondaryQuestion = '';
    this.currentSecondaryAnswer = '';
    this.markedSecondaryCorrectAnswer = new Array();
    this.secondaryAnswers = new Array();


  }
  /*
    The function saves the answer that was marked as correct
  */
  markCorretMainAnswer(index:number){
    for(let i = 0; i < this.markedMainCorrectAnswer.length; i++){
      if(i == index){
        this.markedMainCorrectAnswer[i] = !this.markedMainCorrectAnswer[i];
      }else{
        this.markedMainCorrectAnswer[i] = false;
      }
    }
  }
  /*
    Deletes an answer to the question
  */
  deleteMainAnswer(index: number){
    this.viewSecondary = false;
    this.secondaryAnswerMode = false;
    this.editionQuestionSecondary = false;
    this.editionModeSecondary = false;
    if(this.showMainAnswer){
      this.showMainAnswer = false;
      this.secondaryAnswerMode = false;
      this.indexOfMainanswerToShow = -1;
    }
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(index == this.secondaryQuestionList[i].index){
        this.secondaryQuestionList.splice(i,1);
      }
    }
    this.mainAnswers.splice(index,1);
    this.markedMainCorrectAnswer.splice(index, 1);
    this.secondaryAnswers = new Array();
    this.currentSecondaryAnswer = '';
    this.markedSecondaryCorrectAnswer = new Array();
    this.currentSecondaryQuestion = '';
  }
  /*
    The function edits an answer for the main question
  */
  editMainAnswer(index: number){
    this.viewSecondary = false;
    this.secondaryAnswerMode = false;
    this.editionModeSecondary = false;
    this.editionQuestionSecondary = false;
    this.currentSecondaryAnswer = '';
    this.currentSecondaryQuestion = '';
    this.markedSecondaryCorrectAnswer = new Array();
    this.secondaryAnswers = new Array();
    this.currentMainAnswer = this.mainAnswers[index];
    this.editionModeMain = true;
    this.indexAnswerInEditMain = index;
  }
  /*
    The function doesn't change the edited answer for the main question
  */
  undoEditMain(){
    this.editionModeMain = false;
    this.currentMainAnswer = '';
    this.indexAnswerInEditMain = -1;
    this.typedMainAnswer = false;
  }
  /*
    applies changes of an answer of the main question
  */
  applyEditMain(){
    if(this.currentMainAnswer != null
        && this.currentMainAnswer.length >= 1
        && !this.isSpacePrefix(this.currentMainAnswer)){
            if(-1 != this.mainAnswers.findIndex((item, index) => {
                return item == this.currentMainAnswer && index != this.indexAnswerInEditMain;
            })){
                this.answerExistsInArray = true;
                return;
            }
      this.mainAnswers.splice(this.indexAnswerInEditMain, 1,  this.currentMainAnswer)
      this.editionModeMain = false;
      this.indexAnswerInEditMain = -1;
      this.currentMainAnswer = '';
      this.typedMainAnswer = false;
      this.answerExistsInArray = false;
    }else{
      this.typedMainAnswer = true;
    }
  }
  /*
    The function moves up the requested answer to the main question
  */
  goMainUp(index: number){

    if(index != 0){
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(index - 1 == this.secondaryQuestionList[i].index){

          let tmp = {
            index: index,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }else if(index == this.secondaryQuestionList[i].index){
          let tmp = {
            index: index - 1,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          let removed = this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }
      }
      if(this.showMainAnswer){
        if(this.indexOfMainanswerToShow <= index){
          this.indexOfMainanswerToShow = index - 1;
        }
      }
      let removed = this.mainAnswers.splice(index, 1)
      let removed_marked_correct = this.markedMainCorrectAnswer.splice(index,1);
      this.mainAnswers.splice(index - 1, 0, removed[0]);
      this.markedMainCorrectAnswer.splice(index - 1, 0, removed_marked_correct[0]);

    }
  }
   /*
    The function moves down the requested answer to the main question
  */
  goMainDown(index: number){
    if(index != this.mainAnswers.length - 1){
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(index == this.secondaryQuestionList[i].index){
          let tmp = {
            index: index + 1,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }else if(index + 1 == this.secondaryQuestionList[i].index){
          let tmp = {
            index: index,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }
      }
      if(this.showMainAnswer){
        if(this.indexOfMainanswerToShow <= index){
          this.indexOfMainanswerToShow = index + 1;
        }
      }
      let removed = this.mainAnswers.splice(index, 1);
      let removed_marked_correct = this.markedMainCorrectAnswer.splice(index, 1);
      this.mainAnswers.splice(index + 1, 0, removed[0]);
      this.markedMainCorrectAnswer.splice(index + 1, 0, removed_marked_correct[0]);

    }
  }
  /*
    This function gets an array of strings to detrmine a dynamic style for the buttons in which all the string will be
    so the buttons will be aligned according to the longest string
  */
  generateHighestBox(answers: Array<string>): string{
    let max: number = -1;
    for(let i = 0; i < answers.length; i++){
      if(answers[i].length > max){
        max = answers[i].length;
      }
    }
    let size = Math.max((max * 20), 300);

    let returnedSize: string = (size.toString()) + 'px';
    return returnedSize;
  }
  /*
    The function adds secondary question
  */
  addSecondaryQuestion(index: number){
    if(this.secondaryExists()){
      this.secondaryAnswerMode = !this.secondaryAnswerMode;
    }else{
      this.secondaryAnswerMode = true;
    }

  }

  /*
    The function returns if the chosen question is what the user wants to work on
  */
  whatToShow(index: number):boolean{
    return index == this.indexOfMainanswerToShow;
  }

  /*
    A function that cancels the secondary question
  */
  undoSecondary(){
    this.typedSecondaryAnswer = false;
    this.editionQuestionSecondary = false;
    this.submitSecondaryQuestion = false;
    this.secondaryAnswers = new Array();
    this.currentSecondaryQuestion = '';
    this.secondaryAnswerMode = false;
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(this.indexOfMainanswerToShow == this.secondaryQuestionList[i].index){
        this.secondaryQuestionList.splice(i,1);
      }
    }
  }

  /*
    The function creates secondary question from the details the user has input
  */
  finishSecondaryQuestion(){
    this.editionQuestionSecondary = false;
    if(!this.emptySecondary() && this.hasSecondaryAnswer()){
      for(let i = 0 ; i < this.secondaryQuestionList.length; i++){
        if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
          this.secondaryQuestionList.splice(i, 1);
        }
      }
      let correct_answer = -1;
      for(let i = 0; i < this.markedSecondaryCorrectAnswer.length; i++){
        if(this.markedSecondaryCorrectAnswer[i]){
          correct_answer = i;
        }

      }

      this.secondaryQuestionList.splice(this.secondaryQuestionList.length, 0, {
        index: this.indexOfMainanswerToShow,
        questionText: this.currentSecondaryQuestion,
        answers: this.secondaryAnswers,
        markedAnswer: correct_answer
      });
      this.secondaryAnswerMode = false;
      this.secondaryAnswers = new Array();
      this.currentSecondaryAnswer = '';

      this.currentSecondaryQuestion = '';
      this.markedSecondaryCorrectAnswer = new Array();
    }

    this.submitSecondaryQuestion = true;
    this.typedSecondaryAnswer = false;
  }
  /*
    The function returns TRUE if the secondary question have answers
  */
  hasSecondaryAnswer(): boolean{
    return this.secondaryAnswers.length >= 1;
  }

  /*
    The function returns TRUE if the secondary question exists and needs to be viewed
  */
  secondaryExists(): boolean{
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
        return true;
      }
    }
    return false;
  }
  /*
    The function returns TRUE if secondary question text wasn't filled
  */
  emptySecondary():boolean{
    return this.currentSecondaryQuestion == null || this.currentSecondaryQuestion.length < 2 || this.isSpacePrefix(this.currentSecondaryQuestion);
  }

  replaceSpacesByOneSpace(str) : string{
        return str.replace(/( )+/g," ");
  }

  /*
    The function adds an answer to the secondary question
  */
  addSecondaryAnswer(){
    if(this.currentSecondaryAnswer != null && this.currentSecondaryAnswer.length >= 1 && !this.isSpacePrefix(this.currentSecondaryAnswer)){
      var answer = this.replaceSpacesByOneSpace(this.currentSecondaryAnswer)
      this.secondaryAnswers.push(answer);
      this.markedSecondaryCorrectAnswer.splice(this.markedSecondaryCorrectAnswer.length, 0, false);
      this.currentSecondaryAnswer = '';
      this.typedSecondaryAnswer = false;
    }else{
      this.typedSecondaryAnswer = true;
    }
  }

  /*
    The function returns TRUE if the secondary question has answers
  */
  hasSecondaryAnswers(): boolean{
    return this.secondaryAnswers.length >= 1;
  }

  /*
    The function marks the correct answer by the user
  */
  markCorretSecondaryAnswer(index: number){
    for(let i = 0; i < this.markedSecondaryCorrectAnswer.length; i++){
      if(i == index){
        this.markedSecondaryCorrectAnswer[i] = !this.markedSecondaryCorrectAnswer[i];
      }else{
        this.markedSecondaryCorrectAnswer[i] = false;
      }
    }
  }
  /*
    The function moves the requested secondary question up
  */
  goSecondaryUp(index: number){
    if(index != 0){
      let removed = this.secondaryAnswers.splice(index, 1)
      let removed_marked_correct = this.markedSecondaryCorrectAnswer.splice(index,1);
      this.secondaryAnswers.splice(index - 1, 0, removed[0]);
      this.markedSecondaryCorrectAnswer.splice(index - 1, 0, removed_marked_correct[0]);
    }
  }
    /*
    The function moves the requested secondary question down
  */
  goSecondaryDown(index: number){
    if(index != this.secondaryAnswers.length - 1){
      let removed = this.secondaryAnswers.splice(index, 1);
      let removed_marked_correct = this.markedSecondaryCorrectAnswer.splice(index, 1);
      this.secondaryAnswers.splice(index + 1, 0, removed[0]);
      this.markedSecondaryCorrectAnswer.splice(index + 1, 0, removed_marked_correct[0]);

    }
  }
  /*
    The function deleted an answer to the secondary question
  */
  deleteSecondaryAnswer(index: number){
    this.secondaryAnswers.splice(index,1);
    this.markedSecondaryCorrectAnswer.splice(index, 1);
  }
  /*
    The function gets in edit mode to an answer of the secondary question
  */
  editSecondaryAnswer(index: number){
    this.currentSecondaryAnswer = this.secondaryAnswers[index];
    this.editionModeSecondary = true;
    this.indexAnswerInEditSecondary = index;
  }
  /*
    The function applies th changes of the edit mode in an answer of the seocndary question
  */
  applySecondaryEdit(){
    if(this.currentSecondaryAnswer != null && this.currentSecondaryAnswer.length >= 1 && !this.isSpacePrefix(this.currentSecondaryAnswer)){
      this.secondaryAnswers.splice(this.indexAnswerInEditSecondary, 1,  this.currentSecondaryAnswer)
      this.editionModeSecondary = false;
      this.indexAnswerInEditSecondary = -1;
      this.currentMainAnswer = '';
      this.currentSecondaryAnswer = '';
      this.typedSecondaryAnswer = false;
    }else{
      this.typedSecondaryAnswer = true;
    }
  }
  /*
    undos changes an answer of the secondary question
  */
  undoSecondaryEdit(){
    this.typedSecondaryAnswer = false;
    this.editionModeSecondary = false;
    this.currentSecondaryAnswer = '';
    this.indexAnswerInEditSecondary = -1;
  }
  /*
    The function gets into the view of a secondary question
  */
  viewSecondaryAnswer(){
    if(this.viewSecondary){
      this.viewAnswers = new Array();
      this.viewSecondary = false;
    }else{
      this.viewSecondary = true;
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
          this.questionView = this.secondaryQuestionList[i].questionText;
          this.viewAnswers = Object.assign([], this.secondaryQuestionList[i].answers);
          break;
        }
      }
    }
  }
  /*
    The function edits the whole secondary question
  */
  editSecondaryQuestion(){
    this.viewSecondary = false;
    this.editionQuestionSecondary = true;
    let correct_answer_index = -1;
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
        this.currentSecondaryQuestion = this.secondaryQuestionList[i].questionText;
        this.secondaryAnswers = Object.assign([], this.secondaryQuestionList[i].answers);
        correct_answer_index = this.secondaryQuestionList[i].markedAnswer;
        break;
      }
    }
    this.markedSecondaryCorrectAnswer = new Array(this.secondaryAnswers.length);
    for(let i = 0; i < this.markedSecondaryCorrectAnswer.length; i++){
      if(i == correct_answer_index){
        this.markedSecondaryCorrectAnswer[i] = true;
      }else{
        this.markCorretSecondaryAnswer[i] = false;
      }
    }
  }
  /*
    The function returns from view mode of a secondary question
  */
  backToView(){
    this.typedSecondaryAnswer = false;
    this.editionQuestionSecondary = false;
    this.viewSecondaryAnswer();
  }

  /*
    The function retunrs TRUE if the main question have answers
  */
  hasMainAnswres():boolean{
    return this.mainAnswers.length >= 1;
  }
  /*
    A function that returns true if the string starts with a white space, to control flawed input
  */
  isSpacePrefix(str: string): boolean{
      if(str == null){
          return false;
      }
      return str.charAt(0) == ' ';
  }

}
