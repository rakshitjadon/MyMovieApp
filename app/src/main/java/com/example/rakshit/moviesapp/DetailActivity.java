package com.example.rakshit.moviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    private static String BASE_API="https://api.themoviedb.org/3/movie";
    TextView TitleView;
    ImageView image;
    TextView Rating;
    TextView plot;
    TextView Releasedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String m_id=getIntent().getStringExtra("movie_id");
        TitleView = findViewById(R.id.title_text);
        image=findViewById(R.id.poster_image);
        Rating = findViewById(R.id.rating);
        plot = findViewById(R.id.plot);
        Releasedate = findViewById(R.id.release_date);

        Uri.Builder uri = Uri.parse(BASE_API).buildUpon();
        Uri u=uri.appendPath(m_id)
                .appendQueryParameter("api_key", Utils.API_KEY).build();

        DetailAsyncTask dTask=new DetailAsyncTask();
        dTask.execute(u);
    }
    private class DetailAsyncTask extends AsyncTask<Uri,Void,Movie>{

        @Override
        protected Movie doInBackground(Uri... uris) {
            Log.e("this", "doInBackground: "+uris[0] );
            String jsonResponse = null;
            try {
                URL url = new URL(uris[0].toString());
                jsonResponse = Utils.makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("this", "doInBackground: "+jsonResponse );
            return detailingFromJSON(jsonResponse);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            Log.e("thisthis", "onPostExecute: "+movie.getTitle() );
            TitleView.setText(movie.getTitle());
            Picasso.with(DetailActivity.this).load(movie.getPoster()).into(image);
            Rating.setText(movie.getVoteAvg().toString());
            plot.setText(movie.getOverview());
            Log.e("release", "onPostExecute: "+movie.getReleaseDate() );
            Releasedate.setText(movie.getReleaseDate());



        }
    }
    private Movie detailingFromJSON(String jsonResponse) {
        if (jsonResponse == null) {
            return null;
        }
        try {
            JSONObject detail = new JSONObject(jsonResponse);
            String movie_title = detail.optString("title");
            String image_poster = detail.getString("backdrop_path");
            String plot_synopsis = detail.getString("overview");
            String vote_average = detail.getString("vote_average");
            String release_date = detail.getString("release_date");
            Log.e("json", "detailingFromJSON: "+movie_title );
            return new Movie(movie_title,image_poster,plot_synopsis,vote_average,release_date);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
