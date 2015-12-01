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

import android.content.Context;
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
import android.widget.Toast;

import com.example.android.common.logger.Log;
import com.example.android.common.view.SlidingTabLayout;
import com.example.android.slidingtabsbasic.DAO.TechAnnounceDAO;
import com.example.android.slidingtabsbasic.DAO.TechCategoryDAO;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechCategoryList;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;

/**
 * A basic sample which shows how to use {@link com.example.android.common.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    final TechCategoryDAO techCategoryDAO = new TechCategoryDAO();
    final TechAnnounceDAO techAnnounceDAO = new TechAnnounceDAO();
    List<TechAnnounce> techAnnounceList = new ArrayList<>();


    //final TechAnnounce techAnnounce = new TechAnnounce();

    final static int[] favorite = {0,1};
    //TechCategoryList techCategoryList = new TechCategoryList();

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
    private at.markushi.ui.CircleButton tutorialButton;

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


        tutorialButton = (CircleButton) view.findViewById(R.id.tutorialBtn);
        /*tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TutorialDialog.class);
                startActivity(intent);
            }
            });*/

        //BEGIN_INCLUDE (setup_viewpager)
        //Get the ViewPager and set it's PagerAdapter so that it can display items
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
                return "Favorites";
            }
            else if(position == 3){
                return "Saved Pages";
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
                    setTabList(view,position);

                    container.addView(view);

                    break;



                case 3:
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_tags_,
                            container, false);
                    // Add the newly created View to the ViewPager
                    setTabList(view, position);
                    container.addView(view);

                    break;

                default:
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_tags_,
                            container, false);
                    // Add the newly created View to the ViewPager
                    setTabList(view, position);
                    container.addView(view);
                    break;


            }

            return view;
        }


        //Set up each tab UI
        public void setTabList(View view, int position){
            final ListView list, list2, list3, list4;

            final ArrayAdapter<String> adapter,adapter3,adapter4;

            final SlidingTabKeyList adapter2;


            switch (position) {
                // On Categories Tab
                case 0:
                    list = (ListView) view.findViewById(R.id.listTags);
                    //List<TechCategoryList> categoryList = techCategoryDAO.getCategoriesByFav(favorite[1], getContext());;
                    List<TechCategoryList> categoryList = techCategoryDAO.getCategoryList(getContext());
                    final String[] categoryName = new String[categoryList.size()];
                    int i = 0;

                    for (TechCategoryList techCategoryList : categoryList){
                        categoryName[i++] = techCategoryList.getName();
                        //techAnnounceCategoryDAO.getAnnByCatID(techCategoryList.getId(),getBaseContext());
                    }
                    final String[] categories = new String[categoryName.length +1];

                    categories[0] = "Most Recent Announcement";
                    System.arraycopy(categoryName, 0, categories, 1, categoryName.length);

                    //final  String[] category = categoryName;


                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.tags_list_style, R.id.tvList, categories);

                    list.setAdapter(adapter);

                    // View Chosen Category List
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //Display Chosen Category Announcement List
                            if (position == 0) {
                                Intent intent = new Intent(getActivity(), MostRecentList.class);
                                intent.putExtra("From", "Category");
                                intent.putExtra("Title", categories[position]);
                                getActivity().startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(getActivity(), AnnouncementsList.class);
                                intent.putExtra("From", "Category");
                                intent.putExtra("Title", categories[position]);
                                getActivity().startActivity(intent);
                            }
                        }
                    });

                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            if (position == 0) {
                                return false;
                            } else {
                                //onItemLongClick, update the table, and log Progress
                                int updatedRow = techCategoryDAO.updateFavTag(favorite[1], categories[position], getContext());
                                if (updatedRow >= 1) {
                                    Log.i("Updated Cat Row: ", String.valueOf(updatedRow));
                                    view.setSelected(true);
                                    Toast.makeText(getActivity(), "Saved " +categories[position] + " to Favorite", Toast.LENGTH_LONG).show();
                                    return true;
                                } else {
                                    Log.i("Updated Cat Row: ", "No update");
                                    return false;
                                }
                            }
                        }
                    });
                        break;

                // On Tags Tab
                case 1:
                    final ArrayList<TechAnnounce> techAnnounceList = new ArrayList<>();


                    for (TechAnnounce techAnnounce :techAnnounceDAO.getAllAnnouncements(getContext()))
                    {
                        techAnnounceList.add(techAnnounce);
                    }
                    adapter2 = new SlidingTabKeyList(getActivity(), R.layout.tags_list_style, R.id.tvList, techAnnounceList);
                    list2 = (ListView) view.findViewById(R.id.listTags);
                    list2.setAdapter(adapter2);

                    // View Chosen Tag List
                    list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //TODO: Display Chosen Tag Announcements
                            Intent intent = new Intent(getActivity(), DisplayAnnouncement.class);
                            intent.putExtra("Title", techAnnounceList.get(position).getTitle());
                            intent.putExtra("URL", techAnnounceList.get(position).getLink());
                            intent.putExtra("From", "Tags");
                            getActivity().startActivity(intent);
                        }
                    });
                    list2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            int updatedRow = techAnnounceDAO.updateSavedCol(1, techAnnounceList.get(position).getLink(), getContext());

                            adapter2.notifyDataSetChanged();
                            if (updatedRow >= 1) {
                                android.util.Log.i("Updated Ann Row: ", String.valueOf(updatedRow));
                                view.setSelected(true);
                                Toast.makeText(getActivity(), "Announcement #" + (position + 1) + " is Saved", Toast.LENGTH_LONG).show();
                                return true;
                            } else {
                                Log.i("Updated Ann Row: ", "No update");
                                return false;
                            }
                            }

                    });
                    break;

                // On Favorites Tab
                case 2:

                    list3 = (ListView) view.findViewById(R.id.listTags);
                    final List<TechCategoryList> categoryByFav = techCategoryDAO.getCategoriesByFav(favorite[1],getContext());
                    final ArrayList<String> favCategoryName = new ArrayList<String>();

                    for (TechCategoryList techCategoryList : categoryByFav){
                        favCategoryName.add(techCategoryList.getName());
                        //techAnnounceCategoryDAO.getAnnByCatID(techCategoryList.getId(),getBaseContext());
                    }

                    adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.tags_list_style, R.id.tvList, favCategoryName);

                    list3.setAdapter(adapter3);

                    // View Chosen Category List
                    list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //Display Chosen Category Announcement List

                            Intent intent = new Intent(getActivity(), AnnouncementsList.class);
                            intent.putExtra("From", "Category");
                            intent.putExtra("Title", favCategoryName.get(position));
                            getActivity().startActivity(intent);
                        }
                    });

                    list3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            int updatedRow = techCategoryDAO.updateFavTag(favorite[0], favCategoryName.get(position) , getContext());
                            Log.e("Updated Cat Row: ", String.valueOf(updatedRow));
                            view.setSelected(true);
                            Toast.makeText(getActivity(), "Removed From Favorite", Toast.LENGTH_LONG).show();

                            favCategoryName.remove(position);
                            adapter3.notifyDataSetChanged();
                            return true;

                        }
                    });


                    break;

                // On Saved Tab
                case 3:
                    list4 = (ListView) view.findViewById(R.id.listTags);
                    List<TechAnnounce> savedAnnouncements = techAnnounceDAO.getAnnouncementsBySavedTag(1, getContext());
                    final ArrayList<String> savedTitles = new ArrayList<>();
                    final ArrayList<String> savedLinks = new ArrayList<>();
                    int p = 0;
                    int q = 0;

                    for (TechAnnounce techAnnounce : savedAnnouncements) {
                        savedTitles.add(techAnnounce.getTitle());
                        savedLinks.add(techAnnounce.getLink());
                    }

                    adapter4 = new ArrayAdapter<String>(getActivity(), R.layout.tags_list_style, R.id.tvList, savedTitles);
                    adapter4.notifyDataSetChanged();
                    list4.setAdapter(adapter4);

                    // View Chosen Category List
                    list4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            view.setSelected(true);
                            //Display Chosen Category Announcement List

                            Intent intent = new Intent(getActivity(), DisplayAnnouncement.class);

                            intent.putExtra("Title", savedTitles.get(position));
                            intent.putExtra("URL", savedLinks.get(position));
                            getActivity().startActivity(intent);
                        }
                    });

                    list4.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            int updatedRow = techAnnounceDAO.updateSavedCol(0, savedLinks.get(position), getContext());
                            if (updatedRow >= 1) {
                                savedLinks.remove(position);
                                savedTitles.remove(position);
                                adapter4.notifyDataSetChanged();
                                Log.i("Updated Ann Row: ", String.valueOf(updatedRow));
                                view.setSelected(true);
                                Toast.makeText(getActivity(), "Removed #" + (position+1) +" Saved Announcement ", Toast.LENGTH_LONG).show();

                                return true;
                            } else {
                                Log.i("Updated Ann Row: ", "No update");
                                return false;
                            }
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



        private class SlidingTabKeyList extends ArrayAdapter<TechAnnounce> {

            private List<TechAnnounce> techAnnounceList;

            public SlidingTabKeyList(Context context, int resource, int textViewResourceId, List<TechAnnounce> techAnnounceList) {
                super(context, resource, textViewResourceId, techAnnounceList);
                this.techAnnounceList = techAnnounceList;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Make sure we have a view to work with (may have been given null)
                View itemView = convertView;
                if (itemView == null) {
                    itemView = getLayoutInflater(null).inflate(R.layout.sliding_tab_tag_list, parent, false);
                }

                // Find the announcement to work with.
                TechAnnounce currentAnnouncement = techAnnounceList.get(position);

                // AnnouncementTitle:
                TextView announcementTitle = (TextView) itemView.findViewById(R.id.item_announcement);
                announcementTitle.setText(currentAnnouncement.getTitle());

                //tagList
                TextView announcementTags = (TextView) itemView.findViewById(R.id.item_tags);
                String keyList[] = currentAnnouncement.getKeyList();
                if (keyList != null){

                    for (String tag : keyList) {
                        announcementTags.append("- " + tag);
                    }
                }

                return itemView;
            }
        }
    }
}
