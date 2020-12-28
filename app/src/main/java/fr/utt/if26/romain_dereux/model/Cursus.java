package fr.utt.if26.romain_dereux.model;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "cursus_table")
public class Cursus extends BaseObservable {

    private int brancheID;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "identifier")
    private String mIdentifier;

    @NonNull
    @ColumnInfo(name = "branche")
    private String mBranche;

    @ColumnInfo(name = "listCs")
    private ArrayList<String> mListCs;

    public Cursus(@NonNull String identifier, @NonNull String branche, ArrayList<String> listCs){
        this.mIdentifier = identifier;
        this.mBranche = branche;
        this.mListCs = listCs;
    }
    public String getIdentifier(){
        return this.mIdentifier;
    }

    public void setIdentifier(@NonNull String mIdentifier) {
        this.mIdentifier = mIdentifier;
    }


    @NonNull
    public String getBranche() {
        return this.mBranche;
    }
    public void setBranche(@NonNull String branche) {
        this.mBranche = branche;
        notifyPropertyChanged(BR.cursus);
    }

    @Bindable
    public int getBrancheID() {
        return brancheID;
    }

    public void setBrancheID(int brancheID) {
        this.brancheID = brancheID;
        notifyPropertyChanged(BR.brancheID);
    }

    public ArrayList<String> getListCs() {
        return mListCs;
    }

    public void setListCs(ArrayList<String> mListCs) {
        this.mListCs = mListCs;
    }

}
