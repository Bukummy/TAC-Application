package com.example.android.slidingtabsbasic;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by jinyiyang on 11/30/15.
 */
public class keyphrase extends AsyncTask<String, String, String> {

    HttpURLConnection urlConnection;

    @Override
    protected String doInBackground(String... args) {

        String result = "";
        String api_key = "1d4e852374c1e857eb39a0a3b1cf1472";
        String n = "2";
        String text = args[0].replace(" ","%20");
        String toUrl = "http://api.datumbox.com/1.0/KeywordExtraction.json?"+"api_key="+api_key+"&n="+n+"&text="+text;


        try {
            URL url = new URL(toUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = null;
            while ((line = reader.readLine()) != null) {
                result+=line;

            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //Do something with the JSON string


try{
        JSONObject jsonRootObject = new JSONObject(result);
        JSONObject output = jsonRootObject.getJSONObject("output");
        JSONObject kwresult =output.getJSONObject("result");
        JSONObject bigram =kwresult.getJSONObject("2");

        Iterator<?> keys = bigram.keys();
        int i=0;
        String key_phrase ="";
    //get the first keyphrase.
        while( keys.hasNext()&i<1 ) {
            String key = (String)keys.next();
            //This is the key_phrase(bigram);
            key_phrase = key;
            i++;
        }

    //log the first bigram keyphrase here. I just use the first bigram keyphrase.
    // if we want to show more keyphrase, just change the number of the while loop.
        Log.i("yjy", key_phrase);}catch (JSONException e) {e.printStackTrace();}

//do whatever you want with the key_phrase here
        // and it should be use


    }

}