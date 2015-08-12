package com.example.cristhian.popularmovies;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
 * Created by Cristhian on 23/07/2015.
 */
public class PopMoviesDetailFragment extends Fragment {

    private final String LOG_TAG = PopMoviesDetailFragment.class.getSimpleName();
    private Movie movie;
    TextView textView;
    ImageView imageView;
    TextView textViewOverview;
    TextView yearTextView;
    TextView durationTextView;
    TextView rateTextView;

    public PopMoviesDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        textView = (TextView) rootView.findViewById(R.id.detail_text);
        imageView = (ImageView) rootView.findViewById(R.id.image_detail);
        textViewOverview = (TextView) rootView.findViewById(R.id.overview_text);
        yearTextView = (TextView) rootView.findViewById(R.id.year_text);
        durationTextView = (TextView) rootView.findViewById(R.id.duration_text);
        rateTextView = (TextView) rootView.findViewById(R.id.rate_text);

        PopularDetailsMovieTask popularDetailsMovieTask = new PopularDetailsMovieTask();
        popularDetailsMovieTask.execute(movie.getId().toString());

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // menuInflater.inflate(R.menu.menu_pop_movies, menu);
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

    /**
     * Created by Cristhian on 28/07/2015.
     */
    private class PopularDetailsMovieTask extends AsyncTask<String, MovieDetail, MovieDetail> {

        private final String LOG_TAG = PopularDetailsMovieTask.class.getSimpleName();

        private static final String API_KEY = "b237a19b878581bd1bb981cd41555945";

        private String searchDetailMovie(String id) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String movieId = id;

            try {
                final String URL = "http://api.themoviedb.org/3/movie/".concat(movieId).concat("?");
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(URL).buildUpon().appendQueryParameter(API_KEY_PARAM, API_KEY).build();
                java.net.URL url = new URL(builtUri.toString());

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

        public String searchVideosMovie(String id) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieVideoJsonStr = null;
            String movieId = id;

            try {
                final String URL = "http://api.themoviedb.org/3/movie/".concat(movieId).concat("/videos").concat("?");
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(URL).buildUpon().appendQueryParameter(API_KEY_PARAM, API_KEY).build();
                java.net.URL url = new URL(builtUri.toString());

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
                    movieVideoJsonStr = null;
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
                    movieVideoJsonStr = null;
                }
                movieVideoJsonStr = buffer.toString();

                Log.i(LOG_TAG, "Movie videos JSON String: " + movieVideoJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                movieVideoJsonStr = null;
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

            return movieVideoJsonStr;

        }

        private MovieDetail getMovieData(String movieData, String movieVideoData) throws JSONException {
            final String RESULTS = "results";
            MovieDetail movieDetail = new MovieDetail();
            List<MovieVideoDetail> videos = new ArrayList<>();
            JSONObject forecastJson = new JSONObject(movieData);
            JSONObject movieVideosJson = new JSONObject(movieVideoData);
            JSONArray videosArray = movieVideosJson.getJSONArray(RESULTS);

            for (int i = 0; i < videosArray.length(); i++) {
                videos.add(new MovieVideoDetail(videosArray.getJSONObject(i)));
            }
            movieDetail.setVideos(videos);

            movieDetail.setOriginal_title(forecastJson.getString("original_title"));
            movieDetail.setOverview(forecastJson.getString("overview"));
            movieDetail.setPoster_path(forecastJson.getString("poster_path"));
            movieDetail.setRelease_date(forecastJson.getString("release_date"));
            movieDetail.setRuntime(forecastJson.getInt("runtime"));
            movieDetail.setVote_average(forecastJson.getDouble("vote_average"));
            return movieDetail;
        }

        protected MovieDetail doInBackground(String... params) {
            MovieDetail movieDetailTemp = new MovieDetail();
            if (searchDetailMovie(params[0]) != null) {
                String movieData = searchDetailMovie(params[0]);
                String movieVideoData = searchVideosMovie(params[0]);
                try {
                    if (getMovieData(movieData, movieVideoData) != null) {
                        movieDetailTemp = getMovieData(movieData, movieVideoData);
                    }
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
            return movieDetailTemp;
        }

        @Override
        protected void onPostExecute(MovieDetail result) {
            if (result != null) {
                textView.setText(result.getOriginal_title());
                String baseURL = "http://image.tmdb.org/t/p/w185/";
                String item = baseURL.concat(result.getPoster_path());
                ;
                Picasso.with(getActivity()).load(item).noFade().into(imageView);
                textViewOverview.setText(result.getOverview());
                String[] yearVector = result.getRelease_date().split("-");
                yearTextView.setText(yearVector[0]);
                durationTextView.setText(result.getRuntime().toString());
                rateTextView.setText(result.getVote_average().toString());
            }
        }
    }

}
