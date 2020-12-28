package fr.utt.if26.romain_dereux.db;

import java.util.ArrayList;

import androidx.room.TypeConverter;

/**
 * Created by Romain on 28 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

public class Converters {
    @TypeConverter
    public ArrayList<String> gettingListFromString(String genreIds) {
        ArrayList<String> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(s);
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(ArrayList<String> list) {
        String genreIds = "";
        for (String i : list) {
            genreIds += "," + i ;
        }
        return genreIds;
    }
}
