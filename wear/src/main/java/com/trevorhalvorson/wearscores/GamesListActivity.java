package com.trevorhalvorson.wearscores;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GamesListActivity extends Activity {

    private static final String TAG = GamesListActivity.class.getSimpleName();
    private String[] games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        String message = getIntent().getStringExtra("message");
        Log.i(TAG, "onCreate " + message);
        games = message.split("\\|");
        Log.i(TAG, "onCreate " + games.length);

        WearableListView wearableListView = (WearableListView) findViewById(R.id.wearable_list);
        wearableListView.setAdapter(new Adapter(getApplicationContext(), games));

    }

    private class Adapter extends WearableListView.Adapter {
        private Context context = null;
        private final LayoutInflater layoutInflater;
        private String[] games;

        public Adapter(Context context, String[] games) {
            this.context = context;
            this.games = games;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public class ItemViewHolder extends WearableListView.ViewHolder {

            private TextView textView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.subjects);
            }
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ItemViewHolder(layoutInflater.inflate(R.layout.list_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            TextView textView = itemViewHolder.textView;
            textView.setText(games[i]);
        }

        @Override
        public int getItemCount() {
            return games.length;
        }
    }
}
