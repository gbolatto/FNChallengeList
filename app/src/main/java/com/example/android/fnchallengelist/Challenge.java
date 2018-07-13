package com.example.android.fnchallengelist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by gbolatto on 5/26/2018.
 */

@Entity(tableName = "challenge_table", foreignKeys = @ForeignKey(entity = Week.class,
                                                                parentColumns ="id",
                                                                childColumns = "weekId",
                                                                onDelete = CASCADE))
public class Challenge {

    // since all the challenges are in the same challenge table in the database
    // need a unique ID for each
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int mId;

    @ColumnInfo(name = "idWithinWeek")
    @SerializedName("idWithinWeek")
    @Expose
    private int mIdWithinWeek;

    @SerializedName("weekId")
    @Expose
    @ColumnInfo(name = "weekId")
    private int mWeekId;

    @SerializedName("seasonId")
    @Expose
    @ColumnInfo(name = "seasonId")
    private int mSeasonId;

    @SerializedName("challengeString")
    @Expose
    @ColumnInfo(name = "challengeString")
    private String mChallengeString;

    @SerializedName("completion")
    @Expose
    @ColumnInfo(name = "completion")
    private Boolean mCompletion;


    public Challenge(int id, int idWithinWeek, int weekId, int seasonId, String challengeString, Boolean completion) {
        mId = id;
        mIdWithinWeek = idWithinWeek;
        mWeekId = weekId;
        mSeasonId = seasonId;
        mChallengeString = challengeString;
        mCompletion = completion;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getIdWithinWeek() {
        return mIdWithinWeek;
    }

    public void setIdWithinWeek(int idWithinWeek) {
        mIdWithinWeek = idWithinWeek;
    }

    public int getWeekId() {
        return mWeekId;
    }

    public void setWeekId(int weekId) {
        mWeekId = weekId;
    }

    public int getSeasonId() {
        return mSeasonId;
    }

    public void setSeasonId(int seasonId) {
        mSeasonId = seasonId;
    }

    public String getChallengeString() {
        return mChallengeString;
    }

    public void setChallengeString(String challengeString) {
        mChallengeString = challengeString;
    }

    public Boolean getCompletion() {
        return mCompletion;
    }

    public void setCompletion(Boolean completion) {
        mCompletion = completion;
    }
}