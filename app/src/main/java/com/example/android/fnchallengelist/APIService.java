package com.example.android.fnchallengelist;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by gbolatto on 6/6/2018.
 */
interface APIService {

    /*
     * TODO: in future have this use path and only get the max ID from the
     *  getAllSeasonsFromAPI() call below
     *  for now just use the current 4 on server
     */
    @GET("/seasons/4")
    Call<Season> getSeasonFromAPI();


    /*
     * TODO: in future have this return just a basic list of IDs of the seasons available
     * so we can get the current max season ID
     *
    @GET("/seasons/")
    Call<AllSeasons> getAllSeasonsFromAPI();
     */

}