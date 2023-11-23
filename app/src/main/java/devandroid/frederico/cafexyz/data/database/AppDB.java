package devandroid.frederico.cafexyz.data;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import devandroid.frederico.cafexyz.data.database.TransactionDao;
import devandroid.frederico.cafexyz.data.database.TransactionEntity;

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
