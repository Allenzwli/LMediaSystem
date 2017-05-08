package csu.allenzwli.lmediaclientandroid.view;

import csu.allenzwli.lmediaclientandroid.model.Song;

/**
 * Created by allenzwli on 2017/5/8.
 */

public interface MusicPlayerView {
    void rePlayMusic();

    void startPlayMusic();

    void stopPlayMusic();

    void pausePlayMusic();

    void playNextMusic();

    void playPrevMusic();

    void seekToPosition(int position);

    void refreshPlayProgress(int progress);

    void refreshPlaySecondProgress(int progress);

    void refreshPageInfo(Song entity, int totalDuration);
}
