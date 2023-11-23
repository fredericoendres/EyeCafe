package devandroid.frederico.cafexyz.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import devandroid.frederico.cafexyz.data.database.TransactionDao;
import devandroid.frederico.cafexyz.data.database.TransactionEntity;

public class TransactionRepository {

    private TransactionDao roomDao;
    private LiveData<List<TransactionEntity>> getAll;


    public TransactionRepository(Context context) {
        AppDB db = AppDB.getInstance(context);
        roomDao = db.roomDao();
        getAll = roomDao.getAll();
    }


    LiveData<List<TransactionEntity>> getAll() {
        return getAll;
    }

    void insert(TransactionEntity transactionEntity) {
        AppDB.databaseWriteExecutor.execute(() -> {
            roomDao.insert(transactionEntity);
        });
    }

    public void insertTransaction(double totalValue) {
        TransactionEntity roomData = new TransactionEntity();
        roomData.setTransactionTime(System.currentTimeMillis());
        roomData.setTransactionValue(totalValue);
        roomDao.insert(roomData);
    }

}
