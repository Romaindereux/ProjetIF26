package fr.utt.if26.romain_dereux.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;

/**
 * Created by Romain on 15 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */
@Dao
public interface UEDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UE ue);

    @Query("DELETE FROM ue_table")
    void deleteAll();

    @Query("SELECT * FROM ue_table ORDER BY sigle ASC")
    LiveData<List<UE>> getAlphabetizedUE();

    //TODO Create delete by sigle
    //TODO Create Select by branche
    //TODO Create Select by sigle
    //TODO Create Select by category
}
