package fr.utt.if26.romain_dereux.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.RecyclerviewCursusItemBinding;
import fr.utt.if26.romain_dereux.model.Cursus;

public class CursusViewHolder extends RecyclerView.ViewHolder {
    private RecyclerviewCursusItemBinding binding;

    private CursusViewHolder(RecyclerviewCursusItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Cursus cursus) {
        binding.setCursus(cursus);
    }

    public static CursusViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerviewCursusItemBinding itemBinding = RecyclerviewCursusItemBinding.inflate(layoutInflater, parent, false);
        return new CursusViewHolder(itemBinding);
    }
}
