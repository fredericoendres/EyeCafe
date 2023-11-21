package devandroid.frederico.cafexyz.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RoomRepository {

    private RoomDao roomDao;
    private LiveData<List<RoomData>> getAll;


    public RoomRepository(Application application) {
        RoomDB db = RoomDB.getInstance(application);
        roomDao = db.roomDao();
        getAll = roomDao.getAll();
    }


    LiveData<List<RoomData>> getAll() {
        return getAll;
    }

    void insert(RoomData roomData) {
        RoomDB.databaseWriteExecutor.execute(() -> {
            roomDao.insert(roomData);
        });
    }

    public void insertTransaction(double totalValue) {
        RoomData roomData = new RoomData();
        roomData.setTransactionTime(System.currentTimeMillis());
        roomData.setTransactionValue(totalValue);

        RoomDB.databaseWriteExecutor.execute(() -> {
            roomDao.insert(roomData);
        });
    }

}
