package com.example.absolutelysaurabh.new_gridview;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    //Constant value for the movie loader ID. We can choode any constant integer
    //This really comes into play if we're using multiple loaders
    private static final int NEWS_LOADER_ID = 1;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    //Here we need the URL to extract JSON news
    //Use AllCaps for a constant.
    private static final String POPULAR_MOVIE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1";

    private static String URL_PART_ONE = "https://api.themoviedb.org/3/movie/";

    private static String URL_PART_TWO = "?api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1";

    //Adapter for the list of the movies
    private ImageAdapter imageAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);

        imageAdapter = new ImageAdapter(this,new ArrayList<Movie>());

        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie currentMovie = imageAdapter.getItem(position);

                Intent i = new Intent(getApplicationContext(),SingleMovieActivity.class);
                //Pass the image index
                i.putExtra("movies",currentMovie);
                startActivity(i);
            }
        });

        android.app.LoaderManager loaderManager = getLoaderManager();

        //Initialize the loader. Pass in the int ID constant defined above and pass in null for
        //the bundle . Pass this activity for the LoaderCallBacks parameter (which is valid because
        //this activity implements the LoaderCallbacks interface;
        loaderManager.initLoader(NEWS_LOADER_ID,null,this);

//        mEmptyStateTextView = (TextView) findViewById(R.id.empty);
//        gridView.setEmptyView(mEmptyStateTextView);

    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        String FINAL_MOVIE_URL = URL_PART_ONE + orderBy + URL_PART_TWO;

        return new MovieLoader(this,FINAL_MOVIE_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movie) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
//        mEmptyStateTextView.setText("No Internet Connection");

        // Clear the adapter of previous earthquake data
        imageAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movie != null && !movie.isEmpty()) {
            imageAdapter.addAll(movie);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

        imageAdapter.clear();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
