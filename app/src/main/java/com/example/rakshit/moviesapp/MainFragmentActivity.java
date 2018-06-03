package com.example.rakshit.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainFragmentActivity extends AppCompatActivity {
    //private static final String TAG = MainFragmentActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieList;
    private ProgressBar progressBar;
    private GridView gridView;
    MovieListAsyncTask movieAsyncTask;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        gridView = (GridView) findViewById(R.id.movie_grid);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent DetailActivity = new Intent(MainFragmentActivity.this, DetailActivity.class);
                DetailActivity.putExtra("movie_id",movieList.get(i).getMovieId());
                Log.e("Main", "onItemClick: "+movieList.get(i).getTitle() );
                startActivity(DetailActivity);
            }
        });
        movieAsyncTask = new MovieListAsyncTask();

        movieAsyncTask.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_by_popularity)
        {
            SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
            editor.putString("sort", "popular");
            editor.commit();

        }
        else if(id == R.id.sort_by_rating){
            SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
            editor.putString("sort", "top_rated");
            editor.commit();

        }


        movieAsyncTask = new MovieListAsyncTask();

        movieAsyncTask.execute();
        return super.onOptionsItemSelected(item);
    }


    public class MovieListAsyncTask extends AsyncTask<URL, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
        @Override
        protected ArrayList<Movie> doInBackground(URL... params) {
            Uri.Builder uri = Uri.parse(Movie.BASE_URL).buildUpon();
            sharedpreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            String sorting = sharedpreferences.getString("sort", "popularity");
            uri.appendPath(sorting)
                    .appendQueryParameter("api_key", Movie.API_KEY).build();
            String jsonResponse = null;
            try {
                URL url = new URL(uri.toString());
                jsonResponse = Utils.makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Utils.parseJSONList(jsonResponse);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            movieList.clear();
            movieList.addAll(movies);
            movieAdapter.notifyDataSetChanged();
        }
    }



    public boolean bhvbjh() {
        return true;
    }
}
