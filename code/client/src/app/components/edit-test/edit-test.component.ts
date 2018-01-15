import { Component, OnInit } from '@angular/core';
import { Block } from '../../models/index';
import { BlockComponent } from '../block/block.component';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { QuestionListComponent } from '../block/question-list/question-list.component';
import { SessionService } from '../../services/session-service/index';
@Component({
  selector: 'app-edit-test',
  templateUrl: './edit-test.component.html',
  styleUrls: ['./edit-test.component.css']
})
export class EditTestComponent implements OnInit {

  blockList:BlockComponent[]=new Array();



  constructor(private router:Router,private dialog:MatDialog,public questionDataService:SessionService) { 
    this.blockList[0]=new BlockComponent(this.dialog,this.router,this.questionDataService);
    this.blockList[0].blockNumber=0;
  }

  ngOnInit() {
  }

  moveMeUp(currentIndex) {
    let arrayLastIndex = this.blockList.length - 1;
    if (currentIndex == 0) {
      let tmpMe = this.blockList.shift();
      for (let num = 0; num < arrayLastIndex; num++) {
        this.blockList[num].blockNumber--;
      }
      this.blockList.push(tmpMe);
      tmpMe.blockNumber = arrayLastIndex;
    }
    let tmpHolder = this.blockList[currentIndex - 1];
    let mememe = this.blockList[currentIndex];
    mememe.blockNumber--;
    tmpHolder.blockNumber++;
    this.blockList[currentIndex - 1] = mememe;
    this.blockList[currentIndex] = tmpHolder;
  }

  moveMeDown(currentIndex) {
    let arrayLastIndex = this.blockList.length - 1;
    if (currentIndex == arrayLastIndex) {
      let tmpMe = this.blockList.pop();
      for (let num = arrayLastIndex-1; num > 0; num--) {
        this.blockList[num].blockNumber++;
      }
      this.blockList.unshift(tmpMe);
      tmpMe.blockNumber = 0;
    }
    let tmpHolder = this.blockList[currentIndex + 1];
    let mememe = this.blockList[currentIndex];
    mememe.blockNumber++;
    tmpHolder.blockNumber--;
    this.blockList[currentIndex + 1] = mememe;
    this.blockList[currentIndex] = tmpHolder;
  }

}
