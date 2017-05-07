package csu.allenzwli.lmediaclientandroid.presenter;

import csu.allenzwli.lmediaclientandroid.model.Song;

/**
 * Created by allenzwli on 2017/5/7.
 */

public interface MusicPresenter {

    void loadMusicListData(boolean isSwipeRefresh);

    void onItemClickListener(int position, Song song);
}
