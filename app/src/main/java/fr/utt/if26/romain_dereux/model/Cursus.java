package fr.utt.if26.romain_dereux.model;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import fr.utt.if26.romain_dereux.viewmodel.CursusViewModel;
import fr.utt.if26.romain_dereux.viewmodel.UEViewModel;


@Entity(tableName = "cursus_table")
public class Cursus extends BaseObservable implements Serializable {

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

    @ColumnInfo(name = "listTm")
    private ArrayList<String> mListTm;

    @ColumnInfo(name = "listEc")
    private ArrayList<String> mListEc;

    @ColumnInfo(name = "listMe")
    private ArrayList<String> mListMe;

    @ColumnInfo(name = "listHt")
    private ArrayList<String> mListHt;

    @ColumnInfo(name="st09")
    private boolean mSt09 = false;

    @ColumnInfo(name="st10")
    private boolean mSt10 = false;

    @ColumnInfo(name="npml")
    private boolean mNpml = false;

    @ColumnInfo(name = "valid")
    private boolean mValid = false;



    public Cursus(@NonNull String identifier, @NonNull String branche, ArrayList<String> listCs, ArrayList<String> listTm, ArrayList<String> listEc, ArrayList<String> listMe, ArrayList<String> listHt, boolean st09, boolean st10, boolean npml){
        this.mIdentifier = identifier;
        this.mBranche = branche;
        this.mListCs = listCs;
        this.mListTm = listTm;
        this.mNpml = npml;
        this.mListEc = listEc;
        this.mListMe = listMe;
        this.mListHt = listHt;
        this.mSt09 = st09;
        this.mSt10 = st10;
    }

    public boolean isNpml() {
        return mNpml;
    }

    public void setNpml(boolean npml) {
        this.mNpml = npml;
    }

    public boolean isSt09() {
        return mSt09;
    }

    public void setSt09(boolean st09) {
        this.mSt09 = st09;
    }

    public boolean isSt10() {
        return mSt10;
    }

    public void setSt10(boolean st10) {
        this.mSt10 = st10;
    }

    public boolean isValid() {
        return mValid;
    }

    public void setValid(boolean mValid) {
        this.mValid = mValid;
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

    public ArrayList<String> getListTm() {
        return mListTm;
    }

    public void setListTm(ArrayList<String> mListTm) {
        this.mListTm = mListTm;
    }

    public ArrayList<String> getListEc() {
        return mListEc;
    }

    public void setListEc(ArrayList<String> mListEc) {
        this.mListEc = mListEc;
    }

    public ArrayList<String> getListMe() {
        return mListMe;
    }

    public void setListMe(ArrayList<String> mListMe) {
        this.mListMe = mListMe;
    }

    public ArrayList<String> getListHt() {
        return mListHt;
    }

    public void setListHt(ArrayList<String> mListHt) {
        this.mListHt = mListHt;
    }


}
