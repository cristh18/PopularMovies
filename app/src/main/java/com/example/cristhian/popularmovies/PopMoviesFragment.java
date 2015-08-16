package com.example.cristhian.popularmovies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristhian on 21/07/2015.
 */
@SuppressLint("ValidFragment")
public class PopMoviesFragment extends Fragment {

    private GridView myListMovieView;

    private CustomListAdapter customListAdapter;

    private final String LOG_TAG = PopMoviesFragment.class.getSimpleName();

    private String valueSorts;

    Communicator comm;

    public PopMoviesFragment() {
        valueSorts = "popularity.desc";
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            comm = (Communicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Communicator");
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
        if (id == R.id.action_most_popular) {
            PopularMoviesTask popularMoviesTask = new PopularMoviesTask();
            popularMoviesTask.execute("popularity.desc");
            return true;
        }else if (id == R.id.action_highest_rated){
            PopularMoviesTask popularMoviesTask = new PopularMoviesTask();
            popularMoviesTask.execute("vote_average.desc");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        comm = (Communicator) getActivity();
        PopularMoviesTask popularMoviesTask = new PopularMoviesTask();
        popularMoviesTask.execute(valueSorts);

        final List<Movie> movies = new ArrayList<>();

        myListMovieView = (GridView) rootView.findViewById(R.id.listview_pop_movies);

        customListAdapter = new CustomListAdapter(this.getActivity(), movies);

        myListMovieView.setAdapter(customListAdapter);
        myListMovieView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), "You click: " + movies.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                Movie movie = customListAdapter.getItem(i);
                //PopMoviesDetailFragment popMoviesDetailFragment =new PopMoviesDetailFragment();
                //popMoviesDetailFragment.setMovie(movie);
                comm.response(movie);

            }
        });


        return rootView;
    }

    public void setValueSorts(String valueSorts) {
        this.valueSorts = valueSorts;
    }

    public String getValueSorts() {
        return valueSorts;
    }

    public class PopularMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        private final String LOG_TAG = PopularMoviesTask.class.getSimpleName();

        private String searchMovies(String valueSort) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            //String valueSort = "popularity.desc";
            String apiKey = "b237a19b878581bd1bb981cd41555945";

            try {
                final String URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_BY_PARAM = "sort_by";
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(URL).buildUpon()
                        .appendQueryParameter(SORT_BY_PARAM, valueSort)
                        .appendQueryParameter(API_KEY_PARAM, apiKey).build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();

                Log.i(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                forecastJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return forecastJsonStr;

        }

        private List<Movie> getMoviesData(String moviesData) throws JSONException {

            List<Movie> list_movie = new ArrayList<>();
            final String RESULTS = "results";


            JSONObject forecastJson = new JSONObject(moviesData);
            JSONArray moviesArray = forecastJson.getJSONArray(RESULTS);

            for (int i = 0; i < moviesArray.length(); i++) {
                list_movie.add(new Movie(moviesArray.getJSONObject(i)));
            }

            return list_movie;
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            List<Movie> movies = new ArrayList<>();
            if (searchMovies(params[0]) != null) {
                String movieData = searchMovies(params[0]);
                try {
                    if (getMoviesData(movieData).size() > 0 && !getMoviesData(movieData).isEmpty()) {
                        movies.addAll(getMoviesData(movieData));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {

            if (result != null) {
                customListAdapter.clear();
                for (Movie a : result) {
                    customListAdapter.add(a);
                }
            }
        }
    }
}
