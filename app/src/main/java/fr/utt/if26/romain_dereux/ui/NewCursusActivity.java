package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import fr.utt.if26.romain_dereux.MyApp;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityNewCursusBinding;
import fr.utt.if26.romain_dereux.db.dao.UEDao;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewCursusActivity extends AppCompatActivity implements IAddCursus{

    public static final String TAG = "NewCursusActivity";

    public static final String EXTRA_REPLY_ID = "identifier";
    public static final String EXTRA_REPLY_BRANCHE = "branche";
    private ActivityNewCursusBinding binding;

    public String sigleDB;
    public Integer creditDB;

    private List<String> listCsSigle;
    private List<Integer> listCsCredit;
    private UEViewModel ueViewModel;
    private Spinner spinner;
    LifecycleOwner lifecycleOwner = this;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_cursus);

        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);

        listCsSigle = new ArrayList<>();
        ArrayAdapter<String> spinnerAdapterCs = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCsSigle);
        spinnerAdapterCs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCs.setAdapter(spinnerAdapterCs);

        Cursus cursus = new Cursus("", "");
        cursus.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                String branche = getResources().getStringArray(R.array.branches_array)[binding.getCursus().getBrancheID()];
                Log.d(TAG, branche);
                //TODO: getUE FROM BRANCHE AND ONLY CS
                Log.d(TAG, binding.ncaSpinnerBranche.getSelectedItem().toString());
                ueViewModel.getUEByBrancheAndCategory(branche, "CS").observe(lifecycleOwner, new Observer<List<UE>>() {
                    @Override
                    public void onChanged(List<UE> ues) {
                        listCsSigle.clear();
                        for(UE ue: ues){
                            listCsSigle.add(ue.getSigle());
                            listCsCredit.add(ue.getCredit());
                        }
                        spinnerAdapterCs.notifyDataSetChanged();
                    }
                });
            }
        });
        binding.setCursus(cursus);
        //TODO: See if it's possible to delete the interface
        binding.setIAddCursus((IAddCursus) this);
        binding.setNewCursusActivity(this);


        listCsCredit = new ArrayList<>();

        String branche = getResources().getStringArray(R.array.branches_array)[binding.getCursus().getBrancheID()];
        Log.d(TAG, branche);
        //TODO: getUE FROM BRANCHE AND ONLY CS
        Log.d(TAG, binding.ncaSpinnerBranche.getSelectedItem().toString());
        ueViewModel.getUEByBrancheAndCategory(branche, "CS").observe(lifecycleOwner, new Observer<List<UE>>() {
            @Override
            public void onChanged(List<UE> ues) {
                listCsSigle.clear();
                for(UE ue: ues){
                    listCsSigle.add(ue.getSigle());
                    listCsCredit.add(ue.getCredit());
                }
                spinnerAdapterCs.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void inflateNewCursus(){
        Intent replyIntent = new Intent();
        String identifier = binding.getCursus().getIdentifier();
        int brancheID = binding.getCursus().getBrancheID();
        String branche = getResources().getStringArray(R.array.branches_array)[brancheID];
        replyIntent.putExtra(EXTRA_REPLY_ID, identifier);
        replyIntent.putExtra(EXTRA_REPLY_BRANCHE, branche);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void inflateNewUE(String category){
        Toast.makeText(this, "New cs", Toast.LENGTH_LONG).show();
        DialogNewUE dialogNewUE = new DialogNewUE();
        dialogNewUE.setCategory(category);
        String branche = getResources().getStringArray(R.array.branches_array)[binding.getCursus().getBrancheID()];
        dialogNewUE.setBranche(branche);
        dialogNewUE.show(getSupportFragmentManager(), "new cs");


    }

}