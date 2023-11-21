package devandroid.frederico.cafexyz.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "room_db")
public class RoomData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "transactionTime")
    private Long transactionTime;

    @ColumnInfo(name = "transactionValue")
    private Double transactionValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Double getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(Double transactionValue) {
        this.transactionValue = transactionValue;
    }
}
