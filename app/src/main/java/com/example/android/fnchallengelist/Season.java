package com.example.android.fnchallengelist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbolatto on 6/6/2018.
 */
@Entity(tableName = "season_table")
public class Season {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int mId;

    @ColumnInfo(name = "seasonName")
    @SerializedName("seasonName")
    @Expose
    private String mSeasonName;

    @SerializedName("weeks")
    @Expose
    @Ignore
    private List<Week> mWeeks;

    public Season(int id, String seasonName){
        mId = id;
        mSeasonName = seasonName;
    }

    public Season(int id, String seasonName, List<Week> weeks){
        mId = id;
        mSeasonName = seasonName;
        mWeeks = weeks;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getSeasonName() {
        return mSeasonName;
    }

    public void setSeasonName(String seasonName) {
        mSeasonName = seasonName;
    }

    public List<Week> getWeeks() {
        return mWeeks;
    }

    public void setWeeks(List<Week> weeks) {
        mWeeks = weeks;
    }
}
