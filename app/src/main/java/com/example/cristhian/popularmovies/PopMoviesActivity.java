package com.example.cristhian.popularmovies;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class PopMoviesActivity extends ActionBarActivity implements Communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_movies);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.pop_movies_container, new PopMoviesFragment())
                        .commit();
            }
        } else {
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.pop_movies_container, new PopMoviesFragment())
                        .commit();
            }
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
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment1, popMoviesDetailFragment);
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();
    }
}
