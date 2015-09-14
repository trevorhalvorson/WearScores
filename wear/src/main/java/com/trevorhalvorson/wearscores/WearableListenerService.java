package com.trevorhalvorson.wearscores;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;


public class WearableListenerService extends com.google.android.gms.wearable.WearableListenerService {

    private static final String TAG = WearableListenerService.class.getSimpleName();
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "Wear message received");
        if (messageEvent.getPath().equals(WEARABLE_DATA_PATH)) {
            final String message = new String(messageEvent.getData());
            Intent intent = new Intent(this, GamesListActivity.class);
            intent.putExtra("message", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
