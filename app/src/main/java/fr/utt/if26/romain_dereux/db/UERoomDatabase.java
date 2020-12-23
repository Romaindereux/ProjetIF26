package fr.utt.if26.romain_dereux.db;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import fr.utt.if26.romain_dereux.db.dao.CursusDao;
import fr.utt.if26.romain_dereux.db.dao.UEDao;
import fr.utt.if26.romain_dereux.model.Cursus;
import fr.utt.if26.romain_dereux.model.UE;


// BECAME USELESS AFTER THE 23rd


/**
 * Created by Romain on 15 décembre 2020
 * Contact me at romain.dereux@utt.fr
 */

@Database(entities = {UE.class}, version = 1, exportSchema = false)
public abstract class UERoomDatabase extends RoomDatabase {

    public abstract UEDao ueDao();
    private static volatile UERoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UERoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (UERoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UERoomDatabase.class, "cursus_database").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UEDao dao = INSTANCE.ueDao();
                dao.deleteAll();

                UE ue1 = new UE("IF26", "ISI", "TM", 6);
                dao.insert(ue1);
                UE ue2 = new UE("NF16", "ISI", "CS", 6);
                dao.insert(ue2);
            });
        }
    };
}
