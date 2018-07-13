package com.example.android.fnchallengelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by gbolatto on 6/1/2018.
 */
@Dao
public interface WeekDao {

    @Query("SELECT * FROM week_table")
    LiveData<List<Week>> getAllWeeks();

    @Insert(onConflict = IGNORE)
    void insertWeek(Week week);

    @Update
    void updateWeek(Week week);

    @Query("DELETE FROM week_table")
    void deleteAllWeeks();
}
