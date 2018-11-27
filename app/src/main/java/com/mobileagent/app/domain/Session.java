package com.mobileagent.app.domain;

import java.util.List;

/**
 * Created by ironhulk on 02/10/2015.
 */
public class Session {

    private String UUID;
    private long startDateTime;
    private long endDateTime;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public long getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(long startDateTime) {
        this.startDateTime = startDateTime;
    }

    public long getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(long endDateTime) {
        this.endDateTime = endDateTime;
    }
}
