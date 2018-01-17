import { Component, OnInit } from '@angular/core';
import { Block, Test } from '../../models/index';
import { BlockComponent } from '../block/block.component';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material';
import { QuestionListComponent } from '../block/question-list/question-list.component';
import { SessionService } from '../../services/session-service/index';
import { TestService, TestManagerService } from '../../services/database-service/index';
import { AuthService } from '../../services/auth-service/index';
@Component({
  selector: 'app-edit-test',
  templateUrl: './edit-test.component.html',
  styleUrls: ['./edit-test.component.css']
})
export class EditTestComponent implements OnInit {

  blockList:BlockComponent[]=new Array();
  test: Test;



  constructor(
    private router:Router,
    private route: ActivatedRoute,
    private dialog:MatDialog,
    public questionDataService:SessionService,
    private testService: TestService, 
    private authService: AuthService,
    private managerService: TestManagerService
  ) { 
    this.blockList[0]=new BlockComponent(this.dialog,this.router,this.questionDataService);
    this.blockList[0].blockNumber=0;

    this.blockList[1]=new BlockComponent(this.dialog,this.router,this.questionDataService);
    this.blockList[1].blockNumber=1;
  }

  async ngOnInit() {
    let user = this.authService.getCurrentManager();
    let managerId = await this.managerService.getManagerId(user.email);
    managerId = '1';
    let testId = this.route.snapshot.params['testId'];
    this.test = await this.testService.findTestForManagerAndTestId(managerId,parseInt(testId));
    console.log(this.test);
  }

  addBlock(){
    console.log(this.blockList);
    let newIndex=this.blockList.length;
    this.blockList[newIndex]=new BlockComponent(this.dialog,this.router,this.questionDataService);
    this.router.navigate(['edit-test']);
    console.log(this.blockList);  
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
