package com.mobileagent.app.contextservice.ontology.environmental;

import java.util.HashMap;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximitySensorContext implements SensorEventListener{

	private static double SENSOR_MINIMUM = 0.0;
    private static String DEVICE_ENCLOSED = "Device Enclosed";
    private static String NOT_DEVICE_ENCLOSED = "Device Not Enclosed";

    private SensorManager proximitySensorManager;
    private Sensor proximitySensor;
    private HashMap<String, String> proximityContext;

    public ProximitySensorContext(SensorManager environmentSensorManager) {
        proximitySensorManager = environmentSensorManager;
        proximitySensor = proximitySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximityContext = new HashMap<String, String>();
        proximitySensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void deRegisterProximitySensorListener() {
        proximitySensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        buildProximityContext(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO: Not needed now
    }

    private void buildProximityContext(SensorEvent proximitySensorEvent) {

        if (proximitySensorEvent.values[0] > SENSOR_MINIMUM)
            proximityContext.put("device_enclosed", NOT_DEVICE_ENCLOSED);
        else
            proximityContext.put("device_enclosed", DEVICE_ENCLOSED);
    }

    public HashMap<String, String> getProximityContext() {
        return proximityContext;
    }
}
