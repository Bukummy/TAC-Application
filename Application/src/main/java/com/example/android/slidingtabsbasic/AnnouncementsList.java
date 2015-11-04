package com.example.android.slidingtabsbasic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AnnouncementsList extends Activity {
    String[] announcementTitles;
    String[] announcementLinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");


        String pastActivity = intent.getStringExtra("From");
        announcementTitles = intent.getStringArrayExtra("Titles");
        announcementLinks = intent.getStringArrayExtra("URLs");

        ListView list = (ListView) findViewById(R.id.AnnouncementListView);

        String[] announcements = new String[]{"No Announcements"};
        String[] linkTo = new String[]{""};
        String[] titleValue = new String[]{"Category","Tags","Favorite"};

        //Set announcements depending on which was chosen
        if (pastActivity.equals(titleValue[0])){
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            linkTo = displayAnnouncementList.getLinkTo();
        }
        else if (pastActivity.equals(titleValue[1])){
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            linkTo = displayAnnouncementList.getLinkTo();
        }
        else if (pastActivity.equals(titleValue[2])){
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            linkTo = displayAnnouncementList.getLinkTo();
        }
        else{
            announcements = new String[]{"No Announcement"};
            linkTo = new String[]{"http://www.techannounce.ttu.edu"};
        }
        final String[] finalAnnouncements = announcements;
        final String[] finalLink = linkTo;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, announcements);

        list.setAdapter(adapter);

        // View Chosen Category List

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                //TODO: Display webView of Announcement
                            Intent intent = new Intent(AnnouncementsList.this, DisplayAnnouncement.class);
                            intent.putExtra("Title", finalAnnouncements[position]);
                            intent.putExtra("URL",finalLink[position]);
                            startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_announcements_list, menu);
        return false;
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

        return super.onOptionsItemSelected(item);
    }

    private class DisplayAnnouncementList {
        private String title;
        private String[] announcements;
        private String[] linkTo;

        public DisplayAnnouncementList(String title) {
            this.title = title;
        }

        public String[] getAnnouncements() {
            return announcements;
        }

        public String[] getLinkTo() {
            return linkTo;
        }

        public DisplayAnnouncementList invoke() {
            setTitle(title + " List");
            switch (title){
                case "All Announcements":
                    announcements = announcementTitles;
                    linkTo = announcementLinks;
                    break;

                default:
                    announcements = new String[]{"No Announcement"};
                    linkTo = new String[]{"http://www.techannounce.ttu.edu"};
                    break;
            }
            return this;
        }
    }
}
