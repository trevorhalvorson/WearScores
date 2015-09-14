package com.trevorhalvorson.wearscores;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HandheldListenerService extends WearableListenerService {

    private static final String TAG = HandheldListenerService.class.getSimpleName();
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";
    private static final String NFL_SCORES_ENDPOINT = "http://www.nfl.com/liveupdate/scorestrip";

    private List<Game> mGames;

    private NFLService api;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(WEARABLE_DATA_PATH)) {
            Log.i(TAG, "Handheld message received");
            final String message = new String(messageEvent.getData());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("message", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(NFL_SCORES_ENDPOINT).build();
            api = restAdapter.create(NFLService.class);
            //getGames(api);

        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    public void getGames(NFLService api) {
        Log.i(TAG, "getGames API call");
        mGames = new ArrayList<>();

        api.getGames(new Callback<Week>() {
            @Override
            public void success(Week week, Response response) {
                for (Game g :
                        week.getGms()) {
                    mGames.add(g);
                    //addGameDataItem(g);
                }
                if (mGames.size() == 0) {

                } else {

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure " + error.getMessage());
            }

        });
    }
}