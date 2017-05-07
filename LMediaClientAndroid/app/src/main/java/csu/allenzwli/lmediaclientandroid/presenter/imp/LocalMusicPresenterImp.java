package csu.allenzwli.lmediaclientandroid.presenter.imp;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.LocalMusicInteractor;
import csu.allenzwli.lmediaclientandroid.interactor.imp.LocalMusicInteractorImp;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.LocalMusicPresenter;
import csu.allenzwli.lmediaclientandroid.view.LocalMusicView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalMusicPresenterImp implements LocalMusicPresenter,LoadResultCallBack<List<Song>>{

    private LocalMusicView mLocalMusicView;
    private LocalMusicInteractor mLocalMusicInteractor;
    private Context mContext;

    public LocalMusicPresenterImp(Context context,LocalMusicView localMusicView){
        mContext=context;
        mLocalMusicView=localMusicView;
        mLocalMusicInteractor=new LocalMusicInteractorImp(this,mContext);
    }

    @Override
    public void loadLocalMusicListData(boolean isSwipeRefresh) {
        mLocalMusicView.hideLoading();
        if (!isSwipeRefresh) {
            mLocalMusicView.showLoading();
        }
        mLocalMusicInteractor.getLocalMusicListData();
    }

    @Override
    public void onItemClickListener(int position, Song song) {
        mLocalMusicView.navigateToLocalMusicItem(position,song);
    }

    @Override
    public void onSuccess(int code, List<Song> data) {
        mLocalMusicView.hideLoading();
        mLocalMusicView.refreshMusicListData(data);
    }

    @Override
    public void onError(int code, String msg) {
        mLocalMusicView.hideLoading();
        mLocalMusicView.showError(msg);
    }
}
