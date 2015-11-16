/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.slidingtabsbasic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.slidingtabsbasic.RSSParser.HttpManager;
import com.example.android.slidingtabsbasic.AppContent.TechAnnounce;
import com.example.android.slidingtabsbasic.RSSParser.TechAnnounceParser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";


    ListView listAnnouncement;
    ProgressBar pb;
    List<MyTask> tasks;
    List<TechAnnounce> techAnnounceList;
    //List<String[]> announcementCategories = new ArrayList<>();
    String[] announcementTitles = new String[]{};
    String[] announcementURLs = new String[]{};

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    Bundle state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        state = savedInstanceState;

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

        for(int i = 0;i < announcementTitles.length;i++){
            Log.e("Title", announcementTitles[i]);
        }

    }

    protected void onStart() {
        super.onStart();
        if (isOnline()) {
            requestData();
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    private void requestData() {
        MyTask task = new MyTask();
        task.execute("http://www.techannounce.ttu.edu/Client/ViewRss.aspx");
    }

    protected void updateDisplay() {
        if (techAnnounceList != null){

            final String[] announcementTitle = new String[techAnnounceList.size()];
            final String[] announcementLink = new String[techAnnounceList.size()];
            final ArrayList <String[]> announcementCategory = new ArrayList<>();
            String[] categories = new String[techAnnounceList.size()];

            int i = 0,j = 0;
            for (TechAnnounce techannounce :techAnnounceList ) {
                categories = techannounce.getCategoryList();
                announcementTitle[i++] = techannounce.getTitle();
                announcementLink[j++] = techannounce.getLink();
                announcementCategory.add(categories);

            }
            //createIntent(announcementTitle, announcementLink);
            announcementTitles = announcementTitle;
            announcementURLs = announcementLink;


            Bundle announcementsBundle = new Bundle();
            announcementsBundle.putStringArray("Announcement Titles", announcementTitles);
            announcementsBundle.putStringArray("Announcement URLs", announcementURLs);
//            announcementsBundle.putStringArrayList("Announcement Category", categories);
            if (state == null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
                fragment.setArguments(announcementsBundle);
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.commit();
            }

        }

    }

//    protected void createIntent(final String[] announcementTitle, final String[] announcementLink) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_2, android.R.id.text1,announcementTitle );
//
//        listAnnouncement.setAdapter(adapter);
//
//        listAnnouncement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
//                view.setSelected(true);
//
//                Intent intent = new Intent(AllAnnouncementsList.this, DisplayAnnouncement.class);
//
//                intent.putExtra("Title", announcementTitle[position]);
//                intent.putExtra("URL", announcementLink[position]);
//                AllAnnouncementsList.this.startActivity(intent);
//            }
//        });
//      }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");

            if (tasks.isEmpty()) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {

            return HttpManager.getData(params[0]);
        }



        @Override
        protected void onPostExecute(String result) {

            techAnnounceList = TechAnnounceParser.parseFeed(result);

            updateDisplay();

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }    techAnnounceList = TechAnnounceParser.parseFeed(result);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            //		updateDisplay(values[0]);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
