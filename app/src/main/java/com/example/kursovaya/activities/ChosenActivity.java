package com.example.kursovaya.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.kursovaya.support_classes.ChosenAdapter;
import com.example.kursovaya.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChosenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChosenAdapter adapter;
    private List<String> chosenList;

    private String clickedTitle = "";

    SharedPreferences chosenPref;
    SharedPreferences.Editor editor;

    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener= new PopupMenu.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.add_to_schedule:
                    Intent intent = new Intent(ChosenActivity.this, CalendarActivity.class);
                    intent.putExtra(CalendarActivity.WATCHING_KEY, clickedTitle);
                    intent.putExtra(CalendarActivity.ID_FILM, chosenPref.getInt(clickedTitle, 1));
                    startActivity(intent);
                    break;
                case R.id.delete:
                    editor = chosenPref.edit();
                    editor.remove(clickedTitle);
                    editor.apply();
                    recreate();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen);

        recyclerView = findViewById(R.id.chosen_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chosenPref = ChosenActivity.this.getSharedPreferences("Chosen", Context.MODE_PRIVATE);

        adapter = new ChosenAdapter();
        adapter.setClickListener(new ChosenAdapter.OnChosenClickListener() {
            @Override
            public void chosenClicked(String title) {
                Intent intent = new Intent(ChosenActivity.this, FilmDetailsActivity.class);
                intent.putExtra(FilmDetailsActivity.ID_KEY, chosenPref.getInt(title, 1));
                startActivity(intent);
            }
        });
        adapter.setOnLongClickListener(new ChosenAdapter.OnLongChosenListener() {
            @Override
            public void chosenLongClicked(View v, String title) {
                clickedTitle = title;
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.chosenmenu);
                menu.setOnMenuItemClickListener(onMenuItemClickListener);
                menu.show();
            }
        });

        recyclerView.setAdapter(adapter);

        Map<String, ?> map = chosenPref.getAll();
        chosenList = new ArrayList<>();
        for(Map.Entry<String, ?> entry : map.entrySet()){
            chosenList.add(entry.getKey());
        }
        adapter.setChosenList(chosenList);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
}
