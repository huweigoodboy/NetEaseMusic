package com.huwei.neteasemusic.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huwei.neteasemusic.R;


/**
 * @author jerry
 * @date 2016/07/08
 */
public class PlayingListActivity extends BaseBottomMenuActivity{

    public static Intent getStartActIntent(Context from){
        Intent intent = new Intent(from,PlayingListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_playing_list);
    }
}
