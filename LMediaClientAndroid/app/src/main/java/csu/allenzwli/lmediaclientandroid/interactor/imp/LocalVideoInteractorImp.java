package csu.allenzwli.lmediaclientandroid.interactor.imp;

import android.content.Context;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.LocalMusicInteractor;
import csu.allenzwli.lmediaclientandroid.interactor.LocalVideoInteractor;
import csu.allenzwli.lmediaclientandroid.model.Video;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoInteractorImp implements LocalVideoInteractor {

    private LoadResultCallBack<List<Video>> mLoadResultListener;
    private Context mContext;

    public LocalVideoInteractorImp(Context context,LoadResultCallBack<List<Video>> listener){
        mContext=context;
        mLoadResultListener=listener;
    }

    @Override
    public void getLocalMusicListData() {

    }
}
