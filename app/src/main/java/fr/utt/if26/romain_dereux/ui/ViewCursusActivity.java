package fr.utt.if26.romain_dereux.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.Utility;
import fr.utt.if26.romain_dereux.databinding.ActivityViewCursusBinding;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.ui.adapter.UEListAdapter;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewCursusActivity extends AppCompatActivity {

    ActivityViewCursusBinding binding;

    UEListAdapter adapter;

    private UEViewModel ueViewModel;
    private CursusViewModel cursusViewModel;

    public static final String TAG = "ViewCursusActivity";

    private int sumCs=0,sumTm=0,sumMe=0,sumHt=0,sumEc=0;

    private Cursus cursus;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_view_cursus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete_cursus:
                //DELETE cursus and close intent
                String identifier = cursus.getIdentifier();
                finish();
                cursusViewModel.deleteFromIdentifier(identifier);
                return true;
            case R.id.action_add_ue:
                DialogAddUE dialogAddUE = new DialogAddUE();
                String branche = cursus.getBranche();
                dialogAddUE.setBranche(branche);
                dialogAddUE.setIdentifier(cursus.getIdentifier());
                dialogAddUE.show(getSupportFragmentManager(), "new ue");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshData(UE ue){
        ArrayList<String> list;
        switch (ue.getCategory()){
            case "CS":
                list = cursus.getListCs();
                list.add(ue.getSigle());
                bindListUE(list, binding.listCs, "CS");
                break;
            case "TM":
                list = cursus.getListTm();
                list.add(ue.getSigle());
                bindListUE(list, binding.listTm, "TM");
                break;
            case "EC":
                list = cursus.getListEc();
                list.add(ue.getSigle());
                bindListUE(list, binding.listEc, "EC");
                break;
            case "ME":
                list = cursus.getListMe();
                list.add(ue.getSigle());
                bindListUE(list, binding.listMe, "ME");
                break;
            case "HT":
                list = cursus.getListHt();
                list.add(ue.getSigle());
                bindListUE(list, binding.listHt, "HT");
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_cursus);

        /*  Toolbar */
        ActionBar actionBar = getSupportActionBar();
        cursus = (Cursus) getIntent().getSerializableExtra("cursus");
        String title = getResources().getString(R.string.cursus);
        actionBar.setTitle(title.concat(cursus.getIdentifier()));




        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);
        cursusViewModel = new ViewModelProvider(this).get(CursusViewModel.class);

        bindListUE(cursus.getListCs(), binding.listCs, "CS");
        bindListUE(cursus.getListTm(), binding.listTm, "TM");
        bindListUE(cursus.getListMe(), binding.listMe, "ME");
        bindListUE(cursus.getListHt(), binding.listHt, "HT");
        bindListUE(cursus.getListEc(), binding.listEc, "EC");

        binding.setSt09(cursus.isSt09());
        binding.setSt10(cursus.isSt10());
        binding.setNpml(cursus.isNpml());

    }
    public void renitializeSums(){
        sumCs=0;
        sumTm=0;
        sumHt=0;
        sumMe=0;
        sumEc=0;
    }

    public void bindListUE(ArrayList<String> listString, ListView listToBind, String category) {
        ListView listView = listToBind;
        ArrayList<UE> listUE = new ArrayList<>();
        Log.d(TAG, listString.toString());
        renitializeSums();
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

        }
        Log.d(TAG, listUE.toString());
        adapter = new UEListAdapter(this, listUE);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listToBind);
    }
}