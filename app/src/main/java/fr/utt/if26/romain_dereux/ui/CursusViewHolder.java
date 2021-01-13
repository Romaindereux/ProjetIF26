package fr.utt.if26.romain_dereux.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.RecyclerviewCursusItemBinding;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.ui.adapter.CursusListAdapter;

public class CursusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private RecyclerviewCursusItemBinding binding;
    ListItemClickListener mListItemClickListener;



    private CursusViewHolder(RecyclerviewCursusItemBinding binding, ListItemClickListener listItemClickListener) {
        super(binding.getRoot());
        this.binding = binding;

        this.mListItemClickListener = listItemClickListener;
        this.itemView.setOnClickListener(this);

    }

    public void bind(Cursus cursus) {
        binding.setCursus(cursus);
        if (!cursus.isValid()){
            binding.circleValid.setColorFilter(Color.parseColor("#FF0000"));
        }
    }

    public static CursusViewHolder create(ViewGroup parent, ListItemClickListener listItemClickListener) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerviewCursusItemBinding itemBinding = RecyclerviewCursusItemBinding.inflate(layoutInflater, parent, false);
        return new CursusViewHolder(itemBinding, listItemClickListener);
    }


    @Override
    public void onClick(View view) {
        mListItemClickListener.onListItemClick(getAdapterPosition());
    }
}
