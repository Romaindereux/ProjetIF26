package fr.utt.if26.romain_dereux.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import fr.utt.if26.romain_dereux.CursusRepository;
import fr.utt.if26.romain_dereux.model.Cursus;

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
}
