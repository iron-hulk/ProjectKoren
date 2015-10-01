package com.mobileagent.app.contextservice.ontology.spatiotemporal;

import java.util.HashMap;

import android.content.Context;

public class SpatioTemporalContext {

	private Context mContext;
    private LocationContext locationContext;
    HashMap<String, String> spatioContext;

    public SpatioTemporalContext(Context context) {
        mContext = context;
        locationContext = new LocationContext(context);
        spatioContext = new HashMap<String, String>();
    }

    public HashMap<String, String> getSpatioTemporalContext() {
        locationContext.buildLocationContext();

        spatioContext.putAll(locationContext.getLocationContext());

        return spatioContext;
    }
}
