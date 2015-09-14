package com.trevorhalvorson.wearscores;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String NFL_SCORES_ENDPOINT = "http://www.nfl.com/liveupdate/scorestrip";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";

    private List<Game> mGames;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;

    private GoogleApiClient mGoogleApiClient;

    private NFLService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(NFL_SCORES_ENDPOINT).build();
        api = restAdapter.create(NFLService.class);
        getGames(api);
    }

    public void getGames(NFLService api) {
        Log.i(TAG, "getGames API call");
        mProgressBar.setVisibility(View.VISIBLE);
        mGames = new ArrayList<>();

        api.getGames(new Callback<Week>() {
            @Override
            public void success(Week week, Response response) {
                stopLoadingUI();
                for (Game g :
                        week.getGms()) {
                    mGames.add(g);
                }
                mToolbar.setTitle("Week " + week.getW());
                if (mGames.size() == 0) {
                    Snackbar.make(findViewById(R.id.container),
                            "No games this week.",
                            Snackbar.LENGTH_LONG).show();
                } else {

                    if (getIntent().getStringExtra("message") != null) {
                        sendMessage();
                    }

                    Fragment gamesListFragment = GameListFragment.newInstance((ArrayList<Game>) mGames);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, gamesListFragment)
                            .commit();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                stopLoadingUI();
                Snackbar.make(findViewById(R.id.container),
                        error.getMessage(),
                        Snackbar.LENGTH_LONG).show();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //sendMessage();
    }

    private void sendMessage() {
        if (mGoogleApiClient.isConnected()) {
            Log.i(TAG, "sendMessage ");
            String message = arrayToString(mGames);

            new SendMessageToDataLayer(WEARABLE_DATA_PATH, message).start();

        }
    }

    private String arrayToString(List<Game> mGames) {
        String allGamesScores = "";
        for (Game game :
                mGames) {
            allGamesScores = allGamesScores.concat(game.getVs() + " " + game.getV() +
                    " @ " + game.getH() + " " + game.getHs() + "|");
        }
        return allGamesScores;
    }

    public void sendMessageOnClick(View view) {
        sendMessage();
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
                    Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            for (Node node :
                    nodesList.getNodes()) {
                MessageApi.SendMessageResult messageResult =
                        Wearable.MessageApi.sendMessage(
                                mGoogleApiClient, node.getId(), path, message.getBytes()).await();

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


    private void stopLoadingUI() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getGames(api);
                return true;
            case R.id.action_settings:
                sendMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
