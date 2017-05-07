package csu.allenzwli.lmediaclientandroid.presenter.imp;

import android.content.Context;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.LocalVideoInteractor;
import csu.allenzwli.lmediaclientandroid.interactor.imp.LocalVideoInteractorImp;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.presenter.LocalMusicPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.LocalVideoPresenter;
import csu.allenzwli.lmediaclientandroid.view.LocalVideoView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoPresenterImp implements LocalVideoPresenter,LoadResultCallBack<List<Video>> {

    private LocalVideoView mLocalVideoView;

    private LocalVideoInteractor mLocalVideoInteractor;

    private Context mContext;

    public LocalVideoPresenterImp(Context context,LocalVideoView localVideoView){
        mContext=context;
        mLocalVideoView=localVideoView;
        mLocalVideoInteractor=new LocalVideoInteractorImp(mContext,this);
    }

    @Override
    public void loadLocalVideoListData(boolean isSwipeRefresh) {
        mLocalVideoView.hideLoading();
        if(!isSwipeRefresh){
            mLocalVideoView.showLoading();
        }
        mLocalVideoInteractor.getLocalMusicListData();
    }

    @Override
    public void onItemClickListener(int position, Video video) {
        mLocalVideoView.navigateToLocalVideoItem(position,video);
    }

    @Override
    public void onSuccess(int code, List<Video> data) {
        mLocalVideoView.hideLoading();
        mLocalVideoView.refreshVideoListData(data);
    }

    @Override
    public void onError(int code, String msg) {
        mLocalVideoView.hideLoading();
        mLocalVideoView.showError(msg);
    }
}
