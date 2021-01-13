package fr.utt.if26.romain_dereux;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import fr.utt.if26.romain_dereux.db.CursusRoomDatabase;
import fr.utt.if26.romain_dereux.db.dao.CursusDao;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;

public class CursusRepository {

    private CursusDao cursusDao;
    private LiveData<List<Cursus>> mAllCursus;

    public CursusRepository(Application application){
        CursusRoomDatabase db = CursusRoomDatabase.getDatabase(application);
        cursusDao = db.cursusDao();
        mAllCursus= cursusDao.getAlphabetizedCursus();
    }

    public LiveData<List<Cursus>> getAllCursus(){
        return mAllCursus;
    }

    public void deleteFromIdentifier(String identifier){
        cursusDao.deleteFromIdentifier(identifier);
    }
    public void insert(Cursus cursus){
        CursusRoomDatabase.databaseWriteExecutor.execute(() -> {
            cursusDao.insert(cursus);
        });
    }

    public List<String> getListCs(String identifier){
        return cursusDao.getListCs(identifier);
    }

    public void updateListCs(String listUE, String id){
        cursusDao.updateListCs(listUE, id);
    }

    public List<String> getListTm(String identifier){
        return cursusDao.getListTm(identifier);
    }

    public void updateListTm(String listUE, String id){
        cursusDao.updateListTm(listUE, id);
    }

    public List<String> getListEc(String identifier){
        return cursusDao.getListEc(identifier);
    }

    public void updateListEc(String listUE, String id){
        cursusDao.updateListEc(listUE, id);
    }

    public List<String> getListMe(String identifier){
        return cursusDao.getListMe(identifier);
    }

    public void updateListMe(String listUE, String id){
        cursusDao.updateListMe(listUE, id);
    }

    public List<String> getListHt(String identifier){
        return cursusDao.getListHt(identifier);
    }

    public void updateListHt(String listUE, String id){
        cursusDao.updateListHt(listUE, id);
    }

    public void updateNpml(Boolean npml, String id){
        cursusDao.updateNpml(npml, id);
    }

    public void updateSt09(Boolean st09, String id){
        cursusDao.updateSt09(st09, id);
    }

    public void updateSt10(Boolean st10, String id){
        cursusDao.updateSt10(st10, id);
    }

    public void updateValid(Boolean valid, String id){
        cursusDao.updateValid(valid, id);
    }

    public List<Cursus> getCursusById(String id){
        return cursusDao.getCursusById(id);
    }

}
