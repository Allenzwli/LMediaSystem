package csu.allenzwli.lmediaclientandroid.presenter.imp;

import android.content.Context;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.CommonInteractor;
import csu.allenzwli.lmediaclientandroid.interactor.imp.LocalVideoInteractorImp;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.presenter.VideoPresenter;
import csu.allenzwli.lmediaclientandroid.view.VideoView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoPresenterImp implements VideoPresenter,LoadResultCallBack<List<Video>> {

    private VideoView mVideoView;

    private CommonInteractor mLocalVideoInteractor;

    private Context mContext;

    public LocalVideoPresenterImp(Context context,VideoView localVideoView){
        mContext=context;
        mVideoView=localVideoView;
        mLocalVideoInteractor=new LocalVideoInteractorImp(mContext,this);
    }

    @Override
    public void loadVideoListData(boolean isSwipeRefresh) {
        if(!isSwipeRefresh){
            mVideoView.showLoading();
        }
        mLocalVideoInteractor.getCommonListData();
    }

    @Override
    public void onItemClickListener(int position, Video video) {
        mVideoView.navigateToLocalVideoItem(position,video);
    }

    @Override
    public void onSuccess(int code, List<Video> data) {
        mVideoView.hideLoading();
        mVideoView.refreshVideoListData(data);
    }

    @Override
    public void onError(int code, String msg) {
        mVideoView.hideLoading();
        mVideoView.showError(msg);
    }
}
