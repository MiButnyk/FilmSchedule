package com.example.kursovaya.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.kursovaya.support_classes.FilmsAdapter;
import com.example.kursovaya.R;
import com.example.kursovaya.view_models.SearchViewModel;
import com.example.kursovaya.dbclasses.Film;

public class ApiActivity extends AppCompatActivity {

    public static String SEARCH_KEY = "SEARCH_KEY";

    private RecyclerView recyclerView;
    private LiveData<List<Film>> films;
    private FilmsAdapter adapter;
    private SearchViewModel model;
    private Bundle bundle;
    private Timer timer;
    private ProgressBar progressBar;
    private TimerTask timerTask;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_api);

        progressBar = findViewById(R.id.progress_circular);
        text = findViewById(R.id.internet);
        timer = new Timer();
        recyclerView = findViewById(R.id.posts_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        bundle = getIntent().getExtras();

        model = ViewModelProviders.of(this).get(SearchViewModel.class);

        adapter = new FilmsAdapter();
        adapter.setContext(this);
        adapter.setListener(new FilmsAdapter.OnFilmClickListener() {
            @Override
            public void onFilmClick(Film film) {
                Intent filmDetailsActivity = new Intent(ApiActivity.this, FilmDetailsActivity.class);

                filmDetailsActivity.putExtra(FilmDetailsActivity.ID_KEY, film.filmId);
                startActivity(filmDetailsActivity);
            }
        });

        recyclerView.setAdapter(adapter);

        films = model.getSearch(bundle.get(SEARCH_KEY).toString());
        films.observe(ApiActivity.this, new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> filmsList) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                if (!filmsList.isEmpty()) {
                    progressBar.setVisibility(ProgressBar.GONE);
                    adapter.setFilmsList(filmsList);
                    timer.cancel();
                }
                else {
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(ProgressBar.GONE);
                                    text.setVisibility(TextView.VISIBLE);
                                }
                            });
                        }
                    };
                    timer.schedule(timerTask, 3000);
                }
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
}
