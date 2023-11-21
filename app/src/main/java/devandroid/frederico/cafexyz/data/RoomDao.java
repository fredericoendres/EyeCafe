package devandroid.frederico.cafexyz.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface RoomDao {

    @Insert(onConflict = IGNORE)
    void insert(RoomData roomData);

    @Delete
    void delete(RoomData roomData);

    @Delete
    void reset(List<RoomData> roomData);

    @Query("SELECT * FROM room_db")
    LiveData<List<RoomData>> getAll();

}
