import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: 'app-create-rate-question',
  templateUrl: './create-rate-question.component.html',
  styleUrls: ['./create-rate-question.component.css']
})
export class CreateRateQuestionComponent implements OnInit {
  rateSize : number;
  @Input() question: any;
  /*
    Determining the size of the range of the rate question that is created
  */
  increaseRate(){
    if(this.rateSize < 15){
        this.rateSize++;
    }
  }
  decreaseRate(){
    if(this.rateSize > 1){
      this.rateSize--;
    }
  }
  constructor() {
    


   }

  ngOnInit() {
    if(this.question != null){
      this.rateSize = this.question.heightOfRate;
    }else{
      this.rateSize = 2;
    }
  }

}
