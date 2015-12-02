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
import com.example.android.slidingtabsbasic.DAO.TechKeyDAO;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechAnnounceCategoryList;
import com.example.android.slidingtabsbasic.MainActivity;
import com.example.android.slidingtabsbasic.R;
import com.example.android.slidingtabsbasic.RSSParser.HttpManager;
import com.example.android.slidingtabsbasic.RSSParser.TechAnnounceParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code TACAppAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class TACAppSchedulingService extends IntentService {

    private List<TechAnnounce> techAnnounceList;

    private final TechAnnounceDAO techAnnounceDAO = new TechAnnounceDAO();
    private final TechAnnounceCategoryList techAnnounceCategoryList = new TechAnnounceCategoryList();
    private final TechAnnounceCategoryDAO techAnnounceCategoryDAO = new TechAnnounceCategoryDAO();
    private final TechCategoryDAO techCategoryDAO = new TechCategoryDAO();
    private final TechKeyDAO techKeyDAO = new TechKeyDAO();

    private final Calendar calendar = Calendar.getInstance();
    //one week in millisec
    private final long diff = 604800 * 1000;

    private final long thisTime = calendar.getTimeInMillis();
    //plus  5 minute
    private final long extraTime = thisTime + (5*60*1000) ;
    private final long pastWeekT = extraTime - diff;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTimeX = formatter.format(new Timestamp(extraTime));

    Timestamp currentTime = Timestamp.valueOf(currentTimeX);
    //String currentTimeTD = String.valueOf(currentTime);

    //String pastWeek = formatter.format(new Timestamp(pastWeekT));
    //Timestamp pastWeek = Timestamp.valueOf(pastWeekS);
    //String pastWeekTS = String.valueOf(pastWeek);


    public TACAppSchedulingService() {
        super("SchedulingService");
    }

    private static final String TAG = "Conn To Tech Announce";
    // An ID used to post the notification.
    private static final int NOTIFICATION_ID = 1;
    //URL it parses from
    private static final String URL = "http://www.techannounce.ttu.edu/Client/ViewRss.aspx";
    //private static final String api_key = "1d4e852374c1e857eb39a0a3b1cf1472";
    private static final String api_key = "659edf6262926fd91cbff013cb49490f";
    private static final String n = "2";

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
                    String urlExtractString =
                            "http://api.datumbox.com/1.0/KeywordExtraction.json?"+"api_key="+api_key+"&n="+n+"&text="+
                                    techannounce.getDescription().replace(" ", "%20");
                    ArrayList<String> extractedKeys = new ArrayList<>();

                    try {
                        String result = loadFromNetwork(urlExtractString);
                        /**
                         * extrackKeyPhrase takes in the result from Loading from network and
                         * extract the keys in Json format turned into an array
                         */
                        extractedKeys = extractKeyPhrase(result);
                    } catch (IOException e) {
                        Log.i(TAG, getString(R.string.connection_error));
                    }


                    String availLinkInDB = techAnnounceDAO.getAnnouncementsByLink(
                            techannounce.getLink(), getBaseContext()).getLink();

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
                            if (extractedKeys != null){
                                techKeyDAO.checkKeyList(extractedKeys, insertedRow, getBaseContext());}
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
                            if (extractedKeys != null){
                                techKeyDAO.checkKeyList(extractedKeys, availIdInDB, getBaseContext());}
                        } else {
                            Log.i("No Updated Row:", value);
                        }

                    }

                    TechAnnounce announcement = techAnnounceDAO.getAnnouncementsB4Date(pastWeekT, extraTime, getBaseContext());
                    int id = announcement.getId();
                    if ( id >= 1){
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
        NotificationManager mNotificationManager = (NotificationManager)
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
        return HttpManager.getData(urlString);
    }
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private ArrayList<String> extractKeyPhrase(String result) {
        ArrayList<String> key_phrases = new ArrayList<>();
        try{
            JSONObject jsonRootObject = new JSONObject(result);
            JSONObject output = jsonRootObject.getJSONObject("output");
            JSONObject kwresult =output.getJSONObject("result");
            JSONObject bigram =kwresult.getJSONObject("2");

            Iterator<?> keys = bigram.keys();
            int i=0;
            //String key_phrase ="";
            //get the first two KeyPhrases

            while( keys.hasNext() && i < 2) {
                String key = (String)keys.next();
                //This is the key_phrase(bigram);
                //key_phrase = key;
                key_phrases.add(key);

                //log the first bigram KeyPhraseExtract. I just use the first bigram KeyPhraseExtractionTask.
                // if we want to show more KeyPhraseExtractionTask, just change the number of the while loop.
                Log.i("KeyList", String.valueOf(key));
                i++;

            }
            key_phrases.size();


        }catch (JSONException e) {e.printStackTrace();}
        return key_phrases;
    }
}
