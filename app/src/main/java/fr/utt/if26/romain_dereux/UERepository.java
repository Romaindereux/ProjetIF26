package fr.utt.if26.romain_dereux;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import fr.utt.if26.romain_dereux.db.CursusRoomDatabase;
import fr.utt.if26.romain_dereux.db.dao.UEDao;
import fr.utt.if26.romain_dereux.model.UE;

/**
 * Created by Romain on 22 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

public class UERepository {

    private UEDao ueDao;
    private LiveData<List<UE>> mAllUE;

    public UERepository(Application application){
        CursusRoomDatabase db = CursusRoomDatabase.getDatabase(application);
        ueDao = db.ueDao();
        mAllUE= ueDao.getAlphabetizedUE();
    }
    public LiveData<List<UE>> getAllUE(){
        return mAllUE;
    }

    public LiveData<List<UE>> getUEByBrancheAndCategory(String branche, String category){
        return ueDao.getUEByBrancheAndCategory(branche, category);
    }

    public LiveData<List<UE>> getUEByCategory(String category){
        return ueDao.getUEByCategory(category);
    }

    public void insert(UE ue){
        CursusRoomDatabase.databaseWriteExecutor.execute(() -> {
            ueDao.insert(ue);
        });
    }
    public ArrayList<UE> getUEBySigle(String sigle){
        return (ArrayList<UE>) ueDao.getUEBySigle(sigle);
    }
}
