package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.MusicAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.MusicPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.OnlineMusicPresenterImp;
import csu.allenzwli.lmediaclientandroid.ui.MusicPlayerActivity;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.util.MusicPlayState;
import csu.allenzwli.lmediaclientandroid.util.PlayListSingleton;
import csu.allenzwli.lmediaclientandroid.view.MusicView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineMusicFragment extends BaseLazyFragment implements MusicView,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{

    @InjectView(R.id.list_view)
    ListView mListView;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.loading)
    RotateLoading loading;

    private MusicAdapter mOnlineMusicAdapter;

    private MusicPresenter mOnlineMusicPresenter;

    @Override
    protected void onFirstUserVisible() {
        mOnlineMusicPresenter=new OnlineMusicPresenterImp(mContext,this);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mOnlineMusicPresenter.loadMusicListData(false);
                }
            }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mOnlineMusicAdapter=new MusicAdapter(mContext);
        mListView.setAdapter(mOnlineMusicAdapter);
        mListView.setOnItemClickListener(this);
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
            mOnlineMusicAdapter.getmSongBeanLists().clear();
            mOnlineMusicAdapter.getmSongBeanLists().addAll(songBeanLists);
            mOnlineMusicAdapter.notifyDataSetChanged();
            PlayListSingleton.getInstance().refreshSongList(songBeanLists);
        }
    }

    @Override
    public void onRefresh() {
        mOnlineMusicPresenter.loadMusicListData(true);
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_common;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mOnlineMusicPresenter.onItemClickListener(i,mOnlineMusicAdapter.getmSongBeanLists().get(i));
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
        PlayListSingleton.getInstance().refreshSongList(mOnlineMusicAdapter.getmSongBeanLists());
        Intent intent=new Intent(mContext,MusicPlayerActivity.class);
        intent.putExtra(MusicPlayState.PLAYING_SONG,mOnlineMusicAdapter.getmSongBeanLists().get(position));
        intent.putExtra(MusicPlayState.PLAYING_INDEX,position);
        startActivity(intent);
    }

}
