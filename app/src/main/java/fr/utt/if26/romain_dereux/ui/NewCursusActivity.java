package fr.utt.if26.romain_dereux.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utt.if26.romain_dereux.MyApp;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.Utility;
import fr.utt.if26.romain_dereux.databinding.ActivityNewCursusBinding;
import fr.utt.if26.romain_dereux.db.dao.UEDao;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewCursusActivity extends AppCompatActivity implements IAddCursus{

    public static final String TAG = "NewCursusActivity";

    public static final String EXTRA_REPLY_BRANCHE = "branche";
    public static final String EXTRA_REPLY_CURSUS = "cursus";
    private ActivityNewCursusBinding binding;


    private List<String> listCsSigle, listTmSigle, listEcSigle, listMeSigle, listHtSigle;
    private List<Integer> listCsCredit, listTmCredit, listEcCredit, listMeCredit, listHtCredit;



    private UEViewModel ueViewModel;

    private ListView listViewCS, listViewTM, listViewEC, listViewME, listViewHT;
    private ArrayList<String> listCs, listTm, listEc, listMe, listHt;
    private ArrayAdapter<String> listCSAdapter, listTMAdapter, listECAdapter, listMEAdapter, listHTAdapter;

    private Switch switchNpml;

    LifecycleOwner lifecycleOwner = this;


    private int check = 0, checkTm=0, checkMe = 0, checkHt=0, checkEc=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_cursus);

        /*  Toolbar */

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.creation_cursus);



        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);
        listCsSigle = new ArrayList<>();
        listTmSigle = new ArrayList<>();
        listEcSigle = new ArrayList<>();
        listMeSigle = new ArrayList<>();
        listHtSigle = new ArrayList<>();

        // Spinner CS
        ArrayAdapter<String> spinnerAdapterCs = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCsSigle);
        spinnerAdapterCs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCs.setAdapter(spinnerAdapterCs);

        //Spinner TM
        ArrayAdapter<String> spinnerAdapterTm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listTmSigle);
        spinnerAdapterTm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTm.setAdapter(spinnerAdapterTm);

        //Spinner EC
        ArrayAdapter<String> spinnerAdapterEc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listEcSigle);
        spinnerAdapterEc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerEc.setAdapter(spinnerAdapterEc);

        //Spinner ME
        ArrayAdapter<String> spinnerAdapterMe = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listMeSigle);
        spinnerAdapterMe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMe.setAdapter(spinnerAdapterMe);

        //Spinner HT
        ArrayAdapter<String> spinnerAdapterHt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listHtSigle);
        spinnerAdapterHt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerHt.setAdapter(spinnerAdapterHt);



        Cursus cursus = new Cursus("", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false, false, false);
        cursus.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                String branche = getResources().getStringArray(R.array.branches_array)[binding.getCursus().getBrancheID()];
                listCs.clear();
                listTm.clear();
                listEc.clear();
                listMe.clear();
                listHt.clear();
                ueViewModel.getUEByBrancheAndCategory(branche, "CS").observe(lifecycleOwner, new Observer<List<UE>>() {
                    @Override
                    public void onChanged(List<UE> ues) {
                        listCsSigle.clear();
                        for(UE ue: ues){
                            listCsSigle.add(ue.getSigle());
                            listCsCredit.add(ue.getCredit());
                        }
                        spinnerAdapterCs.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(listViewCS);
                    }
                });
                ueViewModel.getUEByBrancheAndCategory(branche, "TM").observe(lifecycleOwner, new Observer<List<UE>>() {
                    @Override
                    public void onChanged(List<UE> ues) {
                        listTmSigle.clear();
                        for(UE ue: ues){
                            listTmSigle.add(ue.getSigle());
                            listTmCredit.add(ue.getCredit());
                        }
                        spinnerAdapterTm.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(listViewTM);
                    }
                });
                ueViewModel.getUEByCategory("EC").observe(lifecycleOwner, new Observer<List<UE>>() {
                    @Override
                    public void onChanged(List<UE> ues) {
                        listEcSigle.clear();
                        for(UE ue: ues){
                            listEcSigle.add(ue.getSigle());
                            listEcCredit.add(ue.getCredit());
                        }
                        spinnerAdapterEc.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(listViewEC);
                    }
                });
                ueViewModel.getUEByCategory("ME").observe(lifecycleOwner, new Observer<List<UE>>() {
                    @Override
                    public void onChanged(List<UE> ues) {
                        listMeSigle.clear();
                        for(UE ue: ues){
                            listMeSigle.add(ue.getSigle());
                            listMeCredit.add(ue.getCredit());
                        }
                        spinnerAdapterMe.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(listViewME);
                    }
                });
                ueViewModel.getUEByCategory("HT").observe(lifecycleOwner, new Observer<List<UE>>() {
                    @Override
                    public void onChanged(List<UE> ues) {
                        listHtSigle.clear();
                        for(UE ue: ues){
                            listHtSigle.add(ue.getSigle());
                            listHtCredit.add(ue.getCredit());
                        }
                        spinnerAdapterHt.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(listViewHT);
                    }
                });

            }
        });
        binding.setCursus(cursus);
        //TODO: See if it's possible to delete the interface
        binding.setIAddCursus((IAddCursus) this);
        binding.setNewCursusActivity(this);


        listCsCredit = new ArrayList<>();
        listTmCredit = new ArrayList<>();
        listEcCredit = new ArrayList<>();
        listMeCredit = new ArrayList<>();
        listHtCredit = new ArrayList<>();

        String branche = getResources().getStringArray(R.array.branches_array)[binding.getCursus().getBrancheID()];
        ueViewModel.getUEByBrancheAndCategory(branche, "CS").observe(lifecycleOwner, new Observer<List<UE>>() {
            @Override
            public void onChanged(List<UE> ues) {
                listCsSigle.clear();
                for(UE ue: ues){
                    listCsSigle.add(ue.getSigle());
                    listCsCredit.add(ue.getCredit());
                }
                spinnerAdapterCs.notifyDataSetChanged();
                Utility.setListViewHeightBasedOnChildren(listViewCS);
            }
        });
        ueViewModel.getUEByBrancheAndCategory(branche, "TM").observe(lifecycleOwner, new Observer<List<UE>>() {
            @Override
            public void onChanged(List<UE> ues) {
                listTmSigle.clear();
                for(UE ue: ues){
                    listTmSigle.add(ue.getSigle());
                    listTmCredit.add(ue.getCredit());
                }
                spinnerAdapterTm.notifyDataSetChanged();
                Utility.setListViewHeightBasedOnChildren(listViewTM);
            }
        });
        ueViewModel.getUEByCategory("EC").observe(lifecycleOwner, new Observer<List<UE>>() {
            @Override
            public void onChanged(List<UE> ues) {
                listEcSigle.clear();
                for(UE ue: ues){
                    listEcSigle.add(ue.getSigle());
                    listEcCredit.add(ue.getCredit());
                }
                spinnerAdapterEc.notifyDataSetChanged();
                Utility.setListViewHeightBasedOnChildren(listViewEC);
            }
        });
        ueViewModel.getUEByCategory("ME").observe(lifecycleOwner, new Observer<List<UE>>() {
            @Override
            public void onChanged(List<UE> ues) {
                listMeSigle.clear();
                for(UE ue: ues){
                    listMeSigle.add(ue.getSigle());
                    listMeCredit.add(ue.getCredit());
                }
                spinnerAdapterMe.notifyDataSetChanged();
                Utility.setListViewHeightBasedOnChildren(listViewME);
            }
        });
        ueViewModel.getUEByCategory( "HT").observe(lifecycleOwner, new Observer<List<UE>>() {
            @Override
            public void onChanged(List<UE> ues) {
                listHtSigle.clear();
                for(UE ue: ues){
                    listHtSigle.add(ue.getSigle());
                    listHtCredit.add(ue.getCredit());
                }
                spinnerAdapterHt.notifyDataSetChanged();
                Utility.setListViewHeightBasedOnChildren(listViewHT);
            }
        });


        /* ListView CS */
        listViewCS = binding.lvCs;
        listCs = new ArrayList<String>();
        //listCs.add("NF16");       //Only for test
        listCSAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listCs);
        listViewCS.setAdapter(listCSAdapter);

        /* ListView TM */
        listViewTM = binding.lvTm;
        listTm = new ArrayList<String>();
        listTMAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTm);
        listViewTM.setAdapter(listTMAdapter);

        /* ListView EC */
        listViewEC = binding.lvEc;
        listEc = new ArrayList<String>();
        listECAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listEc);
        listViewEC.setAdapter(listECAdapter);

        /* ListView ME */
        listViewME = binding.lvMe;
        listMe = new ArrayList<String>();
        listMEAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMe);
        listViewME.setAdapter(listMEAdapter);

        /* ListView HT */
        listViewHT = binding.lvHt;
        listHt = new ArrayList<String>();
        listHTAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listHt);
        listViewHT.setAdapter(listHTAdapter);


        /* On click on the cs spinner */

        binding.spinnerCs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++check > 1){
                    String selecteditem =  adapterView.getItemAtPosition(i).toString();
                    addUE(selecteditem, "CS");
                    Log.d(TAG,"spinner clicked");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG,"spinner empty clicked");
            }
        });

        /* On click on the tm spinner */
        binding.spinnerTm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++checkTm > 1){
                    String selecteditem =  adapterView.getItemAtPosition(i).toString();
                    addUE(selecteditem, "TM");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        /* On click on the ec spinner */
        binding.spinnerEc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++checkEc > 1) {
                    String selecteditem = adapterView.getItemAtPosition(i).toString();
                    addUE(selecteditem, "EC");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        /* On click on the me spinner */
        binding.spinnerMe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++checkMe > 1) {
                    String selecteditem = adapterView.getItemAtPosition(i).toString();
                    addUE(selecteditem, "ME");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        /* On click on the ht spinner */
        binding.spinnerHt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++checkHt > 1) {
                    String selecteditem = adapterView.getItemAtPosition(i).toString();
                    addUE(selecteditem, "HT");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        /* On check the switch npml */
        switchNpml = binding.ncaSwNpml;
        switchNpml.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.getCursus().setNpml(b);
                Log.d(TAG, String.valueOf(b));

            }
        });

        setListEventHandling();
    }




    public void setListEventHandling(){
        setLongClickListenerList(listViewCS, listCs, listCSAdapter);
        setLongClickListenerList(listViewTM, listTm, listTMAdapter);
        setLongClickListenerList(listViewME, listMe, listMEAdapter);
        setLongClickListenerList(listViewHT, listHt, listHTAdapter);
        setLongClickListenerList(listViewEC, listEc, listECAdapter);
    }


    public void setLongClickListenerList(ListView listView, List<String> list, ArrayAdapter<String> adapter){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(NewCursusActivity.this, list.get(i), Toast.LENGTH_SHORT).show();
                adapter.remove(list.get(i));
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }


    @Override
    public void inflateNewCursus(){
        if (binding.etCursusIdentifier.getText().toString().matches("")) {
            Toast.makeText(this, "You did not enter an identifier", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent replyIntent = new Intent();
            Cursus cursus = binding.getCursus();
            replyIntent.putExtra(EXTRA_REPLY_CURSUS, cursus);

            int brancheID = binding.getCursus().getBrancheID();
            String branche = getResources().getStringArray(R.array.branches_array)[brancheID];
            replyIntent.putExtra(EXTRA_REPLY_BRANCHE, branche);

            setResult(RESULT_OK, replyIntent);
            finish();
        }

    }

    public void inflateNewUE(String category){

        DialogNewUE dialogNewUE = new DialogNewUE();
        dialogNewUE.setCategory(category);
        String branche = getResources().getStringArray(R.array.branches_array)[binding.getCursus().getBrancheID()];
        dialogNewUE.setBranche(branche);
        dialogNewUE.show(getSupportFragmentManager(), "new ue");



    }

    /**
     * Fonction adding an UE to the corresponding list and then binding the list to the listView.
     * @param sigle the sigle of the UE to add
     * @param category the category of the UE: CS, TM, EC, ME or HT
     */
    public void addUE(String sigle, String category){
        if (category == "CS" && ! listCs.contains(sigle)){
            listCs.add(sigle);
            binding.getCursus().setListCs(listCs);
            listCSAdapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(listViewCS);
        }else if (category == "TM"&& ! listTm.contains(sigle)){
            listTm.add(sigle);
            binding.getCursus().setListTm(listTm);
            listTMAdapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(listViewTM);
        }else if (category == "EC" && ! listEc.contains(sigle)){
            listEc.add(sigle);
            binding.getCursus().setListEc(listEc);
            listECAdapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(listViewEC);
        }else if (category == "ME" && ! listMe.contains(sigle)){
            listMe.add(sigle);
            binding.getCursus().setListMe(listMe);
            listMEAdapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(listViewME);
        }else if (category == "HT" && ! listHt.contains(sigle)){
            listHt.add(sigle);
            binding.getCursus().setListHt(listHt);
            listHTAdapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(listViewTM);
        }
    }


    public void onChechboxClicked(String idCheckbox){
        if (idCheckbox == "nca_cb_st09"){
            binding.getCursus().setSt09(binding.ncaCbSt09.isChecked());
        }else if(idCheckbox == "nca_cb_st10"){
            binding.getCursus().setSt10(binding.ncaCbSt10.isChecked());
        }
    }

}