package cognitivity.controllers;


import cognitivity.web.app.CognitivityApplicationInsights;

/**
 * Created by ophir on 17/12/17.
 */
public abstract class AbstractRestController<ServiceType> {

    protected static final String crossOriginLocal = "http://localhost:4200";
    protected static final String crossOriginRemote = "https://cognitivityfrontend.azurewebsites.net";

    protected final ServiceType service;
    protected CognitivityApplicationInsights applicationInsights = CognitivityApplicationInsights.getInstance();

    public AbstractRestController(ServiceType service) {
        this.service = service;
    }

}
