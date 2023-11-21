package devandroid.frederico.cafexyz.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

    private RoomRepository roomRepository;

    private final LiveData<List<RoomData>> getAll;

    public RoomViewModel (Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
        getAll = roomRepository.getAll();
    }

    LiveData<List<RoomData>> getAll() { return getAll; }

    public void insert(RoomData roomData) { roomRepository.insert(roomData); }
}


