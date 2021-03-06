package fr.utt.if26.romain_dereux.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import fr.utt.if26.romain_dereux.MyApp;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityNewCursusBinding;
import fr.utt.if26.romain_dereux.databinding.DialogNewUeBinding;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

/**
 * Created by Romain on 16 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

public class DialogNewUE extends DialogFragment {
    public static final String TAG = "DialogNewUE";
    private UEViewModel ueViewModel;


    private DialogNewUeBinding binding;


    private String category;
    private String branche;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        //View view = inflater.inflate(R.layout.dialog_new_ue, container, false);

        binding = DialogNewUeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.setDialogNewUE(this);

        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);

        return view;
    }

    public void cancelCreationUE(){
        Log.d(TAG, "onClick: close dialog");
        getDialog().dismiss();
    }

    public void createUE(){
        if (binding.dNueEtSigle.getText().toString().matches("") || binding.dNueEtCredit.getText().toString().matches("")) {
            Toast.makeText(MyApp.getContext(), "You did not fill all the field", Toast.LENGTH_SHORT).show();
        }else{
            String sigleInput = binding.getSigle();
            Integer creditInput = Integer.valueOf(binding.getCredit());
            if(!sigleInput.equals("")){
                UE ue = new UE(sigleInput, branche, category, creditInput);
                ueViewModel.insert(ue);
                NewCursusActivity newCursusActivity = (NewCursusActivity) getActivity();
                newCursusActivity.addUE(sigleInput, category);
            }
            getDialog().dismiss();
        }

    }

    public void setCategory(String category){
        this.category = category;
    }
    public void setBranche(String branche){
        this.branche = branche;
    }
}
