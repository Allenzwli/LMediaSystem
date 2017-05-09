package csu.allenzwli.lmediaclientandroid.view;

import csu.allenzwli.lmediaclientandroid.model.Video;

/**
 * Created by allenzwli on 2017/5/9.
 */

public interface OnlineVideoView extends VideoView {
    void showMaterialDialogWhenItemLongClick(int position,Video video);
}
