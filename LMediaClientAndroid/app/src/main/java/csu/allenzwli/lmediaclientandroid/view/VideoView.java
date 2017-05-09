package csu.allenzwli.lmediaclientandroid.view;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.model.Video;

/**
 * Created by allenzwli on 2017/5/7.
 */

public interface VideoView extends BaseView{
    void refreshVideoListData(List<Video> videoBeanLists);

}
