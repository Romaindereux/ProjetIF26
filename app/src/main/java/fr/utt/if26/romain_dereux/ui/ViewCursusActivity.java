package fr.utt.if26.romain_dereux.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.ActivityViewCursusBinding;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.ui.adapter.UEListAdapter;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewCursusActivity extends AppCompatActivity {

    ActivityViewCursusBinding binding;

    ListView listViewCS, listViewTM;

    UEListAdapter adapter, adapterTM;

    private UEViewModel ueViewModel;

    public static final String TAG = "ViewCursusActivity";

    private int sumCs=0, sumTm=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_cursus);
        Cursus cursus = (Cursus) getIntent().getSerializableExtra("cursus");

        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);

        listViewCS = (ListView) binding.listCs;
        ArrayList<String> listCsString = cursus.getListCs();
        ArrayList<UE> listCs = new ArrayList<>();
        for(String s : listCsString){
            ueViewModel.getUEBySigle(s).observe(this, new Observer<List<UE>>() {
                @Override
                public void onChanged(List<UE> ues) {
                    for(UE ue: ues){
                        listCs.add(ue);
                        Log.d(TAG, ue.getBranche());
                        sumCs += ue.getCredit();
                        binding.setSumCs(String.valueOf(sumCs).concat(" / 24"));
                    }
                }
            });
        }
        adapter = new UEListAdapter(this, listCs);
        listViewCS.setAdapter(adapter);

        //TM

        listViewTM = (ListView) binding.listTm;
        ArrayList<String> listTmString = cursus.getListTm();
        ArrayList<UE> listTm = new ArrayList<>();
        for(String s : listTmString){
            ueViewModel.getUEBySigle(s).observe(this, new Observer<List<UE>>() {
                @Override
                public void onChanged(List<UE> ues) {
                    for(UE ue: ues){
                        listTm.add(ue);
                        sumTm += ue.getCredit();
                        binding.setSumTm(String.valueOf(sumTm).concat(" / 24"));
                    }
                }
            });
        }
        adapter = new UEListAdapter(this, listTm);
        listViewTM.setAdapter(adapter);





    }
}