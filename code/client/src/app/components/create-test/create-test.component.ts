import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-test',
  templateUrl: './create-test.component.html',
  styleUrls: ['./create-test.component.css']
})
export class CreateTestComponent implements OnInit {
  isHidden: boolean = true;

  styles = {
    'accordion' : this.isHidden,
    'active': !this.isHidden,
  };
  divStyles = {
    'panel' : !this.isHidden
  };
  constructor() { }

  onClick(){
    this.isHidden = !this.isHidden;
    this.styles.accordion = this.isHidden;
    this.styles.active = !this.isHidden;

  }

  ngOnInit() {
  }

}
