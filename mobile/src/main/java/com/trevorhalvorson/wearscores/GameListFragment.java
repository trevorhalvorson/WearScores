package com.trevorhalvorson.wearscores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by trevo on 9/10/2015.
 */
public class GameListFragment extends Fragment {
    private static final String TAG = GameListFragment.class.getSimpleName();

    private RecyclerView mGameRecyclerView;
    private ArrayList<Game> mGames = new ArrayList<>();
    private GameAdapter mAdapter;

    public static GameListFragment newInstance(ArrayList<Game> games) {
        Bundle args = new Bundle();
        args.putSerializable("games_key", games);

        GameListFragment fragment = new GameListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGames = (ArrayList<Game>) getArguments().getSerializable("games_key");
        mAdapter = new GameAdapter(mGames);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        mGameRecyclerView = (RecyclerView) rootView.findViewById(R.id.game_recycler_view);
        mGameRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGameRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mGameRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Game mGame;
        private TextView mGameTitleTextView;

        public GameHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mGameTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_game_title_text_view);
        }

        public void bindGame(Game game) {
            mGame = game;
            mGameTitleTextView.setText(mGame.getV() + " @ " + mGame.getH());
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick ");
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {

        private ArrayList<Game> mGames;

        public GameAdapter(ArrayList<Game> games) {
            mGames = games;
        }

        @Override
        public GameHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_game, viewGroup, false);

            return new GameHolder(view);
        }

        @Override
        public void onBindViewHolder(GameHolder gameHolder, int position) {
            Game game = mGames.get(position);
            gameHolder.bindGame(game);
        }

        @Override
        public int getItemCount() {
            return mGames.size();
        }
    }
}