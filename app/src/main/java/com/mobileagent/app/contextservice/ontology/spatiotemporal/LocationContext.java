package com.mobileagent.app.contextservice.ontology.spatiotemporal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationContext {

    private Context mContext;
    private LinkedHashMap<String,String> locationContext;
    private LocationManager lb;

    public LocationContext(Context context) {
        mContext = context;
        locationContext = new LinkedHashMap<String, String>();
        lb = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void buildLocationContext(){
        Location current = getCurrentLocation();

        locationContext.putAll(getContextDetails(current));
    }

    private Location getCurrentLocation(){
        Location now = null;
        
        if(lb.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            lb.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,getLocationListener());
            now = lb.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lb.removeUpdates(getLocationListener());
        }
        
        if(lb.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            lb.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,getLocationListener());
            now = lb.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lb.removeUpdates(getLocationListener());
        }
        return now;
    }

    private LocationListener getLocationListener(){
        LocationListener l = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        return l;
    }

    private LinkedHashMap<String,String> getContextDetails(Location location) {
        LinkedHashMap<String,String> currentContext = new LinkedHashMap<String, String>();
        Geocoder gc = new Geocoder(mContext);
        try {
            List<Address> addressList = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addressList != null && addressList.size() > 0) {
                currentContext.put("address", addressList.get(0).getAddressLine(0));
                currentContext.put("code", addressList.get(0).getPostalCode());
                currentContext.put("country", addressList.get(0).getCountryName());
                currentContext.put("latitude", ""+location.getLatitude());
                currentContext.put("longitude",""+location.getLongitude());
//                currentContext.put("time_since_update", "" + location.getElapsedRealtimeNanos()/1000/1000/1000/60);
                currentContext.put("accuracy", "" + location.getAccuracy());
            	currentContext.put("speed", "" + location.getSpeed());
            }else{
            	currentContext.put("address", new String());
                currentContext.put("code", new String());
                currentContext.put("country", new String());
                currentContext.put("latitude", ""+location.getLatitude());
                currentContext.put("longitude",""+location.getLongitude());
//                currentContext.put("time_since_update", "" + location.getElapsedRealtimeNanos()/1000/1000/1000/60);
                currentContext.put("accuracy", "" + location.getAccuracy());
            	currentContext.put("speed", "" + location.getSpeed());
            }
            
        }catch(Exception io){
             Log.d("LOCATION CONTEXT","Error retrieving address");
        }
        return currentContext;
    }

    public HashMap<String,String> getLocationContext(){
        return locationContext;
    }
}
