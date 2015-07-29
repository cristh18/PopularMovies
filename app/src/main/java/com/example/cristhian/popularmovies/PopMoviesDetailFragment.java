package com.example.cristhian.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Cristhian on 23/07/2015.
 */
public class PopMoviesDetailFragment extends Fragment {

    private final String LOG_TAG = PopMoviesDetailFragment.class.getSimpleName();
    private Movie movie;
    private TextView textView;
    private ImageView imageView;
    private TextView textViewOverview;

    public PopMoviesDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        //textView = (TextView) rootView.findViewById(R.id.detail_text);
        //textView.setText(this.getMovie().getTitle());
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getActivity().findViewById(R.id.detail_text);
        imageView = (ImageView) getActivity().findViewById(R.id.image_detail);
        textViewOverview = (TextView) getActivity().findViewById(R.id.overview_text);
        if (this.getMovie()!=null){
            textView.setText(this.getMovie().getTitle());
            String baseURL = "http://image.tmdb.org/t/p/w185/";
            String item = baseURL.concat(movie.getPoster_path());                            ;
            Picasso.with(getActivity()).load(item).noFade().into(imageView);
        }else {
            textView.setText("");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_pop_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //if (id == R.id.action_settings){
        //  PopularMoviesTask popularMoviesTask = new PopularMoviesTask();
        //popularMoviesTask.execute();
        //return true;
        //}
        return super.onOptionsItemSelected(item);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }



    public void changeData(Movie movie){
        PopMoviesDetailFragment popMoviesDetailFragment =new PopMoviesDetailFragment();
        popMoviesDetailFragment.setMovie(movie);
       // return popMoviesDetailFragment;
    }
}
