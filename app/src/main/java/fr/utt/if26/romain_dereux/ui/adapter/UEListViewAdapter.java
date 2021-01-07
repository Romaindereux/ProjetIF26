package fr.utt.if26.romain_dereux.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.model.UE;

/**
 * Created by Romain on 28 décembre 2020
 * Contact me at romain.dereux@utt.fr
 *
 */

public class UEListViewAdapter extends ArrayAdapter<UE> {



    public UEListViewAdapter(Context context, int resource, ArrayList<UE> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        String sigle = getItem(position).getSigle();
        Integer credit = getItem(position).getCredit();
        String branche = getItem(position).getBranche();


        LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
        convertView = inflater.inflate(R.layout.layout_ue_item, parent, true);


        TextView tv_sigle = (TextView) convertView.findViewById(R.id.tv_ue_item_sigle);
        TextView tv_credit = (TextView) convertView.findViewById(R.id.tv_ue_item_credit);

        tv_sigle.setText(sigle);
        tv_credit.setText( branche);

        return convertView;

    }
}
