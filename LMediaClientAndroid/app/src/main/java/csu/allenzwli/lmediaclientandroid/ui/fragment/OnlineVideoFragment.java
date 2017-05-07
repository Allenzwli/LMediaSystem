package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.VideoAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.presenter.VideoPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.OnlineVideoPresenterImp;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.view.VideoView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineVideoFragment extends BaseLazyFragment implements VideoView,SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.loading)
    RotateLoading loading;

    private VideoAdapter mOnlineVideoAdapter;

    private VideoPresenter mOnlineVideoPresenter;

    @Override
    protected void onFirstUserVisible() {
        mOnlineVideoPresenter=new OnlineVideoPresenterImp(mContext,this);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mOnlineVideoPresenter.loadVideoListData(false);
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
    public void onRefresh() {
        mOnlineVideoPresenter.loadVideoListData(true);
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
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void refreshVideoListData(List<Video> videoBeanLists) {
        if(videoBeanLists.size()>0){
            mOnlineVideoAdapter=new VideoAdapter(mContext,videoBeanLists);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mOnlineVideoAdapter);
        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }
    @Override
    public void navigateToLocalVideoItem(int position, Video videoBean) {

    }
}
