package fr.utt.if26.romain_dereux.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.ui.CursusViewHolder;
import fr.utt.if26.romain_dereux.ui.UEViewHolder;

/**
 * Created by Romain on 12 janvier 2021
 * Contact me at romain.dereux@utt.fr
 */

public class UeAdapter extends ListAdapter<UE, UEViewHolder> {


    public UeAdapter(@NonNull DiffUtil.ItemCallback<UE> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public UEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UEViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UEViewHolder holder, int position) {
        UE current = getItem(position);
        holder.bind(current);
    }
    public static class UeDiff extends DiffUtil.ItemCallback<UE> {

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
