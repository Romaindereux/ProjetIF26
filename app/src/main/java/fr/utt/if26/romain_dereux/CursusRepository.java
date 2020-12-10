package fr.utt.if26.romain_dereux;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import fr.utt.if26.romain_dereux.db.CursusRoomDatabase;
import fr.utt.if26.romain_dereux.db.dao.CursusDao;
import fr.utt.if26.romain_dereux.model.Cursus;

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
    public void insert(Cursus cursus){
        CursusRoomDatabase.databaseWriteExecutor.execute(() -> {
            cursusDao.insert(cursus);
        });
    }
}
