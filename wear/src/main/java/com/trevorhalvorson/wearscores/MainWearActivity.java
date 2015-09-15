package com.trevorhalvorson.wearscores;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by trevo on 9/11/2015.
 */
public class MainWearActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MainWearActivity.class.getSimpleName();
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        sendMessage();
    }

    private void sendMessage() {
        if (googleApiClient.isConnected()) {
            String message = "football";

            new SendMessageToDataLayer(WEARABLE_DATA_PATH, message).start();

        }
    }

    public class SendMessageToDataLayer extends Thread {
        String path;
        String message;

        public SendMessageToDataLayer(String path, String message) {
            this.path = path;
            this.message = message;
        }

        @Override
        public void run() {
            NodeApi.GetConnectedNodesResult nodesList =
                    Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
            for (Node node :
                    nodesList.getNodes()) {
                MessageApi.SendMessageResult messageResult =
                        Wearable.MessageApi.sendMessage(
                                googleApiClient, node.getId(), path, message.getBytes()).await();

                if (messageResult.getStatus().isSuccess()) {
                    Log.i(TAG, "Message: successfully sent to " + node.getDisplayName());
                    Log.i(TAG, "Message: Node Id is " + node.getId());
                    Log.i(TAG, "Message: Node size is " + nodesList.getNodes().size());
                } else {
                    Log.i(TAG, "Error sending message");
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}