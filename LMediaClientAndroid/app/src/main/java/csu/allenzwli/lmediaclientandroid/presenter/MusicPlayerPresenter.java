package csu.allenzwli.lmediaclientandroid.presenter;

import csu.allenzwli.lmediaclientandroid.model.Song;

/**
 * Created by allenzwli on 2017/5/8.
 */

public interface MusicPlayerPresenter {
    void onNextClick();

    void onPrevClick();

    void onStartPlay();

    void onPausePlay();

    void onRePlay();

    void seekTo(int position);

    void onStopPlay();

    void refreshProgress(int progress);

    void refreshSecondProgress(int progress);

    void refreshPageInfo(Song entity, int totalDuration);
}
