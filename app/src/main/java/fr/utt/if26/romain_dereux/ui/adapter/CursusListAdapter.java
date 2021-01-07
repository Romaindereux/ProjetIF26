package fr.utt.if26.romain_dereux.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.ui.CursusViewHolder;
import fr.utt.if26.romain_dereux.ui.ListItemClickListener;

public class CursusListAdapter extends ListAdapter<Cursus, CursusViewHolder> {

    private ListItemClickListener mListItemClickListener;

    public CursusListAdapter(@NonNull DiffUtil.ItemCallback<Cursus> diffCallback, ListItemClickListener listItemClickListener) {
        super(diffCallback);
        this.mListItemClickListener = listItemClickListener;
    }

    @Override
    public CursusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CursusViewHolder.create(parent, mListItemClickListener);
    }

    @Override
    public void onBindViewHolder(CursusViewHolder holder, int position) {
        Cursus current = getItem(position);
        holder.bind(current);
        holder.itemView.setOnClickListener(holder);
    }

    public static class CursusDiff extends DiffUtil.ItemCallback<Cursus> {

        @Override
        public boolean areItemsTheSame(@NonNull Cursus oldItem, @NonNull Cursus newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Cursus oldItem, @NonNull Cursus newItem) {
            return oldItem.getIdentifier().equals(newItem.getIdentifier());
        }
    }
}
