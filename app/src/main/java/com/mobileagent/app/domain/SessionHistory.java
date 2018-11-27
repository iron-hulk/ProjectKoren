package com.mobileagent.app.domain;

/**
 * Created by ironhulk on 02/10/2015.
 */
public class SessionHistory {
    private String eventDetected;
    private String eventPlanSetup;
    private String eventHandled;
    private long eventTimeStamp;

    public String getEventDetected() {
        return eventDetected;
    }

    public void setEventDetected(String eventDetected) {
        this.eventDetected = eventDetected;
    }

    public String getEventPlanSetup() {
        return eventPlanSetup;
    }

    public void setEventPlanSetup(String eventPlanSetup) {
        this.eventPlanSetup = eventPlanSetup;
    }

    public String getEventHandled() {
        return eventHandled;
    }

    public void setEventHandled(String eventHandled) {
        this.eventHandled = eventHandled;
    }

    public long getEventTimeStamp() {
        return eventTimeStamp;
    }

    public void setEventTimeStamp(long eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
    }
}
