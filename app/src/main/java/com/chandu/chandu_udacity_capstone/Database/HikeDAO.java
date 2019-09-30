package com.chandu.chandu_udacity_capstone.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HikeDAO {
    @Query("SELECT * FROM hikedetails ORDER by hikeID")
    LiveData<List<HikeEntry>> loadAllHikesById();

    @Query("SELECT * FROM hikedetails ORDER by hikeLength")
    LiveData<List<HikeEntry>> loadAllHikesByLength();

    @Query("SELECT * FROM hikedetails ORDER by hikeDifficulty")
    LiveData<List<HikeEntry>> loadAllHikesByDiff();

    @Insert
    void insertHike(HikeEntry hikeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateHike(HikeEntry hikeEntry);

    @Delete
    void deleteHike(HikeEntry hikeEntry);

    @Query("SELECT * FROM hikedetails where hikeID = :id")
    HikeEntry loadHikeById(String id);

    @Query("SELECT * FROM hikedetails where hikeID = :id")
    LiveData<HikeEntry> loadFavHikeId(String id);

}
