package com.example.android.slidingtabsbasic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by jinyiyang on 12/1/15.
 */
public class ReturnKeyphrase {

    public String ReturnKeyphrase(String json) throws JSONException {


        JSONObject jsonRootObject = new JSONObject(json);
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

        return key_phrase;
        //log the first bigram keyphrase here. I just use the first bigram keyphrase.
        // if we want to show more keyphrase, just change the number of the while loop.
    }


}


