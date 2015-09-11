package com.trevorhalvorson.wearscores;


import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Trevor on 8/20/2015.
 */
public interface NFLService {

    @GET("/ss.json")
    void getGames(Callback<Week> response);
}
