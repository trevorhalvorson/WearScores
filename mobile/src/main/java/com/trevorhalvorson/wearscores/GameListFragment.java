package com.trevorhalvorson.wearscores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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
    int mLastPosition = -1;

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
        setRetainInstance(true);
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
        private LinearLayout mGameItemLayout;
        private TextView mGameDetailsTextView;
        private TextView mScoresTextView;

        public GameHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mGameItemLayout = (LinearLayout)
                    itemView.findViewById(R.id.list_item_layout);
            mGameDetailsTextView = (TextView)
                    itemView.findViewById(R.id.list_item_details_text_view);
            mScoresTextView = (TextView)
                    itemView.findViewById(R.id.list_item_scores_text_view);
        }

        public void bindGame(Game game) {
            String details = "";
            mGame = game;
            switch (mGame.getQ()) {
                case "F":
                    mGameItemLayout.setBackgroundColor(getResources().getColor(R.color.game_finished_color));
                    details = mGame.getD() + " " + mGame.getT() + " - FINISHED";
                    break;
                case "FO":
                    mGameItemLayout.setBackgroundColor(getResources().getColor(R.color.game_finished_color));
                    details = mGame.getD() + " " + mGame.getT() + " - FINISHED IN OT";
                    break;
                case "P":
                    mGameItemLayout.setBackgroundColor(getResources().getColor(R.color.game_pending_color));
                    details = mGame.getD() + " " + mGame.getT() + " - UPCOMING";
                    break;
                default:
                    mGameItemLayout.setBackgroundColor(getResources().getColor(R.color.game_in_progress_color));
                    details = mGame.getD() + " " + mGame.getT() + " - IN PROGRESS";
            }
            mGameDetailsTextView.setText(details);
            mScoresTextView.setText(mGame.getVs() + " " + mGame.getV() + " @ " + mGame.getH() + " " + mGame.getHs());
        }

        @Override
        public void onClick(View v) {
            String url = "http://www.google.com/#q=" + mGame.getVnn() + " vs " + mGame.getHnn();
            Fragment webViewFragment = WebViewFragment.newInstance(url);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, webViewFragment, "webViewFragment");
            fragmentTransaction.addToBackStack("webViewFragment");
            fragmentTransaction.commit();
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
                    .inflate(R.layout.list_item, viewGroup, false);

            return new GameHolder(view);
        }

        @Override
        public void onBindViewHolder(GameHolder gameHolder, int position) {
            Game game = mGames.get(position);
            gameHolder.bindGame(game);
            setAnimation(gameHolder.mGameItemLayout, position);
        }

        private void setAnimation(View viewToAnimate, int position) {
            if (position > mLastPosition) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                mLastPosition = position;
            }
        }

        @Override
        public int getItemCount() {
            return mGames.size();
        }
    }
}