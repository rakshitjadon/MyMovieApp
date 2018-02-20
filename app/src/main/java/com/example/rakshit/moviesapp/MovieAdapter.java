package com.example.rakshit.moviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rakshit on 13/02/18.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    MovieAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_item);
        Picasso.with(getContext()).load(movie.getPoster()).into(imageView);
        return convertView;
    }
}
