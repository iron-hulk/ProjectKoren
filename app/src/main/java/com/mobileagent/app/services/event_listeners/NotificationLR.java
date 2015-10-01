package com.mobileagent.app.services.event_listeners;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by ironhulk on 2014/08/05.
 */

public class NotificationLR extends NotificationListenerService{
    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }

}
