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
import android.widget.Toast;

import com.example.android.common.logger.Log;
import com.example.android.slidingtabsbasic.DAO.TechAnnounceCategoryDAO;
import com.example.android.slidingtabsbasic.DAO.TechAnnounceDAO;
import com.example.android.slidingtabsbasic.DAO.TechCategoryDAO;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechAnnounceCategoryList;

import java.util.ArrayList;


public class AnnouncementsList extends Activity {
    //TechAnnounce techAnnounce = new TechAnnounce();
    //TechCategoryList techCategoryList = new TechCategoryList();
    //TechKeyList techKeyList = new TechKeyList();
    //TechAnnounceKeyList techAnnounceKeyList = new TechAnnounceKeyList();
    //TechAnnounceCategoryList techAnnounceCategoryList = new TechAnnounceCategoryList();

    private final TechAnnounceDAO techAnnounceDAO = new TechAnnounceDAO();
    private final TechCategoryDAO techCategoryDAO = new TechCategoryDAO();
    //TechKeyDAO techKeyDAO = new TechKeyDAO();
    //TechAnnounceKeyDAO techAnnounceKeyDAO = new TechAnnounceKeyDAO();
    private final TechAnnounceCategoryDAO techAnnounceCategoryDAO = new TechAnnounceCategoryDAO();

    private String[] announcementTitles;
    private String[] announcementLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");

        setTitle(title + " List");


        String pastActivity = intent.getStringExtra("From");
        announcementLinks = intent.getStringArrayExtra("URLs");
        announcementTitles = intent.getStringArrayExtra("Titles");

        final ListView list = (ListView) findViewById(R.id.AnnouncementListView);

        String[] listName = new String[]{"Category", "Tags", "Favorite"};

        String[] announcements;
        String[] links;

        //Set announcements depending on which was chosen
        if (pastActivity.equals(listName[1])) {
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title, 1).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            links = displayAnnouncementList.getLinks();
        }

        else{
            DisplayAnnouncementList displayAnnouncementList = new DisplayAnnouncementList(title, 0).invoke();
            announcements = displayAnnouncementList.getAnnouncements();
            links = displayAnnouncementList.getLinks();
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.ann_list_style, R.id.tvAnn, announcements);


        list.setAdapter(adapter);

        // View Chosen Category List
        final String[] finalAnnouncements = announcements;
        final String[] finalLinks = links;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);

                Intent intent = new Intent(AnnouncementsList.this, DisplayAnnouncement.class);
                intent.putExtra("Title", finalAnnouncements[position]);
                intent.putExtra("URL", finalLinks[position]);
                startActivity(intent);
            }
        });

    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            if (techAnnounceDAO.getAnnouncementsByLink(finalLinks[position], getBaseContext()).getLink() == null &&
                    list.getItemAtPosition(position) == "No Announcements") {
                return false;
            }
            else {

                int updatedRow = techAnnounceDAO.updateSavedCol(1, finalLinks[position], getBaseContext());
                adapter.notifyDataSetChanged();
                if (updatedRow >= 1) {
                    android.util.Log.i("Updated Ann Row: ", String.valueOf(updatedRow));
                    view.setSelected(true);
                    Toast.makeText(getBaseContext(), "Announcement #" + (position+1)+" is Saved", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    Log.i("Updated Ann Row: ", "No update");
                    return false;
                }
            }
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
        private final String title;
        private final int list;
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
                    if (title.equals("All Announcements") )
                    {
                          announcements = announcementTitles;
                            links = announcementLinks;
                    }
                    else{
                        int cat_id= techCategoryDAO.getCategoriesByName(title, getBaseContext()).getId();

                        ArrayList<TechAnnounceCategoryList> announcementCat = techAnnounceCategoryDAO.getAnnByCatId(cat_id,
                                getBaseContext());

                        if (announcementCat.size() == 0){
                            announcements = new String[]{"No Announcements"};
                            links = new String[]{"http://www.techannounce.ttu.edu/"};
                        }
                        else {
                            String[] announcementsCatTitle = new String[announcementCat.size()];
                            String[] announcementsCatLink = new String[announcementCat.size()];
                            int i = 0;
                            int j = 0;

                            for (TechAnnounceCategoryList techAnnounceCategoryList : announcementCat) {
                                TechAnnounce announcement4mDB = techAnnounceDAO.getAnnouncementsById(
                                        techAnnounceCategoryList.getA_Id(), getBaseContext());

                                announcementsCatTitle[i++] = announcement4mDB.getTitle();
                                announcementsCatLink[j++] = announcement4mDB.getLink();
                            }
                            announcements = announcementsCatTitle;
                            links = announcementsCatLink;
                        }
                    }
                    break;
                case 1:
                    int cat_id= techCategoryDAO.getCategoriesByName(title, getBaseContext()).getId();
                    ArrayList<TechAnnounceCategoryList> announcementCat = techAnnounceCategoryDAO.getAnnByCatId(cat_id ,getBaseContext());

                    String[] announcementsCatTitle = new String[announcementCat.size()];
                    String[] announcementsCatLink = new String[announcementCat.size()];
                    int i = 0;
                    int j = 0;

                    for(TechAnnounceCategoryList techAnnounceCategoryList: announcementCat)
                    {
                        TechAnnounce announcement4mDB = techAnnounceDAO.getAnnouncementsById(
                                techAnnounceCategoryList.getA_Id(), getBaseContext());

                        announcementsCatTitle[i++] = announcement4mDB.getTitle();
                        announcementsCatLink[j++] = announcement4mDB.getLink();
                    }
                    announcements = announcementsCatTitle;
                    links = announcementsCatLink;

                default:
                    announcements = new String[]{"No Announcements"};
                    links = new String[]{"http://www.techannounce.ttu.edu/"};
                    break;

            }
            return this;
        }
    }
}

