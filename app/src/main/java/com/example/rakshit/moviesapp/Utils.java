package com.example.rakshit.moviesapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by rakshit on 13/02/18.
 */
public class Utils {
    private static final String LOG_TAG = Utils.class.getName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/discover";
    public static final String API_KEY = "0b101b9c97ade062c5754b376c59939e";
    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000/* milliseconds */);
            urlConnection.setConnectTimeout(10000/* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 200) {
                jsonResponse = "";
            } else {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder buffer = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }
        }
        return buffer.toString();
    }

    public static ArrayList<Movie> parseJSONList(String JSONString) {

        ArrayList<Movie> movieList = new ArrayList<Movie>();

        try {
            JSONObject Obj = new JSONObject(JSONString);
            JSONArray results = Obj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject Object = results.getJSONObject(i);
                String id = Object.getString("id");
                String posterPath = Object.getString("poster_path");
                Log.e("utils", "parseJSONList: "+id );
                movieList.add(new Movie(id, posterPath));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }
}
