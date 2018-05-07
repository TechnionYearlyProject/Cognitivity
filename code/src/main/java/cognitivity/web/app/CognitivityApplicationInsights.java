package cognitivity.web.app;

import com.microsoft.applicationinsights.TelemetryClient;

/**
 * Created by ophir on 07/05/18.
 */
public class CognitivityApplicationInsights implements ApplicationInsightsTracker {

    private TelemetryClient telemetry = new TelemetryClient();
    private static CognitivityApplicationInsights instance;

    public static CognitivityApplicationInsights getInstance() {
        if (instance == null) {
            instance = new CognitivityApplicationInsights();
        }
        return instance;
    }

    // Private access for singleton
    private CognitivityApplicationInsights() {

    }

    @Override
    public void trackEvent(String eventKey) {
        telemetry.trackEvent(eventKey);
    }

    @Override
    public void trackFailure(Exception e) {
        telemetry.trackException(e);
    }

}
