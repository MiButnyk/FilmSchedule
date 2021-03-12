package com.example.kursovaya.support_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya.R;

import java.util.List;

public class ChosenAdapter extends RecyclerView.Adapter<ChosenAdapter.ChosenHolder> {

    public interface OnLongChosenListener{
        void chosenLongClicked(View v, String title);
    }

    public interface OnChosenClickListener{
        void chosenClicked(String title);
    }

    private OnLongChosenListener longListener;
    private OnChosenClickListener clickListener;
    private List<String> chosen;

    class ChosenHolder extends RecyclerView.ViewHolder{
        TextView title;

        public ChosenHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recv_elem);
        }

        public void bind(String title) {
            this.title.setText(title);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.chosenLongClicked(v, title);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.chosenClicked(title);
                }
            });
        }
    }

    @NonNull
    @Override
    public ChosenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recv, parent, false);
        return new ChosenHolder(v);
    }

    @Override
    public void onBindViewHolder(ChosenHolder holder, int position) {
        holder.bind(chosen.get(position));
    }

    @Override
    public int getItemCount() {
        if (this.chosen == null) return 0;
        return this.chosen.size();
    }

    public void setChosenList(List<String> chosen) {
        this.chosen = chosen;
        notifyDataSetChanged();
    }

    public void setOnLongClickListener(OnLongChosenListener longListener){
        this.longListener = longListener;
    }

    public void setClickListener(OnChosenClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
