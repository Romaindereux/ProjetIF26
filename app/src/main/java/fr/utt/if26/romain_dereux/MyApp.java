package fr.utt.if26.romain_dereux;

import android.app.Application;
import android.content.Context;

/**
 * Created by Romain on 15 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

public class MyApp extends Application {
    /** Context de l'application */
    private static Context context;

    /**
     * Lancement de l'application
     */
    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
    }
    public static Context getContext()
    {
        return MyApp.context;
    }
}
