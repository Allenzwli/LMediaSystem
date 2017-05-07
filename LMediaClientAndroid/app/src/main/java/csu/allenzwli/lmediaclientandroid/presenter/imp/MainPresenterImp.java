package csu.allenzwli.lmediaclientandroid.presenter.imp;

import android.content.Context;

import csu.allenzwli.lmediaclientandroid.interactor.MainInteractor;
import csu.allenzwli.lmediaclientandroid.interactor.imp.MainInteractorImp;
import csu.allenzwli.lmediaclientandroid.presenter.MainPresenter;
import csu.allenzwli.lmediaclientandroid.view.MainView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class MainPresenterImp implements MainPresenter {

    private Context mContext;
    private MainView mMainView;
    private MainInteractor mMainInteractor;

    public MainPresenterImp(Context context,MainView mainView){
        if (null == mainView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mMainView = mainView;
        mMainInteractor = new MainInteractorImp();
    }

    @Override
    public void initialized() {
        mMainView.initializeViews(mMainInteractor.getPagerFragments());
    }
}
