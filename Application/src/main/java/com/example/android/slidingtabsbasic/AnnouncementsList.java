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

import com.example.android.slidingtabsbasic.DAO.TechAnnounceCategoryDAO;
import com.example.android.slidingtabsbasic.DAO.TechAnnounceDAO;
import com.example.android.slidingtabsbasic.DAO.TechAnnounceKeyDAO;
import com.example.android.slidingtabsbasic.DAO.TechCategoryDAO;
import com.example.android.slidingtabsbasic.DAO.TechKeyDAO;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechAnnounceCategoryList;
import com.example.android.slidingtabsbasic.DBS.TechAnnounceKeyList;
import com.example.android.slidingtabsbasic.DBS.TechCategoryList;
import com.example.android.slidingtabsbasic.DBS.TechKeyList;

import java.util.ArrayList;


public class AnnouncementsList extends Activity {

    TechAnnounce techAnnounce = new TechAnnounce();
    TechCategoryList techCategoryList = new TechCategoryList();
    TechKeyList techKeyList = new TechKeyList();
    TechAnnounceKeyList techAnnounceKeyList = new TechAnnounceKeyList();
    TechAnnounceCategoryList techAnnounceCategoryList = new TechAnnounceCategoryList();

    TechAnnounceDAO techAnnounceDAO = new TechAnnounceDAO();
    TechCategoryDAO techCategoryDAO = new TechCategoryDAO();
    TechKeyDAO techKeyDAO = new TechKeyDAO();
    TechAnnounceKeyDAO techAnnounceKeyDAO = new TechAnnounceKeyDAO();
    TechAnnounceCategoryDAO techAnnounceCategoryDAO = new TechAnnounceCategoryDAO();

    String[] announcementTitles;
    String[] announcementLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        setTitle(title + " List");


        String pastActivity = intent.getStringExtra("From");
        announcementTitles = intent.getStringArrayExtra("Titles");
        announcementLinks = intent.getStringArrayExtra("URLs");

        ListView list = (ListView) findViewById(R.id.AnnouncementListView);

        String[] listName = new String[]{"Category", "Tags", "Favorite"};

        String[] announcements = new String[]{"No Announcements"};
        String[] links = new String[]{"http://www.techannounce.ttu.edu/"};

        //Set announcements depending on which was chosen
        if (pastActivity.equals(listName[0])) {
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title, 0).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            links = displayAnnouncementList.getLinks();

        }
        if (pastActivity.equals(listName[1])) {
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title, 1).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            links = displayAnnouncementList.getLinks();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.ann_list_style, R.id.tvAnn, announcements);

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
                intent.putExtra("URL", finalLinks[position]);
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
        private int list;
        private String[] announcements;
        private String[] links;

        public DisplayAnnouncementList(String title, int list) {
            this.list = list;
            this.title = title;
        }

        public String[] getAnnouncements() {
            return announcements;
        }

        public String[] getLinks() {
            return links;
        }

        public DisplayAnnouncementList invoke() {
            switch (list) {
                case 0:
                    switch (title) {
                        case "All Announcements":
                            announcements = announcementTitles;
                            links = announcementLinks;
                            break;

                        default:

                            ArrayList<TechAnnounceCategoryList> announcementCat =techAnnounceCategoryDAO.getAnnByCatId(
                                    techCategoryDAO.getCategoriesByName(title, getBaseContext()).getId(), getBaseContext());
                            String[] announcementsCatTitle = new String[announcementCat.size()];
                            String[] announcementsCatLink = new String[announcementCat.size()];
                            int val = 0;
                            for(TechAnnounceCategoryList techAnnounceCategoryList: announcementCat)
                            {
                                announcementsCatTitle[val++] = techAnnounceDAO.getAnnouncementsById(
                                        techAnnounceCategoryList.getA_Id(), getBaseContext()).getTitle();

                                announcementsCatLink[val++] = techAnnounceDAO.getAnnouncementsById(
                                        techAnnounceCategoryList.getA_Id(), getBaseContext()).getLink();
                            }
                            announcements = announcementsCatTitle;
                            links = announcementsCatLink;
                            break;
                    }
                    break;
                case 1:
                    switch (title) {
                        case "All Announcements":
                            announcements = announcementTitles;
                            links = announcementLinks;
                            break;

                        default:
                            announcements = new String[]{"No Announcements"};
                            links = new String[]{"http://www.techannounce.ttu.edu/"};
                            break;
                    }
                    break;

                default:
                    switch (title) {
                        case "All Announcements":
                            announcements = announcementTitles;
                            links = announcementLinks;
                            break;

                        default:
                            announcements = new String[]{"No Announcements"};
                            links = new String[]{"http://www.techannounce.ttu.edu/"};
                            break;
                    }
                    break;

            }
            return this;
        }
    }
}

