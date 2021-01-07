package fr.utt.if26.romain_dereux.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.ui.CursusViewHolder;
import fr.utt.if26.romain_dereux.ui.UEViewHolder;

/**
 * Created by Romain on 28 décembre 2020
 * Contact me at romain.dereux@utt.fr
 *
 */

public class UEListAdapter extends BaseAdapter {

    Activity context;
    ArrayList<UE> listUEs;
    private static LayoutInflater inflater = null;

    public UEListAdapter(Activity context, ArrayList<UE> listUE){
        this.context = context;
        this.listUEs = listUE;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listUEs.size();
    }

    @Override
    public Object getItem(int i) {
        return listUEs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        itemView = (itemView == null) ? inflater.inflate(R.layout.layout_ue_item, null): itemView;

        TextView tv_sigle = (TextView) itemView.findViewById(R.id.tv_ue_item_sigle);
        TextView tv_credit = (TextView) itemView.findViewById(R.id.tv_ue_item_credit);

        UE selectedUE = listUEs.get(i);
        tv_sigle.setText(selectedUE.getSigle());
        String credit = String.valueOf(selectedUE.getCredit());
        tv_credit.setText(credit);

        return itemView;
    }
}
