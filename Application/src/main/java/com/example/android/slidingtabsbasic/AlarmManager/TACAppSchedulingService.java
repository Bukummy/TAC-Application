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

import com.example.android.slidingtabsbasic.AppContent.TechAnnounce;
import com.example.android.slidingtabsbasic.MainActivity;
import com.example.android.slidingtabsbasic.R;
import com.example.android.slidingtabsbasic.RSSParser.HttpManager;
import com.example.android.slidingtabsbasic.RSSParser.TechAnnounceParser;

import java.io.IOException;
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
    //List<String[]> announcementCategories = new ArrayList<>();
    String[] announcementTitles = new String[]{};
    String[] announcementURLs = new String[]{};


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
                int i = 0,j = 0;
                for (TechAnnounce techannounce :techAnnounceList ) {
                    techannounce.getCategoryList();
                    techannounce.getTitle();
                    techannounce.getLink();
                    techannounce.getDescription();
                    techannounce.getCategoryList();
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
