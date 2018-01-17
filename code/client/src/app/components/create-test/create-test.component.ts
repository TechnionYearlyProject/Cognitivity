import { Component, OnInit } from '@angular/core';
import { Block, Test } from '../../models/index';
import { BlockComponent } from '../block/block.component';
import { Router, ActivatedRoute } from '@angular/router';
import { QuestionListComponent } from '../block/question-list/question-list.component';
import { SessionService } from '../../services/session-service/index';
import { TestService, TestManagerService } from '../../services/database-service/index';
import { AuthService } from '../../services/auth-service/index';
import { QueryList, ViewChildren  } from '@angular/core';

@Component({
  selector: 'app-create-test',
  templateUrl: './create-test.component.html',
  styleUrls: ['./create-test.component.css']
})
export class CreateTestComponent implements OnInit {


  @ViewChildren(BlockComponent) blocks: QueryList<BlockComponent>;
  blocksList = []
  test: Test;
  iterator: Array<Object> = new Array();
  titleTest: string;

  ngAfterViewInit() {

    
  }

  constructor(
    private router:Router,
    private route: ActivatedRoute,
    public questionDataService:SessionService,
    private testService: TestService, 
    private authService: AuthService,
    private managerService: TestManagerService
  ) { 
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
    this.iterator.splice(this.iterator.length, 0, new Object());
  }

  moveMeUp(currentIndex: number) {
    if(currentIndex != 0){
      let removed = this.iterator.splice(currentIndex, 1);
      this.iterator.splice(currentIndex - 1, 0, removed[0]);
    }
  }

  moveMeDown(currentIndex: number) {
    if(currentIndex != this.blocksList.length){
      let removed = this.iterator.splice(currentIndex, 1);
      this.iterator.splice(currentIndex + 1, 0, removed[0]);
    }
  }

  getQuestions(index: number){
    this.blocksList = this.blocks.toArray();
    console.log(this.blocksList[index].getQuestions());
  }

  printQueryList(){
    console.log(this.blocks);
    console.log('print: ')
    console.log(this.blocks.toArray())
  }


  deleteBlock(index: number){
    console.log('In delete');
    this.iterator.splice(index,1);
  }
  

}
