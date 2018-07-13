package com.example.android.fnchallengelist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by gbolatto on 6/6/2018.
 */
@Dao
public interface SeasonDao {

    @Query("SELECT * FROM season_table")
    LiveData<List<Season>> getAllSeasons();

    @Query("SELECT * FROM season_table ORDER BY id DESC LIMIT 1")
    LiveData<Season> getLatestSeason();

    @Query("SELECT seasonName FROM season_table WHERE id==(SELECT MAX(id) from season_table)")
    LiveData<String> getLatestSeasonName();

    @Insert(onConflict = IGNORE)
    void insertSeason(Season season);

    @Query("DELETE FROM season_table")
    void deleteAllSeasons();
}
