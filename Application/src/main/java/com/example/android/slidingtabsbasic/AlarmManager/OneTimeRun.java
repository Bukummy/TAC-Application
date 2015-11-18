package com.example.android.slidingtabsbasic.AlarmManager;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Bukunmi on 11/15/2015.
 */
public class OneTimeRun extends Application {

    TACAppAlarmReceiver alarm = new TACAppAlarmReceiver();

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            alarm.setAlarm(this);

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }
}
