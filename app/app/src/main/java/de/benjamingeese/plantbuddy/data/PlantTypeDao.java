package de.benjamingeese.plantbuddy.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PlantTypeDao {
    @Query("SELECT * FROM PlantType")
    List<PlantType> getAll();

    @Query("SELECT * FROM PlantType")
    LiveData<List<PlantType>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PlantType> types);
}
