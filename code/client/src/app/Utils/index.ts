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
 * --------------timing_startTestMeasure()-------------------------------
 * # Starts measuring time for a test.
 * # No input required.
 * 
 * ------------timing_stopTestMeasure()----------------------------------
 * # Ends measuring time for a test. 
 * # No input needed.
 * 
 * ------------timing_startBlockMeasure()--------------------------------
 * # Initializes a new block_timing object in the results array and sets
 * the bStartTS and bIsMeasured fields.
 * # Input: @param bID and @param bQuestionNum needed.
 * 
 * ------------timing_resumeBlockMeasure()-------------------------------
 * Same as the above, without initializing new fields.
 * #Input - @param bID - the block to be resumed.
 * #Output - true is successfully resumed, false otherwise.
 * 
 * 
 * ------------timing_stopBlockMeasure()---------------------------------
 * # Ends measuring time for a block.
 * # Input: @param bID to be stopped.
 * # Output: updates bTotTS
 * 
 * -----------timing_startQuestionMeasure()------------------------------
 * #Starts measuring time for a single question and initializes the needed fields.
 * # Input - @param qID of the question to be measured and @param bID of the containing block.
 * # Output - True if successfully started the time measurment, false otherwise.
 * 
 * -----------timing_resumeQuestionMeasure()-----------------------------
 * #Same as the above without initializing.
 * # Input - @param qID of the question to be measured and @param bID of the containing block.
 * # Output - True if successfully started the time measurment, false otherwise.
 * 
 * -----------timing_stopQuestionMeasure()-------------------------------
 * #Stops timing measurment for a question.
 * # Input - @param qID of the question to be measured and @param bID of the containing block.
 * # Output - True if successfully stopped the time measurment, false otherwise.
 * 
 * ------------timing_startConfidenceMeasure()-----------------------------
 * #Starts measuring confidence bar for a single question.
 * # Input - @param qID of the question to be measured and @param bID of the containing block.
 * #Output - True/false.
 * can be used to resume time measurment as well.
 * 
 * ------------timing_stopConfidenceMeasure()------------------------------
 * # Stops time measurment for the confidence bar.
 * # Input - @param qID of the question to be measured and @param bID of the containing block.
 * #Output - True/False.
 * 
 * 
 * 
 * 
 * 
 * --------------------------------RESULTS EASY GETTERS-----------------------------------------
 * getFullResults()
 * # Returns the whole object. (test_timing)
 * 
 * getQuestionTotalRunning(@param qID)
 * # Returns the total running time of a question. (number)
 * 
 * getQuestionConBarTotalRunning(@param qID)
 * # Returns the total running time of a question's confidence bar. (number)
 * 
 * getBlockTotalRunning(@param bID)
 * # Returns the total running time of a block. (number)
 * 
 * getTestTotalRunning()
 * # Returns the total running time of a test. (number)
 * 
 * isBlockBeingMeasured(@param bID)
 * # Returns True if  the block is being measured, false otherwise. (boolean)
 * 
 * isQuestionBeingMeasured(@param qID)
 * # Returns True if the question is being measured, false otherwise. (boolean)
 */


export class TimeMeasurer {
  
  //An object of TimeMeasurement interface that contains the test object.
  private timingObject: TimeMeasurment;
  private myTestObj:test_timing;
  // saves the next index to initialize a block in.
  private nextFreeBlock: number;

  //setting the initial required fields for measuring a test
  constructor(givenTimeMeasurmentObj:TimeMeasurment,given_tID:number,given_tBlocksNum:number) {
    this.timingObject = givenTimeMeasurmentObj;
    this.nextFreeBlock = 0;
    this.myTestObj = this.timingObject.testObject;
    this.myTestObj.tID = given_tID;
    this.myTestObj.tBlocksNum = given_tBlocksNum;
    this.myTestObj.tIsMeasured = false;
    this.myTestObj.tStartTS = 0;
    this.myTestObj.tEndTS = 0;
    this.myTestObj.tTotTS = 0;
    this.myTestObj.resultArr = new Array[this.myTestObj.tBlocksNum]; 
  }
  
  /**
   * This function is called to generate a start TS for a test.
   * # No Input needed.
   * # Output - changes the tStartTS and tIsMeasured fields. 
   */
  timing_startTestMeasure(): void{
    this.myTestObj.tStartTS = performance.now();
    this.myTestObj.tIsMeasured = true;
    // for DEBUGGING
    //console.log("Starting to measure time for test with ID: "+myTestObj.tID);
    //console.log("tStartTS is: "+myTestObj.tStartTS);
  }

  /**
   * This function is called to pause/finish time measurment for a test.
   * # No input needed.
   * # Output - changes the tEndTS, tTotTS and tIsMeasured fields.
   */
  timing_stopTestMeasure(): void{
    this.myTestObj.tEndTS = performance.now();
    this.myTestObj.tTotTS = (this.myTestObj.tEndTS - this.myTestObj.tStartTS);
    this.myTestObj.tIsMeasured = false;
    // for DEBUGGING
    //console.log("finished to measure time for test with ID: "+myTestObj.tID);
    //console.log("tEndTS is: "+myTestObj.tEndTS);
    //console.log("tTotTS is: "+myTestObj.tTotTS);
  }

  /**
   * Quick internal work around for initializng a block_timing object inside the blocks list
   */
  private initialize_block_timing(new_bID:number,new_bQuestionNum:number,currObj:block_timing):void{
    currObj.bID = new_bID;
    currObj.bQuestionsNum = new_bQuestionNum;
    currObj.bIsMeasured = false;
    currObj.bStartTS = 0;
    currObj.bEndTS = 0;
    currObj.bTotTS = 0;
    currObj.questionTimes = new Array[currObj.bQuestionsNum];
    for(let i = 0; i < currObj.bQuestionsNum; i++){
      currObj.questionTimes[i].qID = -1;
      currObj.questionTimes[i].qIsMeasured = false;
      currObj.questionTimes[i].qBlockID = currObj.bID;
      currObj.questionTimes[i].qConBarStartTS = 0;
      currObj.questionTimes[i].qConBarEndTS = 0;
      currObj.questionTimes[i].qConBarTotTS = 0;
      currObj.questionTimes[i].qStartTS = 0;
      currObj.questionTimes[i].qEndTS = 0;
      currObj.questionTimes[i].qTotTS = 0;
    }
  }

  /**
   * This function starts measuring time for a block.
   * @param given_bID 
   * @param given_bQuestionNum 
   */
  timing_startBlockMeasure(given_bID:number,given_bQuestionNum:number): void{
      let myCurrBlock = this.myTestObj.resultArr[this.nextFreeBlock];
      this.nextFreeBlock++;
      //initialize the block object.
      this.initialize_block_timing(given_bID,given_bQuestionNum,myCurrBlock);
      myCurrBlock.bStartTS = performance.now();
      myCurrBlock.bIsMeasured = true;
      //for DEBUGGING
      //console.log("Start measure Block with id: "+myCurrBlock.bID);
      //console.log("bStartTS is: "+myCurrBlock.bStartTS);      
  }

  
  /**
   * This function checks if a block exists.
   * #Input - @param bID
   * #Output - the block's index in the resultsArray or -1 if it doesn't exist
   */
  private findBlock(given_bID:number):number{
    for(let i = 0; i < this.nextFreeBlock; i++) {
      if(this.myTestObj.resultArr[i].bID == given_bID){
        return i;
      }
    }
    return -1;
  }

  /**
   * This function resumes time measuring for a block.
   */
  timing_resumeBlockMeasure(given_bID:number): boolean{
    let myBlockIndex =  this.findBlock(given_bID);
    if(myBlockIndex > -1){
      let myCurrBlock = this.myTestObj.resultArr[myBlockIndex];
      if(myCurrBlock.bIsMeasured){
        return false;
      }
      myCurrBlock.bStartTS = performance.now();
      myCurrBlock.bIsMeasured = true;
      return true;
    }
    return false;
  }


  /**
   * This function pauses/ends time measuring for a block
   * # Input - @param bID 
   * # Output - True if successfully stopped the measurment, false otherwise.
   */
  timing_stopBlockMeasure(given_bID:number): boolean{
    let myBlockIndex = this.findBlock(given_bID);
    if(myBlockIndex > -1){
      let myCurrBlock = this.myTestObj.resultArr[myBlockIndex];
      if(myCurrBlock.bIsMeasured){
        myCurrBlock.bEndTS = performance.now();
        myCurrBlock.bTotTS = (myCurrBlock.bEndTS - myCurrBlock.bStartTS);
        myCurrBlock.bIsMeasured = false;
        return true;
      }
    }
    return false;
  }


  /**
   * This function starts time measuring for a single question.
   * # Input: @param bID - the containing block's ID. and @param qID 
   */
  timing_startQuestionMeasure(given_bID:number,given_qID:number): boolean{
    //find the first place with a question object with id != -1
    let myBlockIndex = this.findBlock(given_bID);
    if(myBlockIndex > -1){
      let myCurrBlock = this.myTestObj.resultArr[myBlockIndex];
      for(let i = 0; i < myCurrBlock.bQuestionsNum; i++){
        if(myCurrBlock.questionTimes[i].qID == -1){
          myCurrBlock.questionTimes[i].qID = given_qID;
          myCurrBlock.questionTimes[i].qStartTS = performance.now();
          myCurrBlock.questionTimes[i].qIsMeasured = true;
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Internal function to find a question and return it's ref in the block's questions array, null otherwise
   */
  private findQuestion(given_qID:number,given_bID:number):question_timing{
    let myBlockIndex = this.findBlock(given_bID);
    if(myBlockIndex > -1){
      let myCurrBlock = this.myTestObj.resultArr[myBlockIndex];
      for(let i = 0; i < myCurrBlock.bQuestionsNum; i++){
        if(myCurrBlock.questionTimes[i].qID == given_qID){
          return myCurrBlock.questionTimes[i];
        }
      }
    }
    return null;
  }

  /**
   * This function resumes time measuring for a question.
   * # Input - @param qID of the question to be measured and @param bID of the containing block.
   * # Output - True if successfully started the time measurment, false otherwise.
   */
  timing_resumeQuestionMeasure(given_qID:number,given_bID:number):boolean{
    let myCurrQuestion = this.findQuestion(given_qID,given_bID);
    if(myCurrQuestion != null){
      myCurrQuestion.qStartTS = performance.now();
      myCurrQuestion.qIsMeasured = true;
      return true;
    }
    return false;
  }

  /**
   * #Stops timing measurment for a question.
   * # Input - @param qID of the question to be measured and @param bID of the containing block.
   * # Output - True if successfully stopped the time measurment, false otherwise.
   * */
  timing_stopQuestionMeasure(given_qID:number,given_bID:number):boolean{
    let myCurrQuestion = this.findQuestion(given_qID,given_bID);
    if(myCurrQuestion != null){
      myCurrQuestion.qEndTS = performance.now();
      myCurrQuestion.qTotTS = (myCurrQuestion.qEndTS - myCurrQuestion.qStartTS);
      myCurrQuestion.qIsMeasured = false;
      return true;
    }
    return false;
  }  

  /**
   *#Starts measuring confidence bar for a single question.
   * # Input - @param qID of the question to be measured and @param bID of the containing block.
   * #Output - True/false.
   * can be used to resume time measurment as well.  
   */
  timing_startConfidenceMeasure(given_qID:number,given_bID:number):boolean{
    let myCurrQuestion = this.findQuestion(given_qID,given_bID);
    if(myCurrQuestion != null){
      myCurrQuestion.qConBarStartTS = performance.now();
      return true;
    }
    return false;
  }

  /**
   * # Stops time measurment for the confidence bar.
   * # Input - @param qID of the question to be measured and @param bID of the containing block.
   * #Output - True/False. 
   */
  timing_stopConfidenceMeasure(given_qID:number,given_bID:number):boolean{
    let myCurrQuestion = this.findQuestion(given_qID,given_bID);
    if(myCurrQuestion != null){
      myCurrQuestion.qConBarEndTS = performance.now();
      myCurrQuestion.qConBarTotTS = (myCurrQuestion.qConBarEndTS - myCurrQuestion.qConBarStartTS);
      return true;
    }
    return false;
  }

// -------------------------------------- SIMPLE RESULTS GETTERS ---------------------------
getFullResults():test_timing{
  return this.myTestObj
}  

getQuestionTotalRunning(given_qID:number):number{
  for(let i = 0; i < this.myTestObj.tBlocksNum; i++){
    let myBlockID = this.myTestObj.resultArr[i].bID;
    let tmpQuestion = this.findQuestion(given_qID,myBlockID);
    if(tmpQuestion != null){
      return tmpQuestion.qTotTS;
    }
  }
  return -1;
}

getQuestionConBarTotalRunning(given_qID:number):number{
  for(let i = 0; i < this.myTestObj.tBlocksNum; i++){
    let myBlockID = this.myTestObj.resultArr[i].bID;
    let tmpQuestion = this.findQuestion(given_qID,myBlockID);
    if(tmpQuestion != null){
      return tmpQuestion.qConBarTotTS;
    }
  }
  return -1;
}

getBlockTotalRunning(given_bID:number):number{
  let myBlockIndex = this.findBlock(given_bID);
  if(myBlockIndex > -1){
    return this.myTestObj.resultArr[myBlockIndex].bTotTS;
  }
  return -1;
}

getTestTotalRunning():number{
  return this.myTestObj.tTotTS;
}

isBlockBeingMeasured(given_bID:number):boolean{
  let myBlockIndex = this.findBlock(given_bID);
  if(myBlockIndex > -1){
    return this.myTestObj.resultArr[myBlockIndex].bIsMeasured;
  }
  return false;
}

isQuestionBeingMeasured(given_qID:number):boolean{
  for(let i = 0; i < this.myTestObj.tBlocksNum; i++){
    let myBlockID = this.myTestObj.resultArr[i].bID;
    let tmpQuestion = this.findQuestion(given_qID,myBlockID);
    if(tmpQuestion != null){
      return tmpQuestion.qIsMeasured;
    }
  }
  return false;
}
}