package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.LocalMusicAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.LocalMusicPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.LocalMusicPresenterImp;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.view.LocalMusicView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalMusicFragment extends BaseLazyFragment implements LocalMusicView,SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LocalMusicAdapter mLocalMusicAdapter;

    private LocalMusicPresenter mLocalMusicPresenter;


    @Override
    protected void onFirstUserVisible() {
        mLocalMusicPresenter=new LocalMusicPresenterImp(mContext,this);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLocalMusicPresenter.loadLocalMusicListData(false);
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
        return R.layout.fragment_local_common;
    }


    @Override
    public void showLoading() {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshMusicListData(List<Song> songBeanLists) {
        if(songBeanLists.size()>0){
            mLocalMusicAdapter=new LocalMusicAdapter(mContext,songBeanLists);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mLocalMusicAdapter);
        }
    }

    @Override
    public void navigateToLocalMusicItem(int position, Song songBean) {

    }

    @Override
    public void onRefresh() {
        mLocalMusicPresenter.loadLocalMusicListData(true);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void showError(String msg) {

    }
}
