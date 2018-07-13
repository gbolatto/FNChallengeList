package com.example.android.fnchallengelist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by gbolatto on 6/1/2018.
 */
@Database(entities = {Season.class, Week.class, Challenge.class}, version = 1, exportSchema = false)
public abstract class WeekRoomDatabase extends RoomDatabase{

    public abstract  ChallengeDao challengeDao();
    public abstract WeekDao weekDao();
    public abstract SeasonDao seasonDao();

    private static WeekRoomDatabase INSTANCE;

    public static WeekRoomDatabase getWeekDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                            WeekRoomDatabase.class,
                                            "week_database")
                                            .fallbackToDestructiveMigration()
                                            // used in testing
                                            // .allowMainThreadQueries()
                                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}