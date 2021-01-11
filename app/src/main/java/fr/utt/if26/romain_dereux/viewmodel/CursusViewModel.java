package fr.utt.if26.romain_dereux.viewmodel;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import fr.utt.if26.romain_dereux.CursusRepository;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;

public class CursusViewModel extends AndroidViewModel {

    private CursusRepository mRepository;

    private final LiveData<List<Cursus>> mAllCursus;

    public CursusViewModel (Application application) {
        super(application);
        mRepository = new CursusRepository(application);
        mAllCursus = mRepository.getAllCursus();
    }

    public LiveData<List<Cursus>> getAllCursus() { return mAllCursus; }

    public Cursus getCursus(int position){
        return mAllCursus.getValue().get(position);
    }

    public void insert(Cursus cursus) { mRepository.insert(cursus); }

    public void deleteFromIdentifier(String identifier){
        mRepository.deleteFromIdentifier(identifier);
    }

    public List<String> getListCs(String identifier){
        return mRepository.getListCs(identifier);
    }

    public void updateListCs(String listUE, String id){
        mRepository.updateListCs(listUE, id);
    }

    public List<String> getListTm(String identifier){
        return mRepository.getListTm(identifier);
    }

    public void updateListTm(String listUE, String id){
        mRepository.updateListTm(listUE, id);
    }

    public List<String> getListEc(String identifier){
        return mRepository.getListEc(identifier);
    }

    public void updateListEc(String listUE, String id){
        mRepository.updateListEc(listUE, id);
    }

    public List<String> getListMe(String identifier){
        return mRepository.getListMe(identifier);
    }

    public void updateListMe(String listUE, String id){
        mRepository.updateListMe(listUE, id);
    }

    public List<String> getListHt(String identifier){
        return mRepository.getListHt(identifier);
    }

    public void updateListHt(String listUE, String id){
        mRepository.updateListHt(listUE, id);
    }

    public void updateNpml(Boolean b, String id){mRepository.updateNpml(b, id);}

    public void updateSt09(Boolean b, String id){mRepository.updateSt09(b, id);}

    public void updateSt10(Boolean b, String id){mRepository.updateSt10(b, id);}
}
