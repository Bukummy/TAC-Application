package com.example.android.slidingtabsbasic.AlarmManager;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.slidingtabsbasic.DAO.TechCategoryDAO;
import com.example.android.slidingtabsbasic.DBS.TechCategoryList;

/**
 * Created by Bukunmi on 11/15/2015.
 */
public class OneTimeRun extends Application {

    TACAppAlarmReceiver alarm = new TACAppAlarmReceiver();
    TechCategoryDAO techCategoryDAO = new TechCategoryDAO();
    TechCategoryList techCategoryList = new TechCategoryList();

    @Override
    public void onCreate() {
        super.onCreate();

           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("", false)) {

            String[] categoryList = new String[]{
                    "Academic",
                    "Administration & Finance Information Systems Management (AFISM)",
                    "Arts  Entertainment",
                    "Athletic",
                    "Computing Equipment",
                    "Departamental",
                    "Departmental Events",
                    "Events",
                    "Faculty/Staff Organization",
                    "Fundraiser",
                    "HR Talent Development",
                    "IT Announcements",
                    "Lectures & Seminars",
                    "Non Computing Equipment",
                    "Orientation",
                    "Rec Sports Programing",
                    "Research",
                    "Small Business Development Center",
                    "Student Employment/Career Opportunities",
                    "Student Organization",
                    "Teaching, Learning & Professional Development Center",
                    "Training",
                    "TTU IT Training"};

            //create DB with Categories
            for (String catName : categoryList) {
                int id = techCategoryDAO.getCategoriesByName(
                        catName, getBaseContext()).getId();
                if (id <= 0) {
                    int insertedRow = techCategoryDAO.insert(catName, getBaseContext());
                    String value = String.valueOf(insertedRow);
                    //log progress
                    if (insertedRow <= 0) {
                        Log.i("Inserted Row NO:", value);
                    } else {
                        Log.i("No Inserted Row:", value);
                    }
                } else {
                    Log.i("No Inserted Row:", "NONE");
                }
            }

            alarm.setAlarm(this);

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("", true);
            editor.commit();




        }
    }
}
