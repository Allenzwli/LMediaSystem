package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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
import csu.allenzwli.lmediaclientandroid.presenter.imp.OnlineVideoPresenterImp;
import csu.allenzwli.lmediaclientandroid.service.MusicPlayService;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.view.OnlineVideoView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineVideoFragment extends BaseLazyFragment implements OnlineVideoView,SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.list_view)
    ListView mListView;

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
        mOnlineVideoAdapter=new VideoAdapter(mContext);
        mListView.setAdapter(mOnlineVideoAdapter);
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
        if(videoBeanLists!=null&&videoBeanLists.size()>0){
            mOnlineVideoAdapter.getmLocalVideoLists().clear();
            mOnlineVideoAdapter.getmLocalVideoLists().addAll(videoBeanLists);
            mOnlineVideoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onUserVisible() {
        mContext.stopService(new Intent(mContext, MusicPlayService.class));
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void showMaterialDialogWhenItemLongClick(int position, Video video) {
        //showToast(video.getFileUrl());
        //ListView使用了CardView作为item，点击事件失效，要在adapter的getView里设置
    }

}
