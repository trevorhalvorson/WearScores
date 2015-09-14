package com.trevorhalvorson.wearscores;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class GamesListActivity extends Activity
        implements WearableListView.ClickListener {

    private static final String TAG = GamesListActivity.class.getSimpleName();
    private String[] games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        String message = getIntent().getStringExtra("message");
        games = message.split("\\|");

        WearableListView wearableListView = (WearableListView) findViewById(R.id.wearable_list);
        wearableListView.setAdapter(new Adapter(this, games));
        wearableListView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Log.d(TAG, "onClick ");
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

    private static class Adapter extends WearableListView.Adapter {
        private Context mContext;
        private final LayoutInflater mInflater;
        private String[] games;

        public Adapter(Context context, String[] games) {
            this.mContext = context;
            this.games = games;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WearableListView.ViewHolder(
                    mInflater.inflate(R.layout.list_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
            TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.game_info_text_view);
            textView.setText(games[position]);
            viewHolder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return games.length;
        }
    }
}
