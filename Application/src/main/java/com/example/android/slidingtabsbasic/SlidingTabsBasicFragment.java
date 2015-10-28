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

import com.example.android.common.logger.Log;
import com.example.android.common.view.SlidingTabLayout;

import android.content.Intent;
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
import android.widget.TextView;

/**
 * A basic sample which shows how to use {@link com.example.android.common.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

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
        public void setTabList(View view,int position){
            ListView list;
            ArrayAdapter<String> adapter;

            switch (position) {
                // On Categories Tab
                case 0:
                    list = (ListView) view.findViewById(R.id.listTags);

                    final String[] tags = new String[]{"All Announcements", "Athletic", "Orientation", "Fundraiser"
                            ,"Academic", "Research", "Training", "Departamental", "IT Announcements", "Rec Sports", "Events"};

                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, tags);

                    list.setAdapter(adapter);

                    // View Chosen Category List
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //Display Chosen Category Announcement List
                            Intent intent = new Intent(getActivity(), AnnouncementsList.class);
                            intent.putExtra("Title", tags[position]);
                            intent.putExtra("From","Category");
                            getActivity().startActivity(intent);
                        }
                    });

                        break;
                // On Tags Tab
                case 1:
                    //TODO: Change the R.id.listTags to the corresponding id of the ListView in XML
                    list = (ListView) view.findViewById(R.id.listTags);

                    final String[] categories = new String[]{"Free Stuff", "Movies", "Graduate", "Undergraduate"
                            , "Paid Research"};

                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, categories);

                    list.setAdapter(adapter);

                    // View Chosen Tag List
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //TODO: Display Chosen Tag Announcements
                            Intent intent = new Intent(getActivity(), AnnouncementsList.class);
                            intent.putExtra("Title", categories[position]);
                            intent.putExtra("From","Tags");
                            getActivity().startActivity(intent);
                        }
                    });


                    break;
                // On Favorites Tab
                case 2:
                    //TODO: Change the R.id.listTags to the corresponding id of the ListView in XML
                    list = (ListView) view.findViewById(R.id.listTags);

                    String[] favs = new String[]{};

                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, favs);

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

                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, saved);

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