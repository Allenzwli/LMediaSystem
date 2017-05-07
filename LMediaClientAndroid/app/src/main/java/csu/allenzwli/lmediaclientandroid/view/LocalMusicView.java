package csu.allenzwli.lmediaclientandroid.view;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.model.Song;

/**
 * Created by allenzwli on 2017/5/7.
 */

public interface LocalMusicView extends BaseView {
    void refreshMusicListData(List<Song> songBeanLists);

    void navigateToLocalMusicItem(int position, Song songBean);
}
