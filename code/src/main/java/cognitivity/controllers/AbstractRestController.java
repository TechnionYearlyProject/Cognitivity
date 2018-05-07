package cognitivity.controllers;


import cognitivity.web.app.CognitivityApplicationInsights;

/**
 * Created by ophir on 17/12/17.
 */
public abstract class AbstractRestController<ServiceType> {

    protected CognitivityApplicationInsights applicationInsights = CognitivityApplicationInsights.getInstance();

    protected static final String crossOrigin = "http://localhost:4200";

    protected final ServiceType service;

    public AbstractRestController(ServiceType service) { this.service = service; }

}
