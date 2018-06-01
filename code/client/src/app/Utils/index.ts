import {block_timing, question_timing, test_timing, TimeMeasurment, Block} from "../models/index";


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

  //the object that'll contain all the results from the timing collection.
  myTestObj: test_timing;
  // saves the next index to initialize a block in.
  private nextFreeBlock: number;

  //setting the initial required fields for measuring a test
  constructor( given_tID: number, given_tBlocksNum: number,blocksLengthArray:number[]) {
    this.nextFreeBlock = 0;
    //initializing my results object - 
    this.myTestObj = {tID:given_tID, tBlocksNum:given_tBlocksNum, tIsMeasured:false, tStartTS:0, tEndTS:0, tTotTS:0,resultArr:null }
    this.myTestObj.resultArr = new Array<block_timing>(this.myTestObj.tBlocksNum);
    for(let i=0;i<this.myTestObj.tBlocksNum;i++){
      this.myTestObj.resultArr[i] = {bID:0, bQuestionsNum:0, bIsMeasured:false, bStartTS:0, bEndTS:0, bTotTS:0, questionTimes:null};
      this.myTestObj.resultArr[i].questionTimes = new Array<question_timing>(blocksLengthArray[i]);
      let tmp = blocksLengthArray[i];
      for (let j= 0; j < tmp; j++) {
        this.myTestObj.resultArr[i].questionTimes[j] = {qID:-1, qIsMeasured:false, qBlockID:0, qConBarStartTS:0, qConBarEndTS:0,
        qConBarTotTS:0, qStartTS:0, qEndTS:0, qTotTS:0 }
      }
    }
  }

  /**
   * This function is called to generate a start TS for a test.
   * # No Input needed.
   * # Output - changes the tStartTS and tIsMeasured fields.
   */
  timing_startTestMeasure(): void {
    this.myTestObj.tStartTS = performance.now();
    this.myTestObj.tIsMeasured = true;
  }

  /**
   * This function is called to pause/finish time measurment for a test.
   * # No input needed.
   * # Output - changes the tEndTS, tTotTS and tIsMeasured fields.
   */
  timing_stopTestMeasure(): void {
    this.myTestObj.tEndTS = performance.now();
    this.myTestObj.tTotTS = (this.myTestObj.tEndTS - this.myTestObj.tStartTS);
    this.myTestObj.tIsMeasured = false;
  }

  /**
   * Quick internal work around for initializng a block_timing object inside the blocks list
   */
  private initialize_block_timing(new_bID: number, new_bQuestionNum: number, currObj: block_timing): void {
    currObj.bID = new_bID;
    currObj.bQuestionsNum = new_bQuestionNum;
  }

  /**
   * This function starts measuring time for a block.
   * @param given_bID
   * @param given_bQuestionNum
   */
  timing_startBlockMeasure(given_bID: number, given_bQuestionNum: number): void {
    let myCurrBlock:block_timing = this.myTestObj.resultArr[this.nextFreeBlock];
    this.nextFreeBlock++;
    

    //initialize the block object.
    this.initialize_block_timing(given_bID, given_bQuestionNum, myCurrBlock);
    //myCurrBlock = {bID:given_bID, bQuestionsNum:given_bQuestionNum, bIsMeasured:false, bStartTS:0, bEndTS:0, bTotTS:0, questionTimes:null};
   // myCurrBlock.questionTimes = new Array<question_timing>(myCurrBlock.bQuestionsNum);
    //for (let i = 0; i < myCurrBlock.bQuestionsNum; i++) {
    //  myCurrBlock.questionTimes[i] = {qID:-1, qIsMeasured:false, qBlockID:myCurrBlock.bID, qConBarStartTS:0, qConBarEndTS:0,
    //  qConBarTotTS:0, qStartTS:0, qEndTS:0, qTotTS:0 }
  //  }
    myCurrBlock.bStartTS = performance.now();
    myCurrBlock.bIsMeasured = true;
  }


  /**
   * This function checks if a block exists.
   * #Input - @param bID
   * #Output - the block's index in the resultsArray or -1 if it doesn't exist
   */
  private findBlock(given_bID: number): number {
    for (let i = 0; i < this.nextFreeBlock; i++) {
      if (this.myTestObj.resultArr[i]!= null && this.myTestObj.resultArr[i].bID == given_bID) {
        return i;
      }
    }
    return -1;
  }

  /**
   * This function resumes time measuring for a block.
   */
  timing_resumeBlockMeasure(given_bID: number): boolean {
    let myBlockIndex = this.findBlock(given_bID);
    if (myBlockIndex > -1) {
      let myCurrBlock = this.myTestObj.resultArr[myBlockIndex];
      if (myCurrBlock.bIsMeasured) {
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
  timing_stopBlockMeasure(given_bID: number): boolean {
    let myBlockIndex = this.findBlock(given_bID);
    if (myBlockIndex > -1) {
      let myCurrBlock = this.myTestObj.resultArr[myBlockIndex];
      if (myCurrBlock.bIsMeasured) {
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
  timing_startQuestionMeasure(given_bID: number, given_qID: number): boolean {
    //find the first place with a question object with id != -1
    let myBlockIndex = this.findBlock(given_bID);
    if (myBlockIndex > -1) {
      for (let i = 0; i < this.myTestObj.resultArr[myBlockIndex].bQuestionsNum; i++) {
        if (this.myTestObj.resultArr[myBlockIndex].questionTimes[i].qID == -1) {
          this.myTestObj.resultArr[myBlockIndex].questionTimes[i].qBlockID = given_bID;
          this.myTestObj.resultArr[myBlockIndex].questionTimes[i].qID = given_qID;
          this.myTestObj.resultArr[myBlockIndex].questionTimes[i].qStartTS = performance.now();
          this.myTestObj.resultArr[myBlockIndex].questionTimes[i].qIsMeasured = true;
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Internal function to find a question and return it's ref in the block's questions array, null otherwise
   */
  private findQuestion(given_qID: number, given_bID: number): question_timing {
    let myBlockIndex = this.findBlock(given_bID);
    if (myBlockIndex > -1) {
      let myCurrBlock = this.myTestObj.resultArr[myBlockIndex];
      for (let i = 0; i < myCurrBlock.bQuestionsNum; i++) {
        if (myCurrBlock.questionTimes[i].qID == given_qID) {
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
  timing_resumeQuestionMeasure(given_qID: number, given_bID: number): boolean {
    let myCurrQuestion = this.findQuestion(given_qID, given_bID);
    if (myCurrQuestion != null) {
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
  timing_stopQuestionMeasure(given_qID: number, given_bID: number): boolean {
    let myCurrQuestion = this.findQuestion(given_qID, given_bID);
    if (myCurrQuestion != null) {
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
  timing_startConfidenceMeasure(given_qID: number, given_bID: number): boolean {
    let myCurrQuestion = this.findQuestion(given_qID, given_bID);
    if (myCurrQuestion != null) {
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
  timing_stopConfidenceMeasure(given_qID: number, given_bID: number): boolean {
    let myCurrQuestion = this.findQuestion(given_qID, given_bID);
    if (myCurrQuestion != null) {
      myCurrQuestion.qConBarEndTS = performance.now();
      myCurrQuestion.qConBarTotTS = (myCurrQuestion.qConBarEndTS - myCurrQuestion.qConBarStartTS);
      return true;
    }
    return false;
  }

// -------------------------------------- SIMPLE RESULTS GETTERS ---------------------------
  getFullResults(): test_timing {
    return this.myTestObj
  }

  getQuestionTotalRunning(given_qID: number): number {
    for (let i = 0; i < this.myTestObj.tBlocksNum; i++) {
      let myBlockID = this.myTestObj.resultArr[i].bID;
      let tmpQuestion = this.findQuestion(given_qID, myBlockID);
      if (tmpQuestion != null) {
        return tmpQuestion.qTotTS;
      }
    }
    return -1;
  }

  getQuestionConBarTotalRunning(given_qID: number): number {
    for (let i = 0; i < this.myTestObj.tBlocksNum; i++) {
      let myBlockID = this.myTestObj.resultArr[i].bID;
      let tmpQuestion = this.findQuestion(given_qID, myBlockID);
      if (tmpQuestion != null) {
        return tmpQuestion.qConBarTotTS;
      }
    }
    return -1;
  }

  getBlockTotalRunning(given_bID: number): number {
    let myBlockIndex = this.findBlock(given_bID);
    if (myBlockIndex > -1) {
      return this.myTestObj.resultArr[myBlockIndex].bTotTS;
    }
    return -1;
  }

  getTestTotalRunning(): number {
    return this.myTestObj.tTotTS;
  }

  isBlockBeingMeasured(given_bID: number): boolean {
    let myBlockIndex = this.findBlock(given_bID);
    if (myBlockIndex > -1) {
      return this.myTestObj.resultArr[myBlockIndex].bIsMeasured;
    }
    return false;
  }

  isQuestionBeingMeasured(given_qID: number): boolean {
    for (let i = 0; i < this.myTestObj.tBlocksNum; i++) {
      let myBlockID = this.myTestObj.resultArr[i].bID;
      let tmpQuestion = this.findQuestion(given_qID, myBlockID);
      if (tmpQuestion != null) {
        return tmpQuestion.qIsMeasured;
      }
    }
    return false;
  }
}
