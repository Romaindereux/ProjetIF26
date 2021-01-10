package fr.utt.if26.romain_dereux.db.dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;

@Dao
public interface CursusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cursus cursus);

    @Query("DELETE FROM cursus_table")
    void deleteAll();

    @Query("DELETE FROM cursus_table WHERE identifier = :identifier")
    void deleteFromIdentifier(String identifier);

    @Query("SELECT * FROM cursus_table ORDER BY identifier ASC")
    LiveData<List<Cursus>> getAlphabetizedCursus();

    @Query("SELECT listCs FROM cursus_table WHERE identifier = :identifier")
    List<String> getListCs(String identifier);

    @Query("UPDATE cursus_table SET listCs = :listCs WHERE identifier = :identifier")
    void updateListCs(String listCs, String identifier);

    @Query("SELECT listTm FROM cursus_table WHERE identifier = :identifier")
    List<String> getListTm(String identifier);

    @Query("UPDATE cursus_table SET listTm = :listTm WHERE identifier = :identifier")
    void updateListTm(String listTm, String identifier);

    @Query("SELECT listEc FROM cursus_table WHERE identifier = :identifier")
    List<String> getListEc(String identifier);

    @Query("UPDATE cursus_table SET listEc = :listEc WHERE identifier = :identifier")
    void updateListEc(String listEc, String identifier);

    @Query("SELECT listMe FROM cursus_table WHERE identifier = :identifier")
    List<String> getListMe(String identifier);

    @Query("UPDATE cursus_table SET listMe = :listMe WHERE identifier = :identifier")
    void updateListMe(String listMe, String identifier);

    @Query("SELECT listHt FROM cursus_table WHERE identifier = :identifier")
    List<String> getListHt(String identifier);

    @Query("UPDATE cursus_table SET listHt = :listHt WHERE identifier = :identifier")
    void updateListHt(String listHt, String identifier);


}
