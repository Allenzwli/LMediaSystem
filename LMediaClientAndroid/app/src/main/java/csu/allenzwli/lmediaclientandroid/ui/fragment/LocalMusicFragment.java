package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.MusicAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.MusicPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.LocalMusicPresenterImp;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.view.MusicView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalMusicFragment extends BaseLazyFragment implements MusicView,SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.loading)
    RotateLoading loading;

    private MusicAdapter mLocalMusicAdapter;

    private MusicPresenter mLocalMusicPresenter;


    @Override
    protected void onFirstUserVisible() {
        mLocalMusicPresenter=new LocalMusicPresenterImp(mContext,this);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLocalMusicPresenter.loadMusicListData(false);
                }
            }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_common;
    }


    @Override
    public void showLoading() {
        loading.start();
    }

    @Override
    public void hideLoading() {
        loading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshMusicListData(List<Song> songBeanLists) {
        if(songBeanLists.size()>0){
            mLocalMusicAdapter=new MusicAdapter(mContext,songBeanLists);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mLocalMusicAdapter);
        }
    }

    @Override
    public void onRefresh() {
        mLocalMusicPresenter.loadMusicListData(true);
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void navigateToLocalMusicItem(int position, Song songBean) {

    }
}
