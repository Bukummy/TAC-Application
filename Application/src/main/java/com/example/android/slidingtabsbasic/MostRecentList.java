package com.example.android.slidingtabsbasic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.common.logger.Log;
import com.example.android.slidingtabsbasic.DAO.TechAnnounceDAO;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.RSSParser.HttpManager;
import com.example.android.slidingtabsbasic.RSSParser.TechAnnounceParser;

import java.util.ArrayList;
import java.util.List;

public class MostRecentList extends Activity {

        ListView listAnnouncement;
        ProgressBar pb;
        List<MyTask> tasks;

        List<TechAnnounce> techAnnounceList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_most_recent_list);

            Intent in = getIntent();
            String title = in.getStringExtra("Title");
            setTitle(title);
            String pastActivity = in.getStringExtra("From");


            pb = (ProgressBar) findViewById(R.id.load_progressBar);
            listAnnouncement = (ListView) findViewById(R.id.AnnouncementListView);
            pb.setVisibility(View.INVISIBLE);

            tasks = new ArrayList<>();

        }

    @Override
    protected void onStart() {

        if (isOnline()) {
            requestData("http://www.techannounce.ttu.edu/Client/ViewRss.aspx");
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
        super.onStart();
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_most_recent_list, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            // manually Refresh List
            if (item.getItemId() == R.id.action_get_data) {
                if (isOnline()) {
                    requestData("http://www.techannounce.ttu.edu/Client/ViewRss.aspx");
                } else {
                    Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
                }
            }
            return super.onOptionsItemSelected(item);
        }
        private void requestData(String uri) {
            MyTask task = new MyTask();
            task.execute(uri);
        }

        protected void updateDisplay() {
            if (techAnnounceList != null) {

                final String[] announcementTitle = new String[techAnnounceList.size()];
                final String[] announcementLink = new String[techAnnounceList.size()];

                int i = 0, j = 0;
                for (TechAnnounce techannounce : techAnnounceList) {
                    //list of current parsed data
                    announcementTitle[i++] = techannounce.getTitle();
                    announcementLink[j++] = techannounce.getLink();
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_2, android.R.id.text1, announcementTitle);
                listAnnouncement.setAdapter(adapter);

                listAnnouncement.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                        view.setSelected(true);

                        Intent intent = new Intent(MostRecentList.this, DisplayAnnouncement.class);

                        intent.putExtra("Title", announcementTitle[position]);
                        intent.putExtra("URL", announcementLink[position]);
                        MostRecentList.this.startActivity(intent);
                    }
                });

                listAnnouncement.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        TechAnnounceDAO techAnnounceDAO = new TechAnnounceDAO();
                        String link = techAnnounceDAO.getAnnouncementsByLink(announcementLink[position], getBaseContext()).getLink();
                        if (!(link == null) {
                            Toast.makeText(getBaseContext(), "Announcement Not Accessible Yet", Toast.LENGTH_LONG).show();
                            return false;
                        } 
                        else {
                            int updatedRow = techAnnounceDAO.updateSavedCol(1, announcementLink[position], getBaseContext());
                            adapter.notifyDataSetChanged();
                            if (updatedRow >= 1) {
                                android.util.Log.i("Updated Ann Row: ", String.valueOf(updatedRow));
                                view.setSelected(true);
                                Toast.makeText(getBaseContext(), "Announcement #" + (position + 1) + " is Saved", Toast.LENGTH_LONG).show();
                                return true;
                            } else {
                                Log.i("Updated Ann Row: ", "No update");
                                return false;
                            }
                        }
                    }
                });
            }
        }

        protected boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }

        private class MyTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                //updateDisplay("Starting task");

                if (tasks.size() == 0) {
                    pb.setVisibility(View.VISIBLE);
                }
                tasks.add(this);
            }

            @Override
            protected String doInBackground(String... params) {

                String content = HttpManager.getData(params[0]);
                return content;
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

    }


