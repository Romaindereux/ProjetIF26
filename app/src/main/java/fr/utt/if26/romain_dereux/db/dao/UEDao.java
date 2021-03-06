package fr.utt.if26.romain_dereux.db.dao;

import java.util.ArrayList;
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

    @Query("SELECT * FROM ue_table WHERE branche = :branche AND category = :category")
    LiveData<List<UE>> getUEByBrancheAndCategory(String branche, String category);

    @Query("SELECT * FROM ue_table WHERE category = :category")
    LiveData<List<UE>> getUEByCategory(String category);

    @Query("SELECT * FROM ue_table WHERE branche = :branche")
    LiveData<List<UE>> getUEByBranche(String branche);

    @Query("SELECT * FROM ue_table WHERE (branche = :branche) OR (category = 'EC' OR category = 'ME' OR category = 'HT')")
    LiveData<List<UE>> getUEAvalaibleForBranche(String branche);

    @Query("SELECT * FROM ue_table WHERE sigle = :sigle")
    List<UE> getUEBySigle(String sigle);

    //TODO Create delete by sigle
    //TODO Create Select by sigle
    //TODO Create Select by category
}
