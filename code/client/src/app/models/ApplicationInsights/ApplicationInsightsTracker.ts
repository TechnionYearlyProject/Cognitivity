/**
 * Created by ophir on 21/05/18.
 */

import {AppInsights} from "applicationinsights-js";
import {SwitchCounterTracker} from "./AnswerSwitchCounter";
import {TimeMeasurer} from "../../Utils";
import {environment} from "./environment";


/**
 * This is the Azure's Application-Insights service's instrumentation key. It identifies the web-app
 * and allows it to track events that will be shown in the Azure portal.
 * */
const instrumentation_key = 'bf85656a-0c3e-4a2d-a744-fdffa181700b';


/**
 * This class represents the Application-Insights-Tracker.
 *
 * Can be used to track user experience, and also events that affect
 * the subject's cognitive process while filling a test.
 *
 * How to use:
 * 1. This class is a singleton. To get the instance of the tracker, simply
 *    use ApplicationInsightTracker.getInstance().
 * 2. Use it when a question is being submitted, and call the correct methods.
 * 3. To track an event, simply use the dedicated method, out of the following:
 *    * track number of answer switches per question.
 *    * track amount of elapsed time per question.
 * */

export class ApplicationInsightsTracker {
  private appInsights: Microsoft.ApplicationInsights.IAppInsights;

  /**
   * Constructor and configuration.
   * */
  private config: Microsoft.ApplicationInsights.IConfig = {
    instrumentationKey: environment.appInsights.instrumentationKey
  };

  private constructor() {
    if (!AppInsights.config) {
      AppInsights.downloadAndSetup(this.config);
    }
  }


  /**
   * @param switchTracker - The SwitchCounterTracker object that tracked the
   *                        question's answer switches.
   * @param questionId - The question's id.
   * @param questionType - The question's type.
   * */
  public trackNumberOfAnswersSwitches(switchTracker: SwitchCounterTracker,
                                      questionId: number,
                                      questionType: string) {
    const name = "answerSwitches";
    let properties = {
      "questionId": String(questionId),
      "questionType": questionType
    };
    let measurements = {
      "numberOfSwitcher": switchTracker.getSwitchCount
    };
    console.log('track switch answers');
    AppInsights.trackEvent(name, properties, measurements);
  }

  public trackQuestionElapsedTime(timeMeasurements: TimeMeasurer,
                                  questionId: number,
                                  questionType: string) {
    const name = "questionElapsedTime";
    let properties = {
      "questionId": String(questionId),
      "questionType": questionType
    };
    let measurements = {
      "totalElapsedTime": timeMeasurements.getQuestionTotalRunning(questionId),
      "confidenceBarElapsedTime": timeMeasurements.getQuestionConBarTotalRunning(questionId)
    };
    AppInsights.trackEvent(name, properties, measurements);
  }

  /**
   * Implementation of the singleton pattern.
   * */
  public static get getInstance() {
    return this._instance || (this._instance = new ApplicationInsightsTracker());
  }

  private static _instance: ApplicationInsightsTracker;
}
