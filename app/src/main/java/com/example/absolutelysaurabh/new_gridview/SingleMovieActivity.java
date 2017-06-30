package com.example.absolutelysaurabh.new_gridview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class SingleMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        // Get intent data
        Intent i = getIntent();

        ScrollView sv = new ScrollView(this);
        //Add your widget as a child of the ScrollView.
        //sv.addView(wView);

        Movie movie = (Movie) getIntent().getSerializableExtra("movies");



        String title  = movie.getTitle();
        String overview = movie.getOverview();
        String rating = movie.getRating();
        String release_date = movie.getReleaseDate();
        String imageUrl = movie.getPoster_path_url();

        String url = "http://image.tmdb.org/t/p/w185/"+imageUrl;

        TextView titleView = (TextView) this.findViewById(R.id.movie_title);
        TextView overviewView = (TextView) this.findViewById(R.id.movie_description);
        TextView dateView = (TextView) this.findViewById(R.id.movie_release_date);
        TextView ratingView = (TextView) this.findViewById(R.id.movie_rating);

        titleView.setText(title);
        overviewView.setText(overview);
        dateView.setText(release_date);
        ratingView.setText(rating);

        ImageView imageView = (ImageView) this.findViewById(R.id.movie_image);

        Picasso.with(this).load(url).into(imageView);

//        ImageView imageView = (ImageView) findViewById(R.id.movie_image);

      // imageView.setImageResource(R.drawable.pirates);


    }
}
