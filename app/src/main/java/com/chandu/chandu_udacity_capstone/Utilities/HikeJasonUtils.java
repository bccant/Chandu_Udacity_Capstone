package com.chandu.chandu_udacity_capstone.Utilities;

import android.content.Context;
import android.util.Log;

import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.hike.Hike;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.lang.Math;

public class HikeJasonUtils {
    private static final String LOG_TAG = HikeJasonUtils.class.getSimpleName();
    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param hikesJsonStr JSON response from server
     * @return Array of Strings describing weather data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static List<Hike> getHikeDetailFromJson(Context context, final String hikesJsonStr, final String sortType)
            throws JSONException {
        List<Hike> hikesList = null;
        JSONArray sortedArray = null;
        final String API_RESULTS = context.getString(R.string.trails_api_extractor);

        Gson gson = new Gson();
        JSONObject hikeJason = new JSONObject(hikesJsonStr);
        JSONArray resultsArray = hikeJason.getJSONArray(API_RESULTS);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        sortedArray = sortJsonArray(resultsArray, sortType);
        if (resultsArray.length() > 0) {
            hikesList = Arrays.asList(gson.fromJson(sortedArray.toString(), Hike[].class));
        }

        return hikesList;
    }


    public static JSONArray sortJsonArray(JSONArray array, final String sortType) throws JSONException {
        List<JSONObject> jsons = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            jsons.add(array.getJSONObject(i));
        }

        if (sortType.equals("length") || sortType.equals("difficulty")) {
            Collections.sort(jsons, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject lhs, JSONObject rhs) {
                    if (sortType.equals("length")) {
                        double lid = 0.0;
                        double rid = 0.0;
                        try {
                            lid = lhs.getDouble("length");
                            rid = rhs.getDouble("length");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return Double.compare(lid, rid);
                    } else  {
                        String lid = null;
                        String rid = null;
                        try {
                            lid = lhs.getString("difficulty");
                            rid = rhs.getString("difficulty");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return lid.compareTo(rid);
                    }
                }
            });
        }
        return new JSONArray(jsons);
    }
}
