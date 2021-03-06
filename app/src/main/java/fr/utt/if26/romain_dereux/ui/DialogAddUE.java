package fr.utt.if26.romain_dereux.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import fr.utt.if26.romain_dereux.MyApp;
import fr.utt.if26.romain_dereux.R;
import fr.utt.if26.romain_dereux.databinding.FragmentDialogAddUeBinding;
import fr.utt.if26.romain_dereux.db.Converters;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;

/**
 * This fragment is used by the ViewCursusActivity when the user click on the + of the action bar
 * in order to add a new ue to the cursus
 */
public class DialogAddUE extends DialogFragment {

    public static final String TAG = "DialogAddUE";
    private FragmentDialogAddUeBinding binding;
    private String branche, identifier;
    private UEViewModel ueViewModel;
    private CursusViewModel cursusViewModel;

    private int check = 0; //This variable is used  by the spinner to prevent the spinner at it initialization

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogAddUeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.setDialogAddUe(this);
        ueViewModel = new ViewModelProvider(this).get(UEViewModel.class);
        cursusViewModel = new ViewModelProvider(this).get(CursusViewModel.class);

        List<String> listString = getAllUeFromCursus(identifier);
        populateSpinner(listString);

        // Force caps for category
        binding.dNueEtCategory.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        return view;
    }

    /**
     * Return a list with all the sigle of ue from a cursus given the identifier of this cursus
     * @param identifier
     * @return
     */
    public List<String> getAllUeFromCursus(String identifier){
        Converters converters = new Converters();
        List<String> allUe = new ArrayList<String>(converters.gettingListFromString(cursusViewModel.getListCs(identifier).get(0)));
        allUe.addAll(converters.gettingListFromString(cursusViewModel.getListTm(identifier).get(0)));
        allUe.addAll(converters.gettingListFromString(cursusViewModel.getListEc(identifier).get(0)));
        allUe.addAll(converters.gettingListFromString(cursusViewModel.getListHt(identifier).get(0)));
        allUe.addAll(converters.gettingListFromString(cursusViewModel.getListMe(identifier).get(0)));

        Log.d(TAG, allUe.toString());
        Log.d(TAG, String.valueOf(allUe.size()));
        return allUe;
    }

    /**
     * This method populate the spinner with the UEs available depending of the branche.
     * It also add an event listener when the user click on one of the item
     */
    public void populateSpinner(List<String> list){
       ArrayList<String> listUE = new ArrayList<>();
       ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listUE);
       spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       binding.spinner.setAdapter(spinnerAdapter);
       getUEAvalaibleForBranche(branche).observe(this, new Observer<List<UE>>() {
           @Override
           public void onChanged(List<UE> ues) {
               for (UE ue : ues){
                    if(! list.contains(ue.getSigle())){
                        listUE.add(ue.toString());
                        spinnerAdapter.notifyDataSetChanged();
                    }
               }
           }
       });
       addEventListner();
    }

    /**
     * Add an event listener to the spinner. When the user click on it, it add an UE to the cursus.
     */
    public void addEventListner(){
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++check > 1){
                    String selecteditem =  adapterView.getItemAtPosition(i).toString();
                    UE ue = parseUEString(selecteditem);
                    addUeToDB(ue);
                    ViewCursusActivity viewCursusActivity = (ViewCursusActivity) getActivity();
                    viewCursusActivity.refreshData(ue);
                    getDialog().dismiss();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG,"spinner empty clicked");
            }
        });
    }

    /**
     * Add the UE to the database
     * @param ue UE: ue to add
     */
    public void addUeToDB(UE ue){
        ArrayList<String> listUE;
        Converters converters = new Converters();
        String listString;
        switch (ue.getCategory()){
            case "CS":
                listUE = (ArrayList<String>) cursusViewModel.getListCs(identifier);
                listUE.add(ue.getSigle());
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListCs(listString, identifier);
                break;
            case "TM":
                listUE = (ArrayList<String>) cursusViewModel.getListTm(identifier);
                listUE.add(ue.getSigle());
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListTm(listString, identifier);
                break;
            case "EC":
                listUE = (ArrayList<String>) cursusViewModel.getListEc(identifier);
                listUE.add(ue.getSigle());
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListEc(listString, identifier);
                break;
            case "ME":
                listUE = (ArrayList<String>) cursusViewModel.getListMe(identifier);
                listUE.add(ue.getSigle());
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListMe(listString, identifier);
                break;
            case "HT":
                listUE = (ArrayList<String>) cursusViewModel.getListHt(identifier);
                listUE.add(ue.getSigle());
                listString = converters.writingStringFromList(listUE);
                cursusViewModel.updateListHt(listString, identifier);
                break;
        }
        Cursus cursus = cursusViewModel.getCursusById(identifier).get(0);
        cursus.setValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180);
        cursusViewModel.updateValid(cursus.isNpml() && cursus.isSt09() && cursus.isSt10() && getSumCreditCursus(cursus) > 180, cursus.getIdentifier());

    }
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
     * Parse a string into an UE
     * @param string String of this form : sigle - branche - category - credit
     * @return UE parsed from the input
     */
    public UE parseUEString(String string){
        String[] strings = string.split(" - ");
        UE ue = new UE(strings[0], strings[1], strings[2], Integer.parseInt(strings[3]));
        return ue;
    }

    /**
     * Set the parameter branche in the dialog class in order to use to retrieve the UE
     * @param branche String: the branche of the cursus
     */
    public void setBranche(String branche){
        this.branche = branche;
    }

    /**
     * Set the parameter identifier in the dialog class in order to use it to retrieve list of UEs
     * @param identifier String: the identifier of the cursus
     */
    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }

    /**
     * get the UE available for the cursus depending of the branche of the cursus.
     * @param branche String: the branche of the cursus
     * @return list of all UEs available
     */
    public LiveData<List<UE>> getUEAvalaibleForBranche(String branche){
        return ueViewModel.getUEAvalaibleForBranche(branche);
    }

    public void cancelClick(){
        getDialog().dismiss();
    }

    public void createUE(){
        UE ue = new UE(binding.getSigle(), branche, binding.getCategory(), Integer.parseInt(binding.getCredit()));
        ueViewModel.insert(ue);
        addUeToDB(ue);
        ViewCursusActivity viewCursusActivity = (ViewCursusActivity) getActivity();
        viewCursusActivity.refreshData(ue);
        getDialog().dismiss();
    }


}