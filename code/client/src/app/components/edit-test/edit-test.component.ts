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
  selector: 'app-edit-test',
  templateUrl: './edit-test.component.html',
  styleUrls: ['./edit-test.component.css']
})
export class EditTestComponent implements OnInit {



  @ViewChildren(BlockComponent) blocks: QueryList<BlockComponent>;
  blocksList = []
  test: Test;
  iterator: Array<null> = new Array();
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
    this.iterator.splice(this.iterator.length, 0, null);
    console.log(this.blocks);
    this.blocksList = this.blocks.toArray();
    console.log(this.blocksList);
  }

  moveMeUp(currentIndex: number) {
    if(currentIndex != 0){
      let removed = this.blocksList.splice(currentIndex, 1);
      this.blocksList.splice(currentIndex - 1, 0, removed[0]);
    }
  }

  moveMeDown(currentIndex: number) {
    if(currentIndex != this.blocksList.length){
      let removed = this.blocksList.splice(currentIndex, 1);
      this.blocksList.splice(currentIndex + 1, 0, removed[0]);
    }
  }

  getQuestions(index: number){
    console.log(this.blocksList[index].getQuestions());
  }

}
