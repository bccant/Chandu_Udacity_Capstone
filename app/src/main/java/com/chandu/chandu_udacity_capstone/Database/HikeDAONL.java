package com.chandu.chandu_udacity_capstone.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HikeDAONL {
    @Query("SELECT * FROM hikedetails ORDER by hikeID")
    List<HikeEntry> loadAllHikesById();
}
