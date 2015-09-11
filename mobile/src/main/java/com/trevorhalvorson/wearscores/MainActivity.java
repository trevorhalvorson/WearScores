package com.trevorhalvorson.wearscores;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String NFL_SCORES_ENDPOINT = "http://www.nfl.com/liveupdate/scorestrip";

    private List<Game> mGames;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private NFLService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mGames = new ArrayList<>();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(NFL_SCORES_ENDPOINT).build();
        api = restAdapter.create(NFLService.class);
        getGames(api);
    }

    public void getGames(NFLService api) {
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

    private void stopLoadingUI() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
