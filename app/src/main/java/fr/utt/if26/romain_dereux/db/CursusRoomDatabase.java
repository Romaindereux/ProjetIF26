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
import fr.utt.if26.romain_dereux.model.Cursus;

@Database(entities = {Cursus.class}, version = 1, exportSchema = false)
public abstract class CursusRoomDatabase extends RoomDatabase {

    public abstract CursusDao cursusDao();

    private static volatile CursusRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CursusRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (CursusRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CursusRoomDatabase.class, "cursus_database").addCallback(sRoomDatabaseCallback).build();
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
                CursusDao dao = INSTANCE.cursusDao();
                dao.deleteAll();

                Cursus cursus = new Cursus("cursus1");
                dao.insert(cursus);
                cursus = new Cursus("cursus2");
                dao.insert(cursus);
            });
        }
    };

}