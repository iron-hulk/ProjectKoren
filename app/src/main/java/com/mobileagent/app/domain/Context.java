package com.mobileagent.app.domain;

/**
 * Created by ironhulk on 02/10/2015.
 */
public class Context {

    private String uuid;
    private long contextTimeStamp;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getContextTimeStamp() {
        return contextTimeStamp;
    }

    public void setContextTimeStamp(long contextTimeStamp) {
        this.contextTimeStamp = contextTimeStamp;
    }
}
