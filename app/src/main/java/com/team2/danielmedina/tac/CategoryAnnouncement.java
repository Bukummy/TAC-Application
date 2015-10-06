package com.team2.danielmedina.tac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryAnnouncement extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_announcement);


        final ListView list = (ListView) findViewById(R.id.listCategoryAnnouncement);

        String[] categories = new String[] {};



        final Intent intent1 = getIntent();

        switch (intent1.getStringExtra("Title")){
            case "All Announcements":
                categories = new String[] {"Free Food!", "TAB Movie Night", "Research Study on Family Experience of Addiction Recovery ($25 Compensation)","Research Study on Former Alcoholics", "Fundraiser"};
                break;

            case "Athletic":
                categories = new String[] {};
                break;

            case "Orientation":

                break;

            case "Research":
                categories = new String[] {"Research Study on Family Experience of Addiction Recovery ($25 Compensation)","Research Study on Former Alcoholics"};
                break;

            case "Events":
                categories = new String[] {"TAB Movie Night"};
                break;

            default:
                categories = new String[] {"Empty"};
                break;
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, categories);


        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);

                Intent intent = new Intent(CategoryAnnouncement.this, showAnnouncement.class);

                switch (position){
                    case 0:
                        intent.putExtra("URL", "http://www.techannounce.ttu.edu/Client/ViewMessage.aspx?MsgId=180840");
                        intent.putExtra("Title","Research Study on Family Experience of Addiction Recovery ($25 Compensation)");
                        break;
                    case 1:
                        intent.putExtra("URL", "http://www.techannounce.ttu.edu/Client/ViewMessage.aspx?MsgId=180841");
                        intent.putExtra("Title","Research Study on Former Alcoholics");
                        break;

                    case 2:
                        intent.putExtra("URL","");

                        break;

                    default:
                        break;
                }

                CategoryAnnouncement.this.startActivity(intent);
                //Anything
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_announcement, menu);
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
