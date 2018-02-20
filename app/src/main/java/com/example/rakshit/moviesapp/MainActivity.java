package com.example.rakshit.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieList;
    private ProgressBar progressBar;
    private GridView gridView;
    MovieListAsyncTask movieAsyncTask;
    private Integer selectedId;
    private String sortOrder;


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
                Intent DetailActivity = new Intent(MainActivity.this, DetailActivity.class);
                DetailActivity.putExtra("movie_id",movieList.get(i).getMovieId());
                Log.e("Main", "onItemClick: "+movieList.get(i).getTitle() );
                startActivity(DetailActivity);
            }
        });
        movieAsyncTask = new MovieListAsyncTask();
        movieAsyncTask.execute();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String sortParam = prefs.getString(getString(R.string.prefs_sort_order), getString(R.string.sort_order_popularity));

        if (getString(R.string.sort_order_rating).equals(sortParam)) {
            selectedId = R.id.sort_order_rating;
        } else if (getString(R.string.sort_order_popularity).equals(sortParam)) {
            selectedId = R.id.sort_order_popularity;
        }
        menu.findItem(selectedId).setChecked(true);
        return true;
    }

    /*public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String sortParam = prefs.getString(getString(R.string.prefs_sort_order), getString(R.string.sort_order_popularity));

        if (getString(R.string.sort_order_rating).equals(sortParam)) {
            selectedId = R.id.sort_order_rating;
        } else if (getString(R.string.sort_order_popularity).equals(sortParam)) {
            selectedId = R.id.sort_order_popularity;
        }
        menu.findItem(selectedId).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String param = null;
        switch (item.getItemId()) {
            case R.id.sort_order_popularity:
                param = getString(R.string.sort_order_popularity);
                break;
            case R.id.sort_order_rating:
                param = getString(R.string.sort_order_rating);
                break;
        }
        if (param != null) {
            item.setChecked(true);
            setSortOrder(param);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.prefs_sort_order), this.sortOrder);
        editor.apply();
        MovieListAsyncTask getMovies = new MovieListAsyncTask();
        getMovies.execute();
    }*/

    public class MovieListAsyncTask extends AsyncTask<URL, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
        @Override
        protected ArrayList<Movie> doInBackground(URL... params) {
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            //String sortOrder = prefs.getString(getString(R.string.prefs_sort_order), getString(R.string.sort_order_popularity));
            Uri.Builder uri = Uri.parse(Utils.BASE_URL).buildUpon();
            uri.appendPath("movie")
                    .appendQueryParameter("api_key", Utils.API_KEY).build();
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
        }
    }


}