package fr.utt.if26.romain_dereux.model;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Romain on 15 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

@Entity(tableName = "ue_table")
public class UE extends BaseObservable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name= "sigle")
    private String sigle;

    @ColumnInfo(name = "branche")
    private String branche;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "credit")
    private int credit;

    public UE(String sigle, String branche, String category, int credit) {
        this.sigle = sigle;
        this.branche = branche;
        this.category = category;
        this.credit = credit;
    }

    @NonNull
    @Bindable
    public String getSigle() {
        return sigle;
    }

    public void setSigle(@NonNull String sigle) {
        this.sigle = sigle;
    }

    @Bindable
    public String getBranche() {
        return branche;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Bindable
    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return  sigle + " - " + branche + " - " + category + " - " + credit ;
    }
}
