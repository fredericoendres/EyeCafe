package devandroid.frederico.cafexyz.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "transaction_db")
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "transactionTime")
    private Long transactionTime;

    @ColumnInfo(name = "transactionValue")
    private Double transactionValue;

    @ColumnInfo(name = "paymentType")
    private String paymentType;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

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
