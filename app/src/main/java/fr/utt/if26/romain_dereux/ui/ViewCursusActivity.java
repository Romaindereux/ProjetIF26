package fr.utt.if26.romain_dereux.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.Utility;
import fr.utt.if26.romain_dereux.databinding.ActivityViewCursusBinding;
import fr.utt.if26.romain_dereux.db.Converters;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.ui.adapter.UEListAdapter;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ViewCursusActivity extends AppCompatActivity {

    ActivityViewCursusBinding binding;

    UEListAdapter adapter;

    private UEViewModel ueViewModel;
    private CursusViewModel cursusViewModel;

    public static final String TAG = "ViewCursusActivity";

    private int sumCs=0,sumTm=0,sumMe=0,sumHt=0,sumEc=0;

    private Cursus cursus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_cursus);
        cursus = (Cursus) getIntent().getSerializableExtra("cursus");

        /*  Toolbar */
        ActionBar actionBar = getSupportActionBar();
        String title = getResources().getString(R.string.cursus);
        actionBar.setTitle(title.concat(cursus.getIdentifier()));

        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);
        cursusViewModel = new ViewModelProvider(this).get(CursusViewModel.class);

        bindData();

        addEvent();
    }

    /**
     * Bind all the data from the cursus into the view
     */
    public void bindData(){
        bindListUE(cursus.getListCs(), binding.listCs, "CS");
        bindListUE(cursus.getListTm(), binding.listTm, "TM");
        bindListUE(cursus.getListMe(), binding.listMe, "ME");
        bindListUE(cursus.getListHt(), binding.listHt, "HT");
        bindListUE(cursus.getListEc(), binding.listEc, "EC");

        binding.setSt09(cursus.isSt09());
        binding.setSt10(cursus.isSt10());
        binding.setNpml(cursus.isNpml());
        binding.setBranche(cursus.getBranche());
    }

    /**
     * Add event listener for the checkbox and for longpress on an ue
     */
    public void addEvent(){
        addOnCheckedListener(binding.cbVcaNpml, "NPML");
        addOnCheckedListener(binding.cbVcaSt09, "ST09");
        addOnCheckedListener(binding.cbVcaSt10, "ST10");

        setLongPressOnUe();
    }

    /**
     * Set listener on all list
     */
    public void setLongPressOnUe(){
        setLongPress(binding.listCs, cursus.getListCs(), "CS");
        setLongPress(binding.listTm, cursus.getListTm(), "TM");
        setLongPress(binding.listEc, cursus.getListEc(), "EC");
        setLongPress(binding.listMe, cursus.getListMe(), "ME");
        setLongPress(binding.listHt, cursus.getListHt(), "HT");

    }

    /**
     * On long press, delete the ue from the db, and on the view
     * @param listView
     * @param list
     * @param category
     */
    public void setLongPress(ListView listView, ArrayList<String> list, String category){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                supprUEfromView(list.get(i), category);
                return false;
            }
        });
    }

    /**
     * Suppr Ue from view
     * @param sigle
     * @param category
     */
    public void supprUEfromView(String sigle, String category){
        ArrayList<String> listUE;
        Converters converters = new Converters();
        String listString;
        String[] listUEAdjust;
        switch (category){
            case "CS":
                listUE =  (ArrayList<String>) cursusViewModel.getListCs(cursus.getIdentifier());
                listUEAdjust = listUE.get(0).split(",");
                listUE.clear();
                Collections.addAll(listUE, listUEAdjust);
                listUE.remove(sigle);
                listString = converters.writingStringFromList((ArrayList<String>)listUE);
                cursusViewModel.updateListCs(listString, cursus.getIdentifier());
                cursus.setListCs(listUE);
                bindListUE(listUE,binding.listCs, "CS");
                break;
            case "TM":
                listUE = (ArrayList<String>) cursusViewModel.getListTm(cursus.getIdentifier());
                listUEAdjust = listUE.get(0).split(",");
                listUE.clear();
                Collections.addAll(listUE, listUEAdjust);
                listUE.remove(sigle);
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListTm(listString, cursus.getIdentifier());
                cursus.setListTm(listUE);
                bindListUE(listUE,binding.listTm, "TM");
                break;
            case "EC":
                listUE = (ArrayList<String>) cursusViewModel.getListEc(cursus.getIdentifier());
                listUEAdjust = listUE.get(0).split(",");
                listUE.clear();
                Collections.addAll(listUE, listUEAdjust);
                listUE.remove(sigle);
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListEc(listString, cursus.getIdentifier());
                cursus.setListEc(listUE);
                bindListUE(listUE,binding.listEc, "EC");
                break;
            case "ME":
                listUE = (ArrayList<String>) cursusViewModel.getListMe(cursus.getIdentifier());
                listUEAdjust = listUE.get(0).split(",");
                listUE.clear();
                Collections.addAll(listUE, listUEAdjust);
                listUE.remove(sigle);
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListMe(listString, cursus.getIdentifier());
                cursus.setListMe(listUE);
                bindListUE(listUE,binding.listMe, "ME");
                break;
            case "HT":
                listUE = (ArrayList<String>) cursusViewModel.getListHt(cursus.getIdentifier());
                listUEAdjust = listUE.get(0).split(",");
                listUE.clear();
                Collections.addAll(listUE, listUEAdjust);
                listUE.remove(sigle);
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListHt(listString, cursus.getIdentifier());
                cursus.setListHt(listUE);
                bindListUE(listUE,binding.listHt, "HT");
                break;
        }
        cursus.setValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180);
        cursusViewModel.updateValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180, cursus.getIdentifier());


    }

    /**
     * get the sum of credit of the given cursus
     * @param cursus Cursus: the cursus to get the sum of credit
     * @return int: the sum of credit of the given cursus
     */
    public int getSumCreditCursus(Cursus cursus){
        int sum=0;
        for (String sigle : cursus.getListCs()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListTm()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListEc()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListMe()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        for (String sigle : cursus.getListHt()){
            sum += ueViewModel.getUEBySigle(sigle).get(0).getCredit();
        }
        if(cursus.isSt09()){
            sum += 30;
        }
        if(cursus.isSt10()){
            sum += 30;
        }
        return sum;
    }


    /**
     * Add a listener on the given checkbox
     * @param checkBox
     * @param checkboxTitle
     */
    public void addOnCheckedListener(CheckBox checkBox, String checkboxTitle){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (checkboxTitle){
                    case "NPML":
                        //Change npml in db
                        cursusViewModel.updateNpml(b, cursus.getIdentifier());
                        cursus.setNpml(b);
                        break;
                    case "ST09":
                        cursusViewModel.updateSt09(b, cursus.getIdentifier());
                        cursus.setSt09(b);
                        break;
                    case "ST10":
                        cursusViewModel.updateSt10(b, cursus.getIdentifier());
                        cursus.setSt10(b);
                        break;
                }
                cursus.setValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180);
                cursusViewModel.updateValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180, cursus.getIdentifier());
                binding.setSumTotal(String.valueOf(getSumCreditCursus(cursus)).concat(" / 180"));
            }
        });
    }

    /**
     * Reinitialize the given sum
     * @param category
     */
    public void renitializeSums(String category){
        switch (category){
            case "CS":
                sumCs = 0;
                break;
            case "TM":
                sumTm = 0;
                break;
            case "EC":
                sumEc = 0;
                break;
            case "ME":
                sumMe = 0;
                break;
            case "HT":
                sumHt = 0;
                break;
        }

    }

    /**
     * Bind the list into the view
     * @param listString
     * @param listToBind
     * @param category
     */
    public void bindListUE(ArrayList<String> listString, ListView listToBind, String category) {
        ListView listView = listToBind;
        ArrayList<UE> listUE = new ArrayList<>();
        Log.d(TAG, listString.toString());
        renitializeSums(category);
        for (String s : listString) {
            Log.d(TAG, s);
            UE ue = ueViewModel.getUEBySigle(s).get(0);
            listUE.add(ue);
            if (category == "CS") {
                sumCs += ue.getCredit();
                binding.setSumCs(String.valueOf(sumCs).concat(" / 24"));
                if(sumCs >= 24){
                    binding.tvSumCs.setTextColor(getResources().getColor(R.color.green));
                }else{
                    binding.tvSumCs.setTextColor(getResources().getColor(R.color.red));
                }
            } else if (category == "TM") {
                sumTm += ue.getCredit();
                binding.setSumTm(String.valueOf(sumTm).concat(" / 24"));
                if(sumTm >= 24){
                    binding.tvSumTm.setTextColor(getResources().getColor(R.color.green));
                }else{
                    binding.tvSumTm.setTextColor(getResources().getColor(R.color.red));
                }
            } else if (category == "ME") {
                sumMe += ue.getCredit();
                binding.setSumMe(String.valueOf(sumMe).concat(" / 4"));
                if(sumMe >= 4){
                    binding.tvSumMe.setTextColor(getResources().getColor(R.color.green));
                }else{
                    binding.tvSumMe.setTextColor(getResources().getColor(R.color.red));
                }
            } else if (category == "HT") {
                sumHt += ue.getCredit();
                binding.setSumHt(String.valueOf(sumHt).concat(" / 4"));
                if(sumHt >= 4){
                    binding.tvSumHt.setTextColor(getResources().getColor(R.color.green));
                }else{
                    binding.tvSumHt.setTextColor(getResources().getColor(R.color.red));
                }
            } else if (category == "EC") {
                sumEc += ue.getCredit();
                binding.setSumEc(String.valueOf(sumEc).concat(" / 12"));
                if(sumEc >= 12){
                    binding.tvSumEc.setTextColor(getResources().getColor(R.color.green));
                }else{
                    binding.tvSumEc.setTextColor(getResources().getColor(R.color.red));
                }
            }
            binding.setSumCsTm(String.valueOf(sumCs + sumTm).concat(" / 84"));
            if((sumCs + sumTm) >= 84){
                binding.tvSumCsTm.setTextColor(getResources().getColor(R.color.green));
            }else{
                binding.tvSumCsTm.setTextColor(getResources().getColor(R.color.red));
            }
            binding.setSumMeHt(String.valueOf(sumMe + sumHt).concat(" / 16"));
            if((sumMe + sumHt) >= 16){
                binding.tvSumMeHt.setTextColor(getResources().getColor(R.color.green));
            }else{
                binding.tvSumMeHt.setTextColor(getResources().getColor(R.color.red));
            }
            int internship = 0;
            if(cursus.isSt09()){
                internship += 30;
            }
            if(cursus.isSt10()){
                internship += 30;
            }
            int sumCredit = sumCs + sumTm + sumMe + sumHt + sumEc + internship;
            binding.setSumTotal(String.valueOf(sumCredit).concat(" / 180"));
            if((sumCredit) >= 180){
                binding.tvSumTotal.setTextColor(getResources().getColor(R.color.green));
            }else{
                binding.tvSumTotal.setTextColor(getResources().getColor(R.color.red));
            }

        }
        Log.d(TAG, listUE.toString());
        adapter = new UEListAdapter(this, listUE);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listToBind);
    }


    /**
     * Create the options menu in the action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_view_cursus, menu);
        return true;
    }

    /**
     * Add an event listener to the click of the action bar
     * @param item
     * @return
     */
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
            case R.id.action_duplicate_cursus:
                duplicateCursus();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Refresh data given the new UE
     * @param ue
     */
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

    /**
     * Function that duplicate the cursus in the database and then display a toast
     */
    public void duplicateCursus(){
        cursus.setIdentifier(cursus.getIdentifier().concat("-copy"));
        cursus.setValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180);
        cursusViewModel.insert(cursus);
        Toast.makeText(
                getApplicationContext(),
                "Cursus dupliqué",
                Toast.LENGTH_LONG).show();
    }
}