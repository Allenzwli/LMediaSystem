package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.LocalVideoAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.presenter.LocalVideoPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.LocalVideoPresenterImp;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.view.LocalVideoView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoFragment extends BaseLazyFragment implements LocalVideoView,SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LocalVideoAdapter mLocalVideoAdapter;

    private LocalVideoPresenter mLocalVideoPresenter;

    @Override
    protected void onFirstUserVisible() {
        mLocalVideoPresenter=new LocalVideoPresenterImp(mContext,this);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLocalVideoPresenter.loadLocalVideoListData(false);
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
    public void onRefresh() {
        mLocalVideoPresenter.loadLocalVideoListData(true);
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
    public void refreshVideoListData(List<Video> videoBeanLists) {
        if(videoBeanLists.size()>0){
            mLocalVideoAdapter=new LocalVideoAdapter(mContext,videoBeanLists);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mLocalVideoAdapter);
        }
    }

    @Override
    public void navigateToLocalVideoItem(int position, Video videoBean) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }
}
