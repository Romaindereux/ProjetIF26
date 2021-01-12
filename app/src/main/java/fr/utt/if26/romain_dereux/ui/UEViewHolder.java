package fr.utt.if26.romain_dereux.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.databinding.LayoutUeItemBinding;
import fr.utt.if26.romain_dereux.databinding.RecyclerviewUeItemBinding;
import fr.utt.if26.romain_dereux.databinding.UeItemBinding;
import fr.utt.if26.romain_dereux.model.UE;

/**
 * Created by Romain on 28 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

public class UEViewHolder extends RecyclerView.ViewHolder {
    private UeItemBinding binding;

    private UEViewHolder(UeItemBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
    public void bind(UE ue) {
        binding.setUe(ue);
    }

    public static UEViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UeItemBinding itemBinding = UeItemBinding.inflate(layoutInflater, parent, false);
        return new UEViewHolder(itemBinding);
    }
}
