package devandroid.frederico.cafexyz.data;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomData roomData);

    @Delete
    void delete(RoomData roomData);

    @Delete
    void reset(List<RoomData> roomData);

    @Query("UPDATE room_db SET productPrice = :sPrice WHERE ID = :sID")
    void update(int sID, String sPrice);

    @Query("SELECT * FROM room_db")
    List<RoomData> getAll();

}
