package com.trevorhalvorson.wearscores;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class HandheldListenerService extends WearableListenerService {

    private static final String TAG = HandheldListenerService.class.getSimpleName();
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";

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
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}