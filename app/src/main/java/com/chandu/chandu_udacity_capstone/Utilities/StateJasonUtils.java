package com.chandu.chandu_udacity_capstone.Utilities;

import android.content.Context;

import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.hike.Hike;
import com.chandu.chandu_udacity_capstone.hike.States;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class StateJasonUtils {
    private static final String LOG_TAG = StateJasonUtils.class.getSimpleName();

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            //InputStream is = context.getAssets().open("States.json");
            InputStream is = context.getResources().openRawResource(R.raw.states);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static List<States> getStateDetailFromJson(Context context, final String StatesDetailsStr)
            throws JSONException {
        List<States> statesList = null;
        JSONArray sortedArray = null;
        final String API_RESULTS = context.getString(R.string.states_json_extractor);

        Gson gson = new Gson();
        JSONObject hikeJason = new JSONObject(StatesDetailsStr);
        JSONArray resultsArray = hikeJason.getJSONArray(API_RESULTS);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        if (resultsArray.length() > 0) {
            statesList = Arrays.asList(gson.fromJson(resultsArray.toString(), States[].class));
        }

        return statesList;
    }
}
