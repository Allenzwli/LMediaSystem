package csu.allenzwli.lmediaclientandroid.presenter;

import csu.allenzwli.lmediaclientandroid.model.Video;

/**
 * Created by allenzwli on 2017/5/7.
 */

public interface LocalVideoPresenter {

    void loadLocalVideoListData(boolean isSwipeRefresh);

    void onItemClickListener(int position, Video video);
}
