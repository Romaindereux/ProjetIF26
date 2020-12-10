package fr.utt.if26.romain_dereux.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import fr.utt.if26.romain_dereux.model.Cursus;

@Dao
public interface CursusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cursus cursus);

    @Query("DELETE FROM cursus_table")
    void deleteAll();

    @Query("SELECT * FROM cursus_table ORDER BY identifier ASC")
    LiveData<List<Cursus>> getAlphabetizedCursus();
}
