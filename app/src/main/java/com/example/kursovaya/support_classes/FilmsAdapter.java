package com.example.kursovaya.support_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.kursovaya.R;
import com.example.kursovaya.dbclasses.Film;
import com.squareup.picasso.Picasso;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.FilmsHolder> {

    public interface OnFilmClickListener{
        void onFilmClick(Film film);
    }

    private List<Film> filmsList;
    private OnFilmClickListener listener;
    private Context context;

    class FilmsHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView overview;
        ImageView image;

        public FilmsHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.element_1);
            overview = itemView.findViewById(R.id.element_2);
            image = itemView.findViewById(R.id.image);
        }

        public void bind(Film film, OnFilmClickListener listener){
            if (!film.releaseDate.equals("")) {
                this.title.setText(film.title + " (" + film.releaseDate.substring(0, 4) + ")");
            }
            else {
                this.title.setText(film.title);
            }
            String overviewStr = film.overview;
            if(overviewStr.length() >= 180) {
                this.overview.setText(overviewStr.substring(0, 180) + "...");
            }
            else{
                this.overview.setText(overviewStr);
            }
            Picasso.with(context).load("https://image.tmdb.org/t/p/w185" + film.posterPath).fit().into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFilmClick(film);
                }
            });
        }
    }


    @NonNull
    @Override
    public FilmsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recv_pair, parent, false);
        return new FilmsHolder(v);
    }

    @Override
    public void onBindViewHolder(FilmsHolder holder, int position) {
        holder.bind(filmsList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if(this.filmsList == null) return 0;
        return this.filmsList.size();
    }

    public void setFilmsList(List<Film> filmsList){
        this.filmsList = filmsList;
        notifyDataSetChanged();
    }

    public void setListener(OnFilmClickListener listener){
        this.listener = listener;
    }

    public void setContext(Context context){
        this.context = context;
    }

}