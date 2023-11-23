package devandroid.frederico.cafexyz.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface TransactionDao {

    @Insert(onConflict = IGNORE)
    void insert(TransactionEntity roomData);

    @Delete
    void delete(TransactionEntity roomData);

    @Delete
    void reset(List<TransactionEntity> roomData);

    @Query("SELECT * FROM transaction_db")
    LiveData<List<TransactionEntity>> getAll();

}
