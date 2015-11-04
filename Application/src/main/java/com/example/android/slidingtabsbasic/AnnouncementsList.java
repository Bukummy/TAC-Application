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
        setTitle(title+ " List");

        String pastActivity = intent.getStringExtra("From");
        announcementTitles = intent.getStringArrayExtra("Titles");
        announcementLinks = intent.getStringArrayExtra("URLs");

        ListView list = (ListView) findViewById(R.id.AnnouncementListView);

        String[] listName = new String[]{"Category","Tags","Favorite"};

        String[] announcements = new String[]{"No Announcements"};
        String[] links = new String[]{"http://www.techannounce.ttu.edu/"};

        //Set announcements depending on which was chosen
        if (pastActivity.equals(listName[0])){
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            links = displayAnnouncementList.getLinks();

        }
        if (pastActivity.equals(listName[1])){
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            links = displayAnnouncementList.getLinks();
        }

        if (pastActivity.equals(listName[2])){
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            links = displayAnnouncementList.getLinks();
        }




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, announcements);

        list.setAdapter(adapter);

        // View Chosen Category List
        final String[] finalAnnouncements = announcements;
        final String[] finalLinks = links;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                //TODO: Display webView of Announcement
                            Intent intent = new Intent(AnnouncementsList.this, DisplayAnnouncement.class);
                            intent.putExtra("Title", finalAnnouncements[position]);
                            intent.putExtra("URL",announcementLinks[position]);
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
        private String[] links;

        public DisplayAnnouncementList(String title) {
            this.title = title;
        }

        public String[] getAnnouncements() {
            return announcements;
        }

        public String[] getLinks() {
            return links;
        }

        public DisplayAnnouncementList invoke() {
            switch (title){
                case "All Announcements":
                    announcements = announcementTitles;
                    links = announcementLinks;
                    break;

                default:
                    announcements = new String[]{"No Announcements"};
                    links = new String[]{"http://www.techannounce.ttu.edu/"};
                    break;
            }
            return this;
        }
    }
}
