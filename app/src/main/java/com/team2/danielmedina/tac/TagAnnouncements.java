package com.team2.danielmedina.tac;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TagAnnouncements extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_announcements);

        final ListView list = (ListView) findViewById(R.id.listTagAnnouncements);

        String[] Announcements= {};

        Intent intent1 = getIntent();
        switch (intent1.getStringExtra("Title")){
            case "Free Stuff":
                Announcements = new String[] {"TAB Presents: Photo Keychains"};
                break;

            case "Movies":
                Announcements = new String[] {""};
                break;

            case "Graduate":
                Announcements = new String[] {"October Programs in the Graduate Student Center"};
                break;

            case "Undergraduate":
                Announcements = new String[] {"Attention all A&S Undergraduate Students"};
                break;

            case "Paid Research":
                Announcements = new String[] {"Research Study on Family Experience of Addiction Recovery ($25 Compensation)","Research Study on Former Alcoholics"};
                break;

            default:
                Announcements = new String[] {"Error"};
                break;
        }



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Announcements);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);

                Intent intent = new Intent(TagAnnouncements.this, showAnnouncement.class);

                switch (position) {
                    case 0:
                        intent.putExtra("URL", "http://www.techannounce.ttu.edu/Client/ViewMessage.aspx?MsgId=180840");
                        intent.putExtra("Title","Research Study on Family Experience of Addiction Recovery ($25 Compensation)");
                        break;
                    case 1:
                        intent.putExtra("URL", "http://www.techannounce.ttu.edu/Client/ViewMessage.aspx?MsgId=180841");
                        intent.putExtra("Title","Research Study on Former Alcoholics");
                        break;

                    default:
                        break;
                }

                TagAnnouncements.this.startActivity(intent);
                //Anything
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tag_announcements, menu);
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
}
