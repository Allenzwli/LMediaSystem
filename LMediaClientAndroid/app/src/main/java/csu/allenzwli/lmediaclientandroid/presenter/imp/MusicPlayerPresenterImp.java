package csu.allenzwli.lmediaclientandroid.presenter.imp;

import android.content.Context;

import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.MusicPlayerPresenter;
import csu.allenzwli.lmediaclientandroid.view.MusicPlayerView;

/**
 * Created by allenzwli on 2017/5/8.
 */

public class MusicPlayerPresenterImp implements MusicPlayerPresenter {

    private Context mContext = null;
    private MusicPlayerView mMusicsView = null;

    public MusicPlayerPresenterImp(Context context,MusicPlayerView musicPlayerView){
        mMusicsView=musicPlayerView;
        mContext=context;
    }

    @Override
    public void onNextClick() {
        mMusicsView.playNextMusic();
    }

    @Override
    public void onPrevClick() {
        mMusicsView.playPrevMusic();
    }

    @Override
    public void onStartPlay() {
        mMusicsView.startPlayMusic();
    }

    @Override
    public void onPausePlay() {
        mMusicsView.pausePlayMusic();
    }

    @Override
    public void onRePlay() {
        mMusicsView.rePlayMusic();
    }

    @Override
    public void seekTo(int position) {
        mMusicsView.seekToPosition(position);
    }

    @Override
    public void onStopPlay() {
        mMusicsView.stopPlayMusic();
    }

    @Override
    public void refreshProgress(int progress) {
        mMusicsView.refreshPlayProgress(progress);
    }

    @Override
    public void refreshSecondProgress(int progress) {
        mMusicsView.refreshPlaySecondProgress(progress);
    }

    @Override
    public void refreshPageInfo(Song entity, int totalDuration) {
        mMusicsView.refreshPageInfo(entity, totalDuration);
    }
}
