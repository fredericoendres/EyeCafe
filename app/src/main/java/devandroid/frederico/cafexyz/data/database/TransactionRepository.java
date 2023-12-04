package devandroid.frederico.cafexyz.data.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

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

    public void insertTransaction(double totalValue, String paymentType) {
        TransactionEntity roomData = new TransactionEntity();
        roomData.setTransactionTime(System.currentTimeMillis());
        roomData.setTransactionValue(totalValue);
        roomData.setPaymentType(paymentType);

        AppDB.databaseWriteExecutor.execute(() -> {
            roomDao.insert(roomData);
        });
    }

}
