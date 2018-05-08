export class TimeMeasurer {
  
  private block: any[];
  constructor(private testId: number) {}
  startTestMeasure(): void {

  }

  stopTestMeasure(): void {

  }

  resumeTestMeasure(): void {

  }
  /**
   * @return id of the block for measuring
   */
  addBlockForMeasure(): number {
    return 0; // surpress compiler
  }

  startBlockMeasure(id: number): void {

  }

  stopBlockMeasure(id: number): void {

  }

  resumeBlockMeasure(id: number): void {

  }

  /** 
   * @param blockId id of the block that holds the question
   * @returns id of the question inside the block
   */
  addQuestionForMeasure(blockId: number): number {
    return 0;
  }

  /**
   * @param blockId id of the block
   * @param questionId id of the question being measured
   */
  startQuestionMesure(blockId: number, questionId: number): void {

  }

  /**
   * @param blockId id of the block
   * @param questionId id of the question being measured
   */
  stopQuestionMesure(blockId: number, questionId: number): void {

  }

  /**
   * @param blockId id of the block
   * @param questionId id of the question being measured
   */
  resumeQuestionMesure(blockId: number, questionId: number): void {

  }


}