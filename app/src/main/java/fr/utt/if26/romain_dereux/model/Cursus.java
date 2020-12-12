package fr.utt.if26.romain_dereux.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "cursus_table")
public class Cursus {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "identifier")
    private String mIdentifier;

    public Cursus(@NonNull String identifier){
        this.mIdentifier = identifier;
    }
    public String getIdentifier(){
        return this.mIdentifier;
    }

    public void setIdentifier(@NonNull String mIdentifier) {
        this.mIdentifier = mIdentifier;
    }
}
