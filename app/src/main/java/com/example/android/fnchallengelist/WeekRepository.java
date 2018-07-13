package com.example.android.fnchallengelist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gbolatto on 6/1/2018.
 */
public class WeekRepository {

    private APIService mAPIService;

    private ChallengeDao mChallengeDao;
    private WeekDao mWeekDao;
    private SeasonDao mSeasonDao;
    private LiveData<List<Week>> mWeeks;
    private LiveData<List<Challenge>> mChallenges;
    private LiveData<List<Season>> mAllSeasons;
    private LiveData<String> mLatestSeasonName;


    WeekRepository(Application application) {
        WeekRoomDatabase db = WeekRoomDatabase.getWeekDatabase(application);
        mChallengeDao = db.challengeDao();
        mWeekDao = db.weekDao();
        mSeasonDao = db.seasonDao();
        mWeeks = mWeekDao.getAllWeeks();
        mChallenges = mChallengeDao.getAllChallenges();
        mAllSeasons = mSeasonDao.getAllSeasons();
        mLatestSeasonName = mSeasonDao.getLatestSeasonName();
        mAPIService = APIUtils.getAPIService();
    }

    public LiveData<Season> getSeason() {

        // TODO: have this API call get saved to database and
        // then have this method return the database call instead with a refresh of database
        // data from api if available
        // then do the same for all the other repository methods
        // update database by searching by week and adding new weeks if needed
        // update database by searching by challenge and if challengeString is changed,
        // update the challengeString but keep the completion

        updateDatabaseFromApi();

        return mSeasonDao.getLatestSeason();
    }

    public LiveData<String> getLatestSeasonName() {
        return mLatestSeasonName;
    }

    /*
     * TODO: use for updating database with new season
     * change so only checks for new season to add
     * update database with data from API about the new season
     */
    public void updateDatabaseFromApi() {

        // TODO: getSeasonFromAPI() changing refer to APIService.java/getSeasonFromAPI() about changes
        // change season class to just season id and seasonName when the API changes
        // Change week class to just week id and weekName in same way
        // fix so if a challengeString is updated on the API, the local database gets updated
        // while maintaining completion status
        mAPIService.getSeasonFromAPI().enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                if (response.isSuccessful()){
                    Season season = response.body();
                    insertSeason(season);
                    List<Week> weeks = season.getWeeks();
                    for (int n = 0; n < weeks.size(); n++) {
                        insertWeek(weeks.get(n));
                        for (int i = 0; i < weeks.get(n).getChallenges().size(); i++) {
                            Challenge challenge = weeks.get(n).getChallenges().get(i);
                            // set a default of false completion since this is not in the api
                            challenge.setCompletion(false);
                            insertChallenge(challenge);
                        }
                    }
                } else {

                    // TODO: do something better with the status codes
                    int statusCode = response.code();
                    Log.e("updateDatabaseFromApi", "Could not access API " + statusCode);

                }
            }

            @Override
            public void onFailure(Call<Season> call, Throwable t) {

                // TODO: need better error handling
                Log.e("updateDatabaseFromApi","Could not access API " + t);
            }
        });
    }

    LiveData<List<Week>> getAllWeeks() {
        return mWeeks;
    }

    LiveData<List<Season>> getAllSeasons() {
        return mAllSeasons;
    }

    LiveData<List<Challenge>> getAllChallenges() {
        return mChallenges;
    }

    public void insertSeason(Season season) {
        new insertSeasonAsyncTask(mSeasonDao).execute(season);
    }

    private static class insertSeasonAsyncTask extends AsyncTask<Season, Void, Void> {
        private SeasonDao mAsyncTaskDao;

        insertSeasonAsyncTask(SeasonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Season... seasons) {
            mAsyncTaskDao.insertSeason(seasons[0]);
            return null;
        }
    }

    public void deleteAllSeasons() {
        new deleteAllSeasonsAsyncTask(mSeasonDao).execute();
    }

    private static class deleteAllSeasonsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SeasonDao mAsyncTaskDao;

        deleteAllSeasonsAsyncTask(SeasonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllSeasons();
            return null;
        }
    }

    public void insertWeek(Week week) {
        new insertWeekAsyncTask(mWeekDao).execute(week);
    }

    private static class insertWeekAsyncTask extends AsyncTask<Week, Void, Void> {
        private WeekDao mAsyncTaskDao;

        insertWeekAsyncTask(WeekDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Week... weeks) {
            mAsyncTaskDao.insertWeek(weeks[0]);
            return null;
        }
    }

    public void deleteAllWeeks() {
        new deleteAllWeeksAsyncTask(mWeekDao).execute();
    }

    private static class deleteAllWeeksAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeekDao mAsyncTaskDao;

        deleteAllWeeksAsyncTask(WeekDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllWeeks();
            return null;
        }
    }

    public void updateWeek(Week week) {
        new updateWeekAsyncTask(mWeekDao).execute(week);
    }

    private static class updateWeekAsyncTask extends AsyncTask<Week, Void, Void> {
        private WeekDao mAsyncTaskDao;

        updateWeekAsyncTask(WeekDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Week... weeks) {
            mAsyncTaskDao.updateWeek(weeks[0]);
            return null;
        }
    }

    public void updateChallenge(Challenge challenge) {
        new updateChallengeAsyncTask(mChallengeDao).execute(challenge);
    }

    private static class updateChallengeAsyncTask extends AsyncTask<Challenge, Void, Void> {
        private ChallengeDao mAsyncTaskDao;

        updateChallengeAsyncTask(ChallengeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Challenge... challenges) {
            mAsyncTaskDao.updateChallenge(challenges[0]);
            return null;
        }
    }

    public void insertChallenge(Challenge challenge) {
        new insertChallengeAsyncTask(mChallengeDao).execute(challenge);
    }

    private static class insertChallengeAsyncTask extends AsyncTask<Challenge, Void, Void> {
        private ChallengeDao mAsyncTaskDao;

        insertChallengeAsyncTask(ChallengeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Challenge... challenges) {
            mAsyncTaskDao.insertChallenge(challenges[0]);
            return null;
        }
    }
}