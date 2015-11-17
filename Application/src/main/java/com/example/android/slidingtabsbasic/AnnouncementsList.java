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
<<<<<<< HEAD
=======


>>>>>>> database
    String[] announcementTitles;
    String[] announcementLinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
<<<<<<< HEAD

=======
        setTitle(title+ " List");
>>>>>>> database

        String pastActivity = intent.getStringExtra("From");
        announcementTitles = intent.getStringArrayExtra("Titles");
        announcementLinks = intent.getStringArrayExtra("URLs");

        ListView list = (ListView) findViewById(R.id.AnnouncementListView);

        String[] listName = new String[]{"Category","Tags","Favorite"};

        String[] announcements = new String[]{"No Announcements"};
<<<<<<< HEAD
        String[] linkTo = new String[]{""};
        String[] titleValue = new String[]{"Category","Tags","Favorite"};

        //Set announcements depending on which was chosen
        if (pastActivity.equals(titleValue[0])){
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            linkTo = displayAnnouncementList.getLinkTo();
=======
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
>>>>>>> database
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


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.ann_list_style, R.id.tvAnn, announcements);

        list.setAdapter(adapter);

        // View Chosen Category List
<<<<<<< HEAD
=======
        final String[] finalAnnouncements = announcements;
        final String[] finalLinks = links;
>>>>>>> database

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                //TODO: Display webView of Announcement
<<<<<<< HEAD
                            Intent intent = new Intent(AnnouncementsList.this, DisplayAnnouncement.class);
                            intent.putExtra("Title", finalAnnouncements[position]);
                            intent.putExtra("URL",finalLink[position]);
                            startActivity(intent);
=======
                Intent intent = new Intent(AnnouncementsList.this, DisplayAnnouncement.class);
                intent.putExtra("Title", finalAnnouncements[position]);
                intent.putExtra("URL",finalLinks[position]);
                startActivity(intent);
>>>>>>> database
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
        //int id = item.getItemId();

        switch (item.getItemId()) {
            // When the user clicks START ALARM, set the alarm.
            case R.id.start_action:
                //alarm.setAlarm(this);
                return true;
            // When the user clicks CANCEL ALARM, cancel the alarm.
            case R.id.cancel_action:
                //alarm.cancelAlarm(this);
                return true;
        }
        return false;


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

<<<<<<< HEAD
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
=======

>>>>>>> database
}
