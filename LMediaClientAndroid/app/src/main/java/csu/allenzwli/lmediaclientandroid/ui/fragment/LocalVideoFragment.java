package csu.allenzwli.lmediaclientandroid.ui.fragment;

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
import csu.allenzwli.lmediaclientandroid.adapter.VideoAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.presenter.VideoPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.LocalVideoPresenterImp;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.view.VideoView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoFragment extends BaseLazyFragment implements VideoView,SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.list_view)
    ListView mListView;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.loading)
    RotateLoading loading;

    private VideoAdapter mLocalVideoAdapter;

    private VideoPresenter mLocalVideoPresenter;

    @Override
    protected void onFirstUserVisible() {
        mLocalVideoPresenter=new LocalVideoPresenterImp(mContext,this);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLocalVideoPresenter.loadVideoListData(false);
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
        mLocalVideoAdapter=new VideoAdapter(mContext);
        mListView.setAdapter(mLocalVideoAdapter);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_common;
    }

    @Override
    public void onRefresh() {
        mLocalVideoPresenter.loadVideoListData(true);
    }

    @Override
    public void showLoading() {
        if(loading!=null)
            loading.start();
    }

    @Override
    public void hideLoading() {
        if(loading!=null)
            loading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshVideoListData(List<Video> videoBeanLists) {
        if(videoBeanLists!=null&&videoBeanLists.size()>0){
            mLocalVideoAdapter.getmLocalVideoLists().clear();
            mLocalVideoAdapter.getmLocalVideoLists().addAll(videoBeanLists);
            mLocalVideoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void navigateToLocalVideoItem(int position, Video videoBean) {
        showToast(videoBean.getFileUrl());
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

}
