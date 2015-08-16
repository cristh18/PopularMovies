package com.example.cristhian.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Cristhian on 7/5/2015.
 */
public class CustomListAdapter extends ArrayAdapter<Movie> {

    private final Activity context;
    private final List<Movie> movies;

    public CustomListAdapter(Activity context, List<Movie> movies) {
        super(context, R.layout.movies_list, movies);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.movies = movies;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.movies_list, null, true);

//        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);

        if (movies != null) {
            if (movies.size() != 0 && !movies.isEmpty()) {
//                txtTitle.setText(movies.get(position).getTitle());

                if (movies.get(position).getPoster_path() != null) {
                    String baseURL = "http://image.tmdb.org/t/p/w500/";
                    String item = baseURL.concat(movies.get(position).getPoster_path());                            ;
                    Picasso.with(rowView.getContext()).load(item).noFade().into(imageView);
                }
            }
        }

        return rowView;

    }
}