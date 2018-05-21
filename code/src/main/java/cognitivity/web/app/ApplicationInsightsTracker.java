package cognitivity.web.app;

/**
 * Created by ophir on 07/05/18.
 * <p>
 * this interface defines protocol for delegation and propagation of simple tracking commands to
 * ApplicationInsightsTracker's logic. Uses the configured application insight instrumentation key.
 */
public interface ApplicationInsightsTracker {

    void trackEvent(String eventKey);

    void trackFailure(Exception e);

}
