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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        setTitle(title);

        String pastActivity = intent.getStringExtra("From");

        ListView list = (ListView) findViewById(R.id.AnnouncementListView);


        String[] announcements = new String[]{"No Announcements"};
        //Set announcements depending on which was chosen
        switch (pastActivity){
            case "Category":
                announcements = new String[]{"List of Announcements by Category "};
                break;

            case "Tags":
                announcements = new String[]{"List of Announcements by Key Phrases"};
                break;

            case "Favorites":
                announcements = new String[]{"Favorite Category List"};
                //list of selected categories
                break;

            case "Saved":
                announcements = new String[]{"Saved Announcements List"};
                break;

            default:
                break;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, announcements);

        list.setAdapter(adapter);

        // View Chosen Category List
        final String[] finalAnnouncements = announcements;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                //TODO: Display webView of Announcement
                            Intent intent = new Intent(AnnouncementsList.this, DisplayAnnouncement.class);
                            intent.putExtra("Title", finalAnnouncements[position]);
                            intent.putExtra("URL","http://www.techannounce.ttu.edu/Client/ViewMessage.aspx?MsgId=181115");
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

        if (id == android.R.id.home ){
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
