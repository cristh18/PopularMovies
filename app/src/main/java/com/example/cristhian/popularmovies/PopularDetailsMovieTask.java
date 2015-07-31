//package com.example.cristhian.popularmovies;
//
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * Created by Cristhian on 28/07/2015.
// */
//public class PopularDetailsMovieTask extends AsyncTask<String, MovieDetail, MovieDetail> {
//
//    private final String LOG_TAG = PopularDetailsMovieTask.class.getSimpleName();
//
//    private String searchDetailMovie(String id){
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//
//        // Will contain the raw JSON response as a string.
//        String forecastJsonStr = null;
//        String movieId = id;
//        String apiKey = "b237a19b878581bd1bb981cd41555945";
//
//        try{
//            final String URL = "http://api.themoviedb.org/3/movie/".concat(movieId).concat("?");
//            final String API_KEY_PARAM = "api_key";
//
//            Uri builtUri = Uri.parse(URL).buildUpon().appendQueryParameter(API_KEY_PARAM, apiKey).build();
//            java.net.URL url = new URL(builtUri.toString());
//
//            Log.v(LOG_TAG, "Built URI " + builtUri.toString());
//
//            // Create the request to OpenWeatherMap, and open the connection
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            // Read the input stream into a String
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                // Nothing to do.
//                forecastJsonStr = null;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                // But it does make debugging a *lot* easier if you print out the completed
//                // buffer for debugging.
//                buffer.append(line + "\n");
//            }
//
//            if (buffer.length() == 0) {
//                // Stream was empty.  No point in parsing.
//                forecastJsonStr = null;
//            }
//            forecastJsonStr = buffer.toString();
//
//            Log.i(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);
//
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error ", e);
//            forecastJsonStr = null;
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (final IOException e) {
//                    Log.e(LOG_TAG, "Error closing stream", e);
//                }
//            }
//        }
//        return forecastJsonStr;
//    }
//
//    private MovieDetail getMovieData(String movieData) throws JSONException {
//        MovieDetail movieDetail = new MovieDetail();
//        JSONObject forecastJson = new JSONObject(movieData);
//        movieDetail.setOriginal_title(forecastJson.getString("original_title"));
//        movieDetail.setOverview(forecastJson.getString("overview"));
//        movieDetail.setPoster_path(forecastJson.getString("poster_path"));
//        movieDetail.setRelease_date(forecastJson.getString("release_date"));
//        movieDetail.setRuntime(forecastJson.getInt("runtime"));
//        movieDetail.setVote_average(forecastJson.getDouble("vote_average"));
//        return movieDetail;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
//    }
//
//    protected MovieDetail doInBackground(String... params) {
//        MovieDetail movieDetailTemp = new MovieDetail();
//        if (searchDetailMovie(params[0]) != null) {
//            String movieData = searchDetailMovie(params[0]);
//            try {
//                if (getMovieData(movieData) != null) {
//                    movieDetailTemp =getMovieData(movieData);
//                }
//            } catch (JSONException e) {
//               Log.e(LOG_TAG, e.getMessage());
//            }
//        }
//        return movieDetailTemp;
//    }
//
//    @Override
//    protected void onPostExecute(MovieDetail result) {
//        if (result != null) {
//            popMoviesDetailFragment.setMovieDetail(result);
//        }
//    }
//}
