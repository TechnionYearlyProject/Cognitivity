import {test_timing,block_timing,question_timing, TimeMeasurment} from "../models/index";
import {Block,Question,Test} from "../models/index";


/**
 * Author: Ben
 *  
 * SHORT API SUMMARY
 * 
 * --------------Arguments for the class constructor--------------------
 * # @param tID - the test ID.
 * # @param tBlockNum - the number of blocks in the test.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


export class TimeMeasurer {
  
  //An object of TimeMeasurement interface that contains the test object.
  timingObject: TimeMeasurment;


  /*
  private currTest: Test;
  // list of the test's blocks.
  private blocks: Block[];
  // the test ID number.
  private currTestID: number;
*/

  constructor(given_tID:number,given_tBlocksNum:number) {
    let myTestObj = this.timingObject.testObject;

    /*this.currTest = test;
    this.currTestID = this.currTest.id;
    this.blocks = this.currTest.blocks;*/
  }
  
  /**
   * this function is called to generate a starting timestamp for a test.
   * or resuming a test measurment.
   *
  startTestMeasure(): void {
      this.currTest.testStartTimestamp = performance.now();
      this.currTest.isBeingMeasured = true;
      
      //for DEBUGGING
      console.log("starting to measure time for test with ID: "+this.currTestID);
      console.log("starting time is: "+this.currTest.testStartTimestamp);
  }*/

  /**
   * This function is called to pause the measurment of test's time execution.
   * it Sets the current measured time ***diff*** (the total running time of the test).
   
  stopTestMeasure(): void {
    this.currTest.testEndTimestamp = performance.now();
    this.currTest.testTotalRunningTime = this.currTest.testEndTimestamp - this.currTest.testStartTimestamp;
    this.currTest.isBeingMeasured = false;

    // for DEBUGGING
    console.log("finishing measuring time for test with ID: "+this.currTestID);
    console.log("finishing time is: "+this.currTest.testEndTimestamp);
    console.log("this diff measured is: "+this.currTest.testTotalRunningTime);
  }*/

  /**
   * checks in the list of blocks if the passed id exists.
   *
  private isBlockIdPresent(id:number): Block{
      for (let i in this.blocks){
        if(this.blocks[i].id == id){
          return this.blocks[i];
        }
      }
      return null;
  }*/

  /**
   * checks in the list of question in a block if a question id's exists.
   *
  private isQuestionPresent(currBlock:Block,questionId:number){
    for (let i in currBlock.questions){
      if(currBlock.questions[i].id == questionId){
        return currBlock.questions[i];
      }
    }
    return null;
  }*/

  /**
   * called to start measure time for a block. 
   * gets the  blocks @param id. 
   * if the blocks id isn't found in the passed blocks list when initializing the object - does nothing.
   *
  startBlockMeasure(id: number): void {
    let tmpBlock = this.isBlockIdPresent(id);
    if(tmpBlock != null){
      tmpBlock.blockStartTimestamp = performance.now();
      tmpBlock.isBeingMeasured = true;
      //for DEBUGGING
      console.log("Block with id: "+id+" is present in the test! (started measuring)");
      console.log("starting time is: "+tmpBlock.blockStartTimestamp);      
    }
  }*/

  /**
   * stops measuring time for block with the given id.
   * if  the id isn't found - does nothing.
   * @param id
   *
  stopBlockMeasure(id: number): void {
    let tmpBlock = this.isBlockIdPresent(id);
    if(tmpBlock != null){
      tmpBlock.blockEndTimestamp = performance.now();
      tmpBlock.blockTotalRunningTime = tmpBlock.blockEndTimestamp - tmpBlock.blockStartTimestamp;
      tmpBlock.isBeingMeasured = false;
      //for DEBUGGING
      console.log("Block with id "+id+" is present in the test! (stopped measuring)");
      console.log("the ending time is: "+tmpBlock.blockEndTimestamp);
      console.log("the total running time is: "+tmpBlock.blockTotalRunningTime);
    }
  }*/

  /**
   * @param blockId id of the block
   * @param questionId id of the question being measured
   *
  startQuestionMesure(blockId: number, questionId: number): void {
      //make sure the block is present
      let tmpBlock = this.isBlockIdPresent(blockId);
      if(tmpBlock != null){
        let currQuestion = this.isQuestionPresent(tmpBlock,questionId);
        if(currQuestion != null){
          currQuestion.startTS = performance.now();
          currQuestion.isBeingMeasured = true;
          //for DEBUGGING 
          console.log("question with id: "+questionId+" is present! (started measuring)");
          console.log("starting time is: "+currQuestion.startTS);
        }
      }
  }*/

  /**
   * @param blockId id of the block
   * @param questionId id of the question being measured
   *
  stopQuestionMesure(blockId: number, questionId: number): void {
       //make sure the block is present
       let tmpBlock = this.isBlockIdPresent(blockId);
       if(tmpBlock != null){
         let currQuestion = this.isQuestionPresent(tmpBlock,questionId);
         if(currQuestion != null){
           currQuestion.endTS = performance.now();
           currQuestion.diffTS = currQuestion.endTS - currQuestion.startTS;
           currQuestion.isBeingMeasured = false;
          //for DEBUGGING 
          console.log("question with id: "+questionId+" is present! (stopped measuring)");
          console.log("ending time is: "+currQuestion.endTS);
          console.log("total running time is: "+currQuestion.diffTS);
         }
            
        }


  
  }*/
}