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

    UEListAdapter adapter;

    private UEViewModel ueViewModel;

    public static final String TAG = "ViewCursusActivity";

    private int sumCs=0,sumTm=0,sumMe=0,sumHt=0,sumEc=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_cursus);
        Cursus cursus = (Cursus) getIntent().getSerializableExtra("cursus");

        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);

        bindListUE(cursus.getListCs(), binding.listCs, "CS");
        bindListUE(cursus.getListTm(), binding.listTm, "TM");
        bindListUE(cursus.getListMe(), binding.listMe, "ME");
        bindListUE(cursus.getListHt(), binding.listHt, "HT");
        bindListUE(cursus.getListEc(), binding.listEc, "EC");

        binding.setSt09(cursus.isSt09());
        binding.setSt10(cursus.isSt10());
        binding.setNpml(cursus.isNpml());

    }

    public void bindListUE(ArrayList<String> listString, ListView listToBind, String category) {
        ListView listView = listToBind;
        ArrayList<UE> listUE = new ArrayList<>();
        Log.d(TAG, listString.toString());
        for (String s : listString) {
            Log.d(TAG, s);
            UE ue = ueViewModel.getUEBySigle(s).get(0);
            listUE.add(ue);
            if (category == "CS") {
                sumCs += ue.getCredit();
                binding.setSumCs(String.valueOf(sumCs).concat(" / 24"));
            } else if (category == "TM") {
                sumTm += ue.getCredit();
                binding.setSumTm(String.valueOf(sumTm).concat(" / 24"));
            } else if (category == "ME") {
                sumMe += ue.getCredit();
                binding.setSumMe(String.valueOf(sumMe).concat(" / 4"));
            } else if (category == "HT") {
                sumHt += ue.getCredit();
                binding.setSumHt(String.valueOf(sumHt).concat(" / 4"));
            } else if (category == "EC") {
                sumEc += ue.getCredit();
                binding.setSumEc(String.valueOf(sumEc).concat(" / 12"));
            }
            binding.setSumCsTm(String.valueOf(sumCs + sumTm).concat(" / 84"));
            binding.setSumMeHt(String.valueOf(sumMe + sumHt).concat(" / 16"));
            binding.setSumTotal(String.valueOf(sumCs + sumTm + sumMe + sumHt + sumEc).concat(" / 180"));

//            ueViewModel.getUEBySigle(s).observe(this, new Observer<List<UE>>() {
//                @Override
//                public void onChanged(List<UE> ues) {
//                    Log.d(TAG, "ici");
//                    for(UE ue: ues){
//                        listUE.add(ue);
//                        if (category == "CS"){
//                            sumCs += ue.getCredit();
//                            binding.setSumCs(String.valueOf(sumCs).concat(" / 24"));
//                        }else if (category == "TM"){
//                            sumTm += ue.getCredit();
//                            binding.setSumTm(String.valueOf(sumCs).concat(" / 24"));
//                        }else if (category == "ME"){
//                            sumMe += ue.getCredit();
//                            binding.setSumMe(String.valueOf(sumMe).concat(" / 4"));
//                        }else if (category == "HT"){
//                            sumHt += ue.getCredit();
//                            binding.setSumHt(String.valueOf(sumHt).concat(" / 4"));
//                        }else if (category == "EC"){
//                            sumEc += ue.getCredit();
//                            binding.setSumEc(String.valueOf(sumEc).concat(" / 12"));
//                        }
//
//
//                    }
//                    binding.setSumCsTm(String.valueOf(sumCs + sumTm).concat(" / 84"));
//                    binding.setSumMeHt(String.valueOf(sumMe + sumHt).concat(" / 16"));
//                    binding.setSumTotal(String.valueOf(sumCs + sumTm + sumMe + sumHt + sumEc).concat(" / 180"));
//                }
//            });
        }
        Log.d(TAG, listUE.toString());
        adapter = new UEListAdapter(this, listUE);
        listView.setAdapter(adapter);
    }
}