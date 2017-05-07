package csu.allenzwli.lmediaclientandroid.presenter.imp;

import android.content.Context;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.CommonInteractor;
import csu.allenzwli.lmediaclientandroid.interactor.imp.OnlineMusicInteractorImp;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.MusicPresenter;
import csu.allenzwli.lmediaclientandroid.view.MusicView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineMusicPresenterImp implements MusicPresenter,LoadResultCallBack<List<Song>> {

    private MusicView mMusicView;
    private CommonInteractor mOnlineMusicInteractor;
    private Context mContext;

    public OnlineMusicPresenterImp(Context context,MusicView musicView){
        mMusicView=musicView;
        mContext=context;
        mOnlineMusicInteractor=new OnlineMusicInteractorImp(mContext,this);
    }


    @Override
    public void loadMusicListData(boolean isSwipeRefresh) {
        if (!isSwipeRefresh) {
            mMusicView.showLoading();
        }
        mOnlineMusicInteractor.getCommonListData();
    }

    @Override
    public void onItemClickListener(int position, Song song) {
        mMusicView.navigateToLocalMusicItem(position,song);
    }

    @Override
    public void onSuccess(int code, List<Song> data) {
        mMusicView.hideLoading();
        mMusicView.refreshMusicListData(data);
    }

    @Override
    public void onError(int code, String msg) {
        mMusicView.hideLoading();
        mMusicView.showError(msg);
    }
}
