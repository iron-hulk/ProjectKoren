package com.mobileagent.app.contextservice.ontology.environmental;

import java.util.HashMap;

import android.content.Context;
import android.hardware.SensorManager;

public class EnvironmentContext {

	private SensorManager sensorManager;
    private Context mContext;
    private ProximitySensorContext proximitySensorContext;

    public EnvironmentContext(Context context) {
        mContext = context;
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        proximitySensorContext = new ProximitySensorContext(sensorManager);
    }

    public HashMap<String, String> getEnvironmentContext() {
        HashMap<String, String> environmentContext = new HashMap<String, String>();

        environmentContext.putAll(proximitySensorContext.getProximityContext());

        return environmentContext;
    }

    public void cleanUpContextListeners() {
        proximitySensorContext.deRegisterProximitySensorListener();
    }
}
