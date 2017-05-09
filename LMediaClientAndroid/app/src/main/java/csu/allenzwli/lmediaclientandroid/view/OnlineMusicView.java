package csu.allenzwli.lmediaclientandroid.view;

import csu.allenzwli.lmediaclientandroid.model.Song;

/**
 * Created by allenzwli on 2017/5/9.
 */

public interface OnlineMusicView extends MusicView {

    void showMaterialDialogWhenItemLongClick(int position,Song song);
}
