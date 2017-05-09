package csu.allenzwli.lmediaclientandroid.presenter.imp;

import android.content.Context;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.CommonInteractor;
import csu.allenzwli.lmediaclientandroid.interactor.imp.OnlineMusicInteractorImp;
import csu.allenzwli.lmediaclientandroid.interactor.imp.OnlineVideoInteractorImp;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.presenter.VideoPresenter;
import csu.allenzwli.lmediaclientandroid.view.MusicView;
import csu.allenzwli.lmediaclientandroid.view.OnlineVideoView;
import csu.allenzwli.lmediaclientandroid.view.VideoView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineVideoPresenterImp implements VideoPresenter,LoadResultCallBack<List<Video>>{

    private OnlineVideoView mVideoView;
    private CommonInteractor mOnlineVideoInteractor;
    private Context mContext;

    public OnlineVideoPresenterImp(Context context,OnlineVideoView videoView){
        mContext=context;
        mVideoView=videoView;
        mOnlineVideoInteractor=new OnlineVideoInteractorImp(mContext,this);
    }

    @Override
    public void loadVideoListData(boolean isSwipeRefresh) {
        if(!isSwipeRefresh){
            mVideoView.showLoading();
        }
        mOnlineVideoInteractor.getCommonListData();
    }

    @Override
    public void OnItemLongClickListner(int position, Video video) {
        mVideoView.showMaterialDialogWhenItemLongClick(position,video);
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
