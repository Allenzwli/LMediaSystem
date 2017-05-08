package csu.allenzwli.lmediaclientandroid.ui;

import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.VPFragmentAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseActivity;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.presenter.MainPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.MainPresenterImp;
import csu.allenzwli.lmediaclientandroid.ui.widget.XViewPager;
import csu.allenzwli.lmediaclientandroid.view.MainView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,MainView {

    @InjectView(R.id.home_container)
    XViewPager mViewPager;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.nav_view)
    NavigationView mNavigationView;

    private MainPresenter mMainPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mMainPresenter=new MainPresenterImp(this,this);
        mMainPresenter.initialized();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }

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
       if(id==R.id.action_ablout){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    enum MediaMenuItem{
        LocalMusic,
        OnlineMusic,
        LocalVideo,
        OnlineVideo,
        AdminManage,
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        MediaMenuItem mediaMenuItem=MediaMenuItem.LocalMusic;
        int id = item.getItemId();
        if(id==R.id.nav_admin_manage){
            //弹管理员操作
            mediaMenuItem=MediaMenuItem.AdminManage;
        }else if(id==R.id.nav_feedback){
            //弹出反馈
            return true;
        }
        if (id == R.id.nav_local_music) {
            mediaMenuItem=MediaMenuItem.LocalMusic;
        } else if (id == R.id.nav_online_music) {
            mediaMenuItem=MediaMenuItem.OnlineMusic;
        } else if (id == R.id.nav_local_video) {
            mediaMenuItem=MediaMenuItem.LocalVideo;
        } else if (id == R.id.nav_online_video) {
            mediaMenuItem=MediaMenuItem.OnlineVideo;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mViewPager.setCurrentItem(mediaMenuItem.ordinal());
        return true;
    }

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments) {
        if (null != fragments && !fragments.isEmpty()) {
            mViewPager.setEnableScroll(false);
            mViewPager.setOffscreenPageLimit(fragments.size());
            mViewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragments));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
