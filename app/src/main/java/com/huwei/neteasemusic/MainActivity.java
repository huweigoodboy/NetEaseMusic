package com.huwei.neteasemusic;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huwei.neteasemusic.main.MusicFragment;
import com.huwei.neteasemusic.main.RelationShipFragment;
import com.huwei.neteasemusic.main.DiscoverFragment;
import com.huwei.neteasemusic.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();

    public static final int TAB_DISCOVER = 0;
    public static final int TAB_MUSIC = 1;
    public static final int TAB_RELATION = 2;

    public static final int[] TAB_ICON = {R.drawable.actionbar_discover, R.drawable.actionbar_music, R.drawable.actionbar_friends};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();
        initToolBar();
        initTabLayout();
        initView();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected boolean isDrawLayout() {
        return true;
    }

    @Override
    protected boolean isNeedToolBar() {
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        mToolBar.setNavigationIcon(R.drawable.actionbar_menu);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        mToolBar.setTitle("");

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mFragmentList.add(new DiscoverFragment());
        mFragmentList.add(new MusicFragment());
        mFragmentList.add(new RelationShipFragment());

        mViewPager.setOffscreenPageLimit(mFragmentList.size() - 1);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        mViewPager.setCurrentItem(TAB_DISCOVER);
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

//        Rect bounds = new Rect(0,0, DisplayUtil.dip2px(this,56),DisplayUtil.dip2px(this,56));
//        Drawable drawable = getResources().getDrawable(R.drawable.actionbar_discover);
//        drawable.setBounds(bounds);
//        mTabLayout.getTabAt(TAB_DISCOVER).setIcon(drawable);
//
//        drawable = getResources().getDrawable(R.drawable.actionbar_music);
//        drawable.setBounds(bounds);
//        mTabLayout.getTabAt(TAB_MUSIC).setIcon(drawable);
//
//        drawable = getResources().getDrawable(R.drawable.actionbar_friends);
//        drawable.setBounds(bounds);
//        mTabLayout.getTabAt(TAB_RELATION).setIcon(drawable);

        ImageView tabDiscover = getIconImageView(R.drawable.actionbar_discover);
        tabDiscover.setSelected(true);
        mTabLayout.getTabAt(TAB_DISCOVER).setCustomView(tabDiscover);
        mTabLayout.getTabAt(TAB_MUSIC).setCustomView(getIconImageView(R.drawable.actionbar_music));
        mTabLayout.getTabAt(TAB_RELATION).setCustomView(getIconImageView(R.drawable.actionbar_friends));
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private ImageView getIconImageView(int resId) {
        ImageView iv_icon = new ImageView(mContext);
        int wh = DisplayUtil.dip2px(this, 56);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(wh, wh);
        iv_icon.setLayoutParams(layoutParams);

        iv_icon.setImageResource(resId);

        return iv_icon;
    }
}
