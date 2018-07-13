package com.example.android.fnchallengelist;

/**
 * Created by gbolatto on 6/6/2018.
 */
public class APIUtils {

    // TODO: add server url
    public static final String BASE_URL = "http://0.0.0.0";

    public static APIService getAPIService() {
        return APIClient.getClient(BASE_URL).create(APIService.class);
    }
}