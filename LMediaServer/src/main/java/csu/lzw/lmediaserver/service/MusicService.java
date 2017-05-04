package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Song;

import java.util.List;

/**
 * Created by allenzwli on 2017/4/12.
 */
public interface MusicService {
    void saveSong(Song song);
    List<Song> getSongsPerPage(int page);
    List<Song> getSongsByStartIndexAndLength(int startIndex,int length);
    int getSongAllCount();
}
