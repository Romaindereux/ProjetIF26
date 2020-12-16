package fr.utt.if26.romain_dereux.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import fr.utt.if26.romain_dereux.R;

/**
 * Created by Romain on 16 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

public class DialogNewUE extends DialogFragment {
    public static final String TAG = "DialogNewUE";

    //TODO add data binding
    private EditText sigle, credit;
    private Button createButton, cancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_new_ue, container, false);
        createButton = view.findViewById(R.id.d_nue_bt_create);
        cancelButton = view.findViewById(R.id.d_nue_bt_retour);
        sigle = view.findViewById(R.id.d_nue_et_sigle);
        credit = view.findViewById(R.id.d_nue_et_credit);

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: close dialog");
                getDialog().dismiss();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: new ue");
                String sigleInput = sigle.getText().toString();
                Integer creditInput = Integer.valueOf(credit.getText().toString());
                if(!sigleInput.equals("")){
                    ((NewCursusActivity)getActivity()).sigleDB = sigleInput;
                    ((NewCursusActivity)getActivity()).creditDB = creditInput;
                    //TODO Stock it in the database
                    //TODO Send the sigle(pk) to the activity and display it
                }
                getDialog().dismiss();
            }
        });
        return view;
    }
}
