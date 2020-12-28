package fr.utt.if26.romain_dereux.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.ui.UEViewHolder;

/**
 * Created by Romain on 28 décembre 2020
 * Contact me at romain.dereux@utt.fr
 *
 * TODO: Delete it later
 */

public class UEListAdapter extends ListAdapter<UE, UEViewHolder> {
    public UEListAdapter(@NonNull DiffUtil.ItemCallback<UE> diffCallback) {
        super(diffCallback);
    }

    @Override
    public UEViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UEViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(UEViewHolder holder, int position) {
        UE current = getItem(position);
        holder.bind(current);
    }

    public static class UEDiff extends DiffUtil.ItemCallback<UE> {

        @Override
        public boolean areItemsTheSame(@NonNull UE oldItem, @NonNull UE newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UE oldItem, @NonNull UE newItem) {
            return oldItem.getSigle().equals(newItem.getSigle());
        }
    }
}
