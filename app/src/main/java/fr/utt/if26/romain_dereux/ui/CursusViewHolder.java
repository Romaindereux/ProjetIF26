package fr.utt.if26.romain_dereux.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.R;

public class CursusViewHolder extends RecyclerView.ViewHolder {
    private final TextView cursusItemView;

    private CursusViewHolder(View itemView) {
        super(itemView);
        cursusItemView = itemView.findViewById(R.id.rv_cursus_item);
    }

    public void bind(String text) {
        cursusItemView.setText(text);
    }

    public static CursusViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_cursus_item, parent, false);
        return new CursusViewHolder(view);
    }
}
