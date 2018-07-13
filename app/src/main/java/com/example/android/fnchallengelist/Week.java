package com.example.android.fnchallengelist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by gbolatto on 5/26/2018.
 */
@Entity(tableName = "week_table", foreignKeys = @ForeignKey(entity = Season.class,
                                                            parentColumns = "id",
                                                            childColumns = "seasonId",
                                                            onDelete = CASCADE))
public class Week {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int mId;

    @ColumnInfo(name = "idWithinSeason")
    @SerializedName("idWithinSeason")
    @Expose
    private int mIdWithinSeason;

    @ColumnInfo(name = "seasonId")
    @SerializedName("seasonId")
    @Expose
    private int mSeasonId;

    @ColumnInfo(name = "weekName")
    @SerializedName("weekName")
    @Expose
    private String mWeekName;

    @SerializedName("challenges")
    @Expose
    @Ignore
    private List<Challenge> mChallenges;

    public Week (int id, int idWithinSeason, int seasonId, String weekName) {
        mId = id;
        mIdWithinSeason = idWithinSeason;
        mSeasonId = seasonId;
        mWeekName = weekName;
    }

    public Week (int id, int idWithinSeason, int seasonId, String weekName, List<Challenge> challenges) {
        mId = id;
        mIdWithinSeason = idWithinSeason;
        mSeasonId = seasonId;
        mWeekName = weekName;
        mChallenges = challenges;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getIdWithinSeason() {
        return mIdWithinSeason;
    }

    public void setIdWithinSeason(int idWithinSeason) {
        mIdWithinSeason = idWithinSeason;
    }

    public String getWeekName() {
        return mWeekName;
    }

    public void setWeekName(String weekName) {
        mWeekName = weekName;
    }

    public int getSeasonId() {
        return mSeasonId;
    }

    public void setSeasonId(int seasonId) {
        mSeasonId = seasonId;
    }

    public List<Challenge> getChallenges() {
        return mChallenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        mChallenges = challenges;
    }
}