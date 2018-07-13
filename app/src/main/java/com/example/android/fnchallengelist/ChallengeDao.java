package com.example.android.fnchallengelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by gbolatto on 6/6/2018.
 */
@Dao
public interface ChallengeDao {

    @Insert(onConflict = IGNORE)
    void insertChallenge(Challenge challenge);

    @Update
    void updateChallenge(Challenge challenge);

    @Query("SELECT * FROM challenge_table")
    LiveData<List<Challenge>> getAllChallenges();

    @Query("SELECT * FROM challenge_table WHERE weekId = :weekId")
    LiveData<List<Challenge>> getChallengesByWeekId(int weekId);
}
