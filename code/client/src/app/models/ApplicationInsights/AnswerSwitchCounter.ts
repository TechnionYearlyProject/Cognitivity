/**
 * Created by ophir on 21/05/18.
 */

/**
 * This class represents Answer-Switch-Counter for answer switch tracking.
 *
 * An object of this class will be passes to ApplicationInsightsTracker's method to track
 * the number of answer switches a subject has done while answering a message.
 *
 * How to use:
 * 1. To get the instance of the tracker, simply use SwitchCounterTracker.getNewSwitchCounter().
 * 2. Use it when beginning to answer a question.
 * 3. Every time a answer switch occurs, call switchAnswer().
 *
 * */
export class SwitchCounterTracker {
  private counter: number;

  private constructor(newCounter: number) {
    this.counter = newCounter;
  }

  /**
   * Posts a switch answer event.
   * */
  public switchAnswer(): void {
    this.counter++;
  }

  /**
   * Returns the answer switch counter.
   * */
  public get getSwitchCount(): number {
    return this.counter;
  }

  /**
   * Implementation of the singleton pattern.
   * */
  public static get getNewSwitchCounter() {
    return this._instance || (this._instance = new this(0));
  }

  private static _instance: SwitchCounterTracker;
}
