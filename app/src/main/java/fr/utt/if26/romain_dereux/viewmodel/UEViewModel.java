package fr.utt.if26.romain_dereux.viewmodel;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import fr.utt.if26.romain_dereux.UERepository;
import fr.utt.if26.romain_dereux.model.UE;

/**
 * Created by Romain on 22 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

public class UEViewModel extends AndroidViewModel {

    private UERepository mRepository;

    private final LiveData<List<UE>> mAllUE;

    public UEViewModel (Application application) {
        super(application);
        mRepository = new UERepository(application);
        mAllUE = mRepository.getAllUE();
    }

    public LiveData<List<UE>> getAllUE() { return mAllUE; }

    public LiveData<List<UE>> getUEByBrancheAndCategory(String branche, String category){
        return mRepository.getUEByBrancheAndCategory(branche, category);
    }

    public LiveData<List<UE>> getUEByCategory(String category){
        return mRepository.getUEByCategory(category);
    }

    public LiveData<List<UE>> getUEByBranche(String branche){
        return mRepository.getUEByBranche(branche);
    }

    public LiveData<List<UE>> getUEAvalaibleForBranche(String branche){
        return mRepository.getUEByBranche(branche);
    }

    public ArrayList<UE> getUEBySigle(String sigle){
        return mRepository.getUEBySigle(sigle);
    }

    public void insert(UE ue) { mRepository.insert(ue); }
}
