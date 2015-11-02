package com.team2.bukunmioyedeji.tac;

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

import com.example.android.slidingtabsbasic.DisplayAnnouncement;
import com.example.android.slidingtabsbasic.R;

import java.util.ArrayList;
import java.util.List;

public class AllAnnouncementsList extends Activity {

    ListView listAnnouncement;
    ProgressBar pb;
    List<MyTask> tasks;

    List<TechAnnounce> techAnnounceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_announcements);

        Intent in = getIntent();
        String title = in.getStringExtra("Title");
        setTitle(title);

        listAnnouncement = (ListView) findViewById(R.id.AnnouncementListView);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();


        String pastActivity = in.getStringExtra("From");



    }
    protected void onStart() {
        super.onStart();
        if (isOnline()) {
            requestData("http://www.techannounce.ttu.edu/Client/ViewRss.aspx");
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_announcements, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
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
        if (techAnnounceList != null){

            final String[] announcementTitle = new String[techAnnounceList.size()];
            final String[] announcementLink = new String[techAnnounceList.size()];

            int i = 0,j = 0;
            for (TechAnnounce techannounce :techAnnounceList ) {
                announcementTitle[i++] = techannounce.getTitle();
                announcementLink[j++] = techannounce.getLink();
            }
            createIntent(announcementTitle, announcementLink);
        }

    }

    protected void createIntent(final String[] announcementTitle, final String[] announcementLink) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_2, android.R.id.text1,announcementTitle );

        listAnnouncement.setAdapter(adapter);

        listAnnouncement.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);

                Intent intent = new Intent(AllAnnouncementsList.this, DisplayAnnouncement.class);

                intent.putExtra("Title", announcementTitle[position]);
                intent.putExtra("URL", announcementLink[position]);
                AllAnnouncementsList.this.startActivity(intent);
            }
        });
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

            if (tasks.isEmpty()) {
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
