package com.example.android.slidingtabsbasic.AlarmManager;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.android.slidingtabsbasic.DAO.TechAnnounceCategoryDAO;
import com.example.android.slidingtabsbasic.DAO.TechAnnounceDAO;
import com.example.android.slidingtabsbasic.DAO.TechCategoryDAO;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechAnnounceCategoryList;
import com.example.android.slidingtabsbasic.MainActivity;
import com.example.android.slidingtabsbasic.R;
import com.example.android.slidingtabsbasic.RSSParser.HttpManager;
import com.example.android.slidingtabsbasic.RSSParser.TechAnnounceParser;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code TACAppAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class TACAppSchedulingService extends IntentService {

    List<TechAnnounce> techAnnounceList;

    TechAnnounceDAO techAnnounceDAO = new TechAnnounceDAO();
    TechAnnounceCategoryList techAnnounceCategoryList = new TechAnnounceCategoryList();
    TechAnnounceCategoryDAO techAnnounceCategoryDAO = new TechAnnounceCategoryDAO();
    TechCategoryDAO techCategoryDAO = new TechCategoryDAO();


    Calendar calendar = Calendar.getInstance();
    //one week in millisec
    long diff = 604800;

    long thisTime = calendar.getTimeInMillis();
    //plus  1 minute
    long extraTime = thisTime + (60) ;
    long pastWeekT = extraTime - diff;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = formatter.format(new Timestamp(extraTime));

    //Timestamp currentTime = Timestamp.valueOf(currentT);
    //String currentTimeTD = String.valueOf(currentTime);

    String pastWeek = formatter.format(new Timestamp(pastWeekT));
    //Timestamp pastWeek = Timestamp.valueOf(pastWeekS);
    //String pastWeekTS = String.valueOf(pastWeek);


    public TACAppSchedulingService() {
        super("SchedulingService");
    }

    public static final String TAG = "Conn To Tech Announce";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    //URL it parses from
    public static final String URL = "http://www.techannounce.ttu.edu/Client/ViewRss.aspx";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)
        // The URL from which to fetch content.
        String urlString = URL;
        if (isOnline()) {
            // Try to connect to the TechAnnounce RSS Feed and download content.
            try {
                String result = loadFromNetwork(urlString);
                techAnnounceList = TechAnnounceParser.parseFeed(result);
            } catch (IOException e) {
                Log.i(TAG, getString(R.string.connection_error));
            }

            //if parseableURl, send notification
            if (techAnnounceList != null) {

                for (TechAnnounce techannounce :techAnnounceList ) {

                    int compareAnnouncement;
                    String availLinkInDB = techAnnounceDAO.getAnnouncementsByLink(
                            techannounce.getLink(), getBaseContext()).getLink();

                    String  availIdTitleDB = techAnnounceDAO.getAnnouncementsByLink(
                            techannounce.getLink(), getBaseContext()).getTitle();

                    String availIdDesDB = techAnnounceDAO.getAnnouncementsByLink(
                            techannounce.getLink(), getBaseContext()).getDescription();

                    String[] availCategoriesDB = techAnnounceDAO.getAnnouncementsByLink(
                            techannounce.getLink(), getBaseContext()).getCategoryList();

                    int availIdInDB = techAnnounceDAO.getAnnouncementsByLink(
                            techannounce.getLink(), getBaseContext()).getId();

                    if (availLinkInDB == null) {
                        //perform insertion
                        int insertedRow = techAnnounceDAO.insert(techannounce, getBaseContext());

                        String value = String.valueOf(insertedRow);
                        //log progress
                        if (insertedRow > 0) {
                            Log.i("Inserted Row NO:", value);
                            techCategoryDAO.checkCategoryList(techannounce, insertedRow, getBaseContext());
                        }
                        else {
                            Log.i("No Inserted Row:", value);
                        }
                    }
                    //if link is available
                    else {
                        //perform update

                        int updatedRow = techAnnounceDAO.update(techannounce, availIdInDB , getBaseContext());
                        String value = String.valueOf(updatedRow);
                        if (updatedRow > 0) {
                            techCategoryDAO.checkCategoryList(techannounce, availIdInDB, getBaseContext());
                            Log.i("Updated Row NO:", value);

                        } else {
                            Log.i("No Updated Row:", value);
                        }

                    }

                    TechAnnounce announcement = techAnnounceDAO.getAnnouncementsB4Date(pastWeekT, extraTime, getBaseContext());
                    int id = announcement.getId();
                    if ( id > 1){
                        int deletedRow = techAnnounceDAO.delete(id, getBaseContext());

                        if (techAnnounceCategoryList.getA_Id() == id){
                            int deletedACRow = techAnnounceCategoryDAO.delete(id ,getBaseContext());
                            String val = String.valueOf(deletedRow);
                            if (deletedACRow >= 1 ) {
                                Log.i("Deleted Row NO:", val);
                            } else {
                                Log.i("No Deleted Row:", val);
                            }
                        }

                        String value = String.valueOf(deletedRow);
                        if (deletedRow >= 1) {
                            Log.i("Deleted Row NO:", value);
                        } else {
                            Log.i("No Deleted Row:", value);
                        }

                    }

                }

                    sendNotification(getString(R.string.announcement_found));
                Log.i(TAG, "Updated TechAnnounce!!");
            } else {
                sendNotification(getString(R.string.no_announcement));
                Log.i(TAG, "No Updated TechAnnounce. :-(");
            }
            // Release the wake lock provided by the BroadcastReceiver.
            TACAppAlarmReceiver.completeWakefulIntent(intent);

        }
        else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent= new Intent(this, MainActivity.class);

//        intent.putExtra("Title", techAnnounce(.getTitle()t.get());
//        intent.putExtra("URL", announcementLink[position]);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notif)
                        .setContentTitle(getString(R.string.announcement_alert))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

//
// The methods below this line fetch content from the specified URL and return the
// content as a string.
//
    /** Given a URL string, initiate a fetch operation. */
    private String  loadFromNetwork(String urlString) throws IOException {
        String content = HttpManager.getData(urlString);
        return content;
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
