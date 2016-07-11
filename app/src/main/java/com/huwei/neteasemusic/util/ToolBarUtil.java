package com.huwei.neteasemusic.util;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.huwei.neteasemusic.R;

/**
 * @author jerry
 * @date 2016/07/11
 */
public class ToolBarUtil {

    public static void setBackStyle(final AppCompatActivity activity, Toolbar toolbar){
        toolbar.setNavigationIcon(R.drawable.actionbar_back);

        if (toolbar != null) {
            activity.setSupportActionBar(toolbar);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
