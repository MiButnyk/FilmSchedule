package com.example.kursovaya.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kursovaya.view_models.FilmDetailsViewModel;
import com.example.kursovaya.R;
import com.example.kursovaya.dbclasses.Details;
import com.example.kursovaya.pojo.Cast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FilmDetailsActivity extends AppCompatActivity {

    public static String ID_KEY = "ID_KEY";

    private TextView title;
    private TextView orig_title;
    private TextView overview;
    private TextView income;
    private TextView genres;
    private TextView vote;
    private TextView budget;
    private ImageView image;
    private Spinner actors;
    private Spinner companies;
    private FloatingActionButton fab;
    private LiveData<Details> details;
    private FilmDetailsViewModel model;
    private Timer timer;
    private TimerTask timerTask;
    private ProgressBar progressBar;

    private int filmid = 1;


    private View.OnClickListener OnFABClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(FilmDetailsActivity.this, v);
            popupMenu.inflate(R.menu.popupmenu);

            popupMenu
                    .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String normal = title.getText().toString();
                            switch (item.getItemId()) {
                                case R.id.menu1:
                                    SharedPreferences chosenPref = FilmDetailsActivity.this.getSharedPreferences("Chosen", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = chosenPref.edit();
                                    editor.putInt(normal, filmid);
                                    editor.apply();
                                    return true;
                                case R.id.menu2:
                                    Intent intent = new Intent(FilmDetailsActivity.this, CalendarActivity.class);
                                    intent.putExtra(CalendarActivity.WATCHING_KEY, normal);
                                    intent.putExtra(CalendarActivity.ID_FILM, filmid);
                                    startActivity(intent);
                                    return true;
                                case R.id.menu3:
                                    Uri address = Uri.parse("https://m.youtube.com/results?search_query=" + normal + " trailer");
                                    Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                                    startActivity(openlink);
                                default:
                                    return false;
                            }
                        }
                    });
            popupMenu.show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        title = findViewById(R.id.title);
        orig_title = findViewById(R.id.original_title);
        overview = findViewById(R.id.overview);
        income = findViewById(R.id.money);
        actors = findViewById(R.id.actors);
        companies = findViewById(R.id.companies);
        genres = findViewById(R.id.genres);
        vote = findViewById(R.id.vote);
        budget = findViewById(R.id.budget);
        image = findViewById(R.id.image);

        progressBar = findViewById(R.id.progress_circular);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(ProgressBar.GONE);
                        overview.setText(R.string.internet_connection);
                        fab.setVisibility(View.GONE);
                    }
                });
            }
        };
        Bundle bundle = getIntent().getExtras();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(OnFABClick);

        model = ViewModelProviders.of(this).get(FilmDetailsViewModel.class);

        details = model.getDetails((int)bundle.get(ID_KEY));
        details.observe(FilmDetailsActivity.this, new Observer<Details>() {
            @Override
            public void onChanged(Details details) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                if(details != null){
                    filmid = details.filmId;

                    title.setText(details.title);
                    orig_title.setText(details.originalTitle);

                    if(details.budget > 0){
                        budget.setText("Budget: " + details.budget + "$");
                    }
                    else{
                        budget.setText("Budget: No info");
                    }

                    if(details.revenue > 0){
                        income.setText("Income: " + details.revenue + "$");
                    }
                    else{
                        income.setText("Income: No info");
                    }

                    Picasso.with(FilmDetailsActivity.this).load("https://image.tmdb.org/t/p/w185" + details.posterPath).into(image);
                    overview.setText(details.overview);

                    List<String> genre = details.genres;
                    StringBuilder stringBuilder = new StringBuilder("Genres: ");
                    for(int i = 0; i < genre.size(); i++){
                        stringBuilder.append(genre.get(i)).append(", ");
                    }
                    genres.setText(stringBuilder.substring(0, stringBuilder.length() - 2));

                    vote.setText("Average vote: " + details.voteAverage.toString());

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(FilmDetailsActivity.this, android.R.layout.simple_spinner_item, details.companies);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    companies.setAdapter(arrayAdapter);
                    List<String> cast = new ArrayList<>();
                    for(Cast c: details.cast) {
                        if(!c.getName().equals("")) {
                            cast.add(c.getName());
                        }
                    }

                    ArrayAdapter<String> castAdapter = new ArrayAdapter<>(FilmDetailsActivity.this, android.R.layout.simple_spinner_item, cast);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    actors.setAdapter(castAdapter);
                    timer.cancel();
                    progressBar.setVisibility(ProgressBar.GONE);
                }
                else {
                    timer.schedule(timerTask, 3000);
                }
            }
        });
    }
}
