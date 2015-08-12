package com.example.cristhian.popularmovies;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class PopMoviesActivity extends ActionBarActivity implements Communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_movies);


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.pop_movies_container, new PopMoviesFragment())
                    .commit();
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_pop_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void response(Movie movie) {
        FragmentManager fragmentManager = getFragmentManager();
        PopMoviesDetailFragment popMoviesDetailFragment = (PopMoviesDetailFragment) fragmentManager.findFragmentById(R.id.fragment2);
        if (popMoviesDetailFragment != null) {
            popMoviesDetailFragment.setMovie(movie);
        } else {
            PopMoviesDetailFragment fragment = new PopMoviesDetailFragment();
            fragment.setMovie(movie);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pop_movies_container, fragment)
                    .addToBackStack(null).commit();


        }


//        PopMoviesDetailFragment fragment = new PopMoviesDetailFragment();
//        fragment.setMovie(movie);
//        getFragmentManager().beginTransaction()
//                .add(R.id.pop_movies_container, fragment)
//                .addToBackStack("fragment").commit();
    }


    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
