package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.MusicAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.MusicPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.LocalMusicPresenterImp;
import csu.allenzwli.lmediaclientandroid.ui.MusicPlayerActivity;
import csu.allenzwli.lmediaclientandroid.ui.widget.MusicPlayer;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.util.MusicPlayState;
import csu.allenzwli.lmediaclientandroid.util.PlayListSingleton;
import csu.allenzwli.lmediaclientandroid.view.MusicView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalMusicFragment extends BaseLazyFragment implements MusicView,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener {

    @InjectView(R.id.list_view)
    ListView mListView;

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
        mLocalMusicAdapter=new MusicAdapter(mContext);
        mListView.setAdapter(mLocalMusicAdapter);
        mListView.setOnItemClickListener(this);
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
        if(songBeanLists!=null&&songBeanLists.size()>0){
            mLocalMusicAdapter.getmSongBeanLists().clear();
            mLocalMusicAdapter.getmSongBeanLists().addAll(songBeanLists);
            mLocalMusicAdapter.notifyDataSetChanged();
            PlayListSingleton.getInstance().refreshSongList(songBeanLists);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mLocalMusicPresenter.onItemClickListener(i,mLocalMusicAdapter.getmSongBeanLists().get(i));
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void navigateToLocalMusicItem(int position, Song songBean) {
        //showToast(songBean.getFileUrl());
        PlayListSingleton.getInstance().refreshSongList(mLocalMusicAdapter.getmSongBeanLists());
        Intent intent=new Intent(mContext,MusicPlayerActivity.class);
        intent.putExtra(MusicPlayState.PLAYING_SONG,mLocalMusicAdapter.getmSongBeanLists().get(position));
        intent.putExtra(MusicPlayState.PLAYING_INDEX,position);
        startActivity(intent);
    }

}
