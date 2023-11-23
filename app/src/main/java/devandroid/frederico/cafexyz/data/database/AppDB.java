package devandroid.frederico.cafexyz.data.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TransactionEntity.class},version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    private static volatile AppDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDB getInstance(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDB.class, "room_database").build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract TransactionDao roomDao();

}
