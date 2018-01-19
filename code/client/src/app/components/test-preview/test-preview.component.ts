import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { LocalStorageService } from '../../services/local-storage';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Test, Question, OpenQuestion, TypeQuestion, QuestionPosition, Block, QuestionInDB } from '../../models';
import { TestService, TestManagerService } from '../../services/database-service/index';

@Component({
  selector: 'app-test-preview',
  templateUrl: './test-preview.component.html',
  styleUrls: ['./test-preview.component.css']
})

/*
this component takes place when the preview button is fired (to preview the whole test)
it uses the block viewer and indirectly the question viewer to view the whole test.
*/
export class TestPreviewComponent implements OnInit {
  
  //default constructor 
  constructor(
    private localStorageService: LocalStorageService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private testService: TestService,
    private tmService: TestManagerService
  ) { }

  //the test object , so we can save/import tests.
  test: Test;
  //the current test id.
  testId: number;
  //the current test's index in the tests list.
  currIndex: number;
  //variable to indicate if we should hide the following button in the creation.
  hideNextButton: boolean = true;
  //variable to hold the blocks array
  blocks: Block[];
  //variable that hold the blocks array length
  blocksLength: number;

  //default initialization function.
  async ngOnInit() {
   
    let email = this.authService.getCurrentManager().email;
    let managerId = await this.tmService.getManagerId(email);
    let testId = this.route.snapshot.params['testId'];
    this.test = await this.testService.findTestForManagerAndTestId(managerId, testId);
    this.blocks = this.test.blocks;
    this.blocksLength = this.blocks.length;
    console.log(this.test);
    this.currIndex = 0;
  }

  /*
  Input - an index of a specific block
  Output - returns a boolean result if the called block's id (from the HTML form) is the current id 
  were viewing. 
  */
  showBlock(index) {
    return index == this.currIndex;
  }

  /*
  to indicate if we finished previewing the test.
  */
  isFinished(e) {
    this.hideNextButton = !e;
  }

  //routing back to the dashboard after we finished previewing the test.
  finishPreview() {
    this.router.navigate(['/dashboard']);
  }

  /*
  this function increments our block index (the index of the block were viewing)
  and checks if we finished (reached the last object in the blocks list)
  */
  nextBlock() {
    this.currIndex++;
    this.hideNextButton = true;
    if (this.currIndex == this.test.blocks.length) {
      this.finishPreview();
    }
  }

}
