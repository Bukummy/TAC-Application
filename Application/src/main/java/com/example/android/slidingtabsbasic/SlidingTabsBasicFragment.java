/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.slidingtabsbasic;

<<<<<<< HEAD
import com.example.android.common.logger.Log;
import com.example.android.common.view.SlidingTabLayout;
import com.example.android.slidingtabsbasic.RSSParser.AllAnnouncementsList;
import com.example.android.slidingtabsbasic.RSSParser.HttpManager;
import com.example.android.slidingtabsbasic.RSSParser.TechAnnounce;
import com.example.android.slidingtabsbasic.RSSParser.TechAnnounceParser;

import android.content.Context;
=======
>>>>>>> database
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
<<<<<<< HEAD
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
=======
import android.widget.Toast;

import com.example.android.common.logger.Log;
import com.example.android.common.view.SlidingTabLayout;
>>>>>>> database

/**
 * A basic sample which shows how to use {@link com.example.android.common.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";
    String[] announcementTitles;
    String[] announcementURLs;
    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);

    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items

        announcementTitles = getArguments().getStringArray("Announcement Titles");
        announcementURLs = getArguments().getStringArray("Announcement URLs");


        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0){
                return "Categories";
            }
            else if(position == 1){
                return "Tags";
            }
            else if(position == 2){
                return "Favorite";
            }
            else if(position == 3){
                return "Saved";
            }
            else return ("overflow");

        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view;

            switch (position) {
                case 0:

                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_tags_,
                            container, false);

                    //Set up list to add to view
                    setTabList(view, position);

                    // Add the newly created View to the ViewPager
                    container.addView(view);

                    break;

                case 1:
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_tags_,
                            container, false);
                    // Add the newly created View to the ViewPager
                    setTabList(view,position);

                    container.addView(view);

                    break;

                case 2:
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_tags_,
                            container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);

                    break;

                case 3:
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_tags_,
                            container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);

                    break;

                default:
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_tags_,
                            container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);
                    break;


            }

            return view;
        }


        //Set up each tab UI
        public void setTabList(View view, int position){
            final ListView list;
            ArrayAdapter<String> adapter;

            switch (position) {
                // On Categories Tab
                case 0:
                    list = (ListView) view.findViewById(R.id.listTags);

<<<<<<< HEAD
                    final String[] categories = new String[]{"All Announcements", "Athletic", "Orientation", "Fundraiser"
                            ,"Academic", "Research", "Training", "Departamental", "IT Announcements", "Rec Sports", "Events"};

                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, categories);
=======
                    final String[] categories = new String[]{"All Announcements",
                            "Academic",
                            "Administration & Finance Information Systems Management (AFISM)",
                            "Arts & Entertainment",
                            "Athletic",
                            "Computing Equipment",
                            "Departamental",
                            "Departmental Events",
                            "Faculty/Staff Organization",
                            "Fundraiser",
                            "HR Talent Development",
                            "IT Announcements",
                            "Lectures & Seminars",
                            "Non Computing Equipment",
                            "Orientation",
                            "Rec Sports Programing",
                            "Research",
                            "Research",
                            "Small Business Development Center",
                            "Student Employment/Career Opportunities",
                            "Student Organization",
                            "Teaching, Learning & Professional Development Center",
                            "Training",
                            "TTU IT Training", "Events" };

                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.tags_list_style, R.id.tvList, categories);
>>>>>>> database

                    list.setAdapter(adapter);

                    // View Chosen Category List
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //Display Chosen Category Announcement List

                            Intent intent = new Intent(getActivity(), AnnouncementsList.class);
<<<<<<< HEAD
                            intent.putExtra("From","Category");
=======
                            intent.putExtra("From", "Category");
>>>>>>> database
                            intent.putExtra("Title", categories[position]);
                            intent.putExtra("Titles", announcementTitles);
                            intent.putExtra("URLs", announcementURLs);
                            getActivity().startActivity(intent);
                        }
                    });

                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {



                            int tag_fav = R.string.tag_fav_text;
                            view.setTag(tag_fav);
                            view.setSelected(true);
                            Toast.makeText(getActivity(), "Saved to Favorite", Toast.LENGTH_LONG).show();

                            return true;
                        }
                    });


                        break;
                // On Tags Tab
                case 1:
                    //TODO: Change the R.id.listTags to the corresponding id of the ListView in XML
                    list = (ListView) view.findViewById(R.id.listTags);

                    final String[] tags = new String[]{"Free Stuff", "Movies", "Graduate", "Undergraduate"
                            , "Paid Research"};

<<<<<<< HEAD
                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, tags);
=======
                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.tags_list_style, R.id.tvList, tags);
>>>>>>> database

                    list.setAdapter(adapter);

                    // View Chosen Tag List
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //TODO: Display Chosen Tag Announcements
                            Intent intent = new Intent(getActivity(), AnnouncementsList.class);
                            intent.putExtra("Title", tags[position]);
                            intent.putExtra("From","Tags");
                            intent.putExtra("Titles", announcementTitles);
                            intent.putExtra("URLs", announcementURLs);
                            getActivity().startActivity(intent);
                        }
                    });


                    break;
                // On Favorites Tab
                case 2:
                    //TODO: Change the R.id.listTags to the corresponding id of the ListView in XML
                    list = (ListView) view.findViewById(R.id.listTags);

                    String[] favs = new String[]{};

                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.tags_list_style, R.id.tvList, favs);

                    list.setAdapter(adapter);

                    // View Chosen Favorite List
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //TODO: Display screen with favorite Categories
//                            Intent intent = new Intent(getActivity(), NextClass.class);
//                            intent.putExtra("Title", tags[position]);
//                            getActivity().startActivity(intent);
                        }
                    });


                    break;
                // On Saved Tab
                case 3:
                    //TODO: Change the R.id.listTags to the corresponding id of the ListView in XML of Next Activity

                    list = (ListView) view.findViewById(R.id.listTags);

                    String[] saved = new String[]{};

                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.tags_list_style, R.id.tvList, saved);

                    list.setAdapter(adapter);

                    // Display Chosen Saved Announcement
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //TODO: set up show announcement view
//                            Intent intent = new Intent(getActivity(), NextClass.class);
//                            intent.putExtra("Title", tags[position]);
//                            getActivity().startActivity(intent);
                        }
                    });

                    break;

                default:

                    break;

            }
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

    }
}
