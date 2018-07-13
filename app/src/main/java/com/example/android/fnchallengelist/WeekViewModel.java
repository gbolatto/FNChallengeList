package com.example.android.fnchallengelist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbolatto on 6/1/2018.
 */
public class WeekViewModel extends AndroidViewModel {

    private WeekRepository mRepository;
    private LiveData<List<Week>> mWeeks;
    private LiveData<List<Challenge>> mChallenges;
    private LiveData<Season> mSeason;
    private LiveData<String> mLatestSeasonName;

    public WeekViewModel(Application application) {
        super(application);
        mRepository = new WeekRepository(application);
        mWeeks = mRepository.getAllWeeks();
        mChallenges = mRepository.getAllChallenges();
        mSeason = mRepository.getSeason();
        mLatestSeasonName = mRepository.getLatestSeasonName();
    }

    public LiveData<List<Week>> getAllWeeks() {
        return mWeeks;
    }

    public LiveData<List<Challenge>> getAllChallenges(){
        return mChallenges;
    }

    public void insertWeek(Week week) {
        mRepository.insertWeek(week);
    }

    public void updateWeek(Week week) {
        mRepository.updateWeek(week);
    }

    public void updateChallenge(Challenge challenge) {
        mRepository.updateChallenge(challenge);
    }

    public LiveData<Season> getSeason(){
        return mSeason;
    }

    public LiveData<String> getLatestSeasonName() {
        return mLatestSeasonName;
    }

    public void insertSeason(Season season) {
        mRepository.insertSeason(season);
    }
}