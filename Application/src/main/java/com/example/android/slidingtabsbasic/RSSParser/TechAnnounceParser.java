<<<<<<< HEAD
package com.example.android.slidingtabsbasic.RSSParser;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bukunmi on 10/27/2015.
 */
public class TechAnnounceParser {

    public static List<TechAnnounce> parseFeed(String content) {

        try {

            boolean inDataItemTag = false;
            String currentTagName = "";
            TechAnnounce techAnnounce = null;
            List<TechAnnounce> techAnnounceList = new ArrayList<>();
            List<String> categoryList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("item")) { //create list that holds category
                            categoryList = new ArrayList<>();
                            inDataItemTag = true;
                            techAnnounce = new TechAnnounce();
                            techAnnounceList.add(techAnnounce);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            String[] categories = new String[categoryList.size()];
                            /*category = (String[])categoryList.toArray();*/
                            for(int i=0; i<categoryList.size(); i++){
                                categories[i] = categoryList.get(i);
                            }
                            techAnnounce.setCategoryList(categories);

                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && techAnnounce != null)
                        {
                            switch (currentTagName) {

                                case "title":
                                    techAnnounce.setTitle(parser.getText());
                                    break;
                                case "link":
                                    techAnnounce.setLink(parser.getText());
                                    break;

                                case "description":
                                    techAnnounce.setDescription(parser.getText());
                                    break;
                                case "category":
                                   //techAnnounce.setCate(parser.getText());
                                    String value = parser.getText();
                                   categoryList.add(value);
                                    break;

                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();

            }

            return techAnnounceList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
=======
package com.example.android.slidingtabsbasic.RSSParser;


import com.example.android.slidingtabsbasic.AppContent.TechAnnounce;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bukunmi on 10/27/2015.
 */
public class TechAnnounceParser {

    public static List<TechAnnounce> parseFeed(String content) {

        try {

            boolean inDataItemTag = false;
            String currentTagName = "";
            TechAnnounce techAnnounce = null;
            List<TechAnnounce> techAnnounceList = new ArrayList<>();
            List<String> categoryList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("item")) { //create list that holds category
                            categoryList = new ArrayList<>();
                            inDataItemTag = true;
                            techAnnounce = new TechAnnounce();
                            techAnnounceList.add(techAnnounce);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            String[] categories = new String[categoryList.size()];
                            /*category = (String[])categoryList.toArray();*/
                            for(int i=0; i<categoryList.size(); i++){
                                categories[i] = categoryList.get(i);
                            }
                            techAnnounce.setCategoryList(categories);

                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && techAnnounce != null)
                        {
                            switch (currentTagName) {

                                case "title":
                                    techAnnounce.setTitle(parser.getText());
                                    break;
                                case "link":
                                    techAnnounce.setLink(parser.getText());
                                    break;

                                case "description":
                                    techAnnounce.setDescription(parser.getText());
                                    break;
                                case "category":
                                   //techAnnounce.setCate(parser.getText());
                                    String value = parser.getText();
                                   categoryList.add(value);
                                    break;

                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();

            }

            return techAnnounceList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
>>>>>>> database
