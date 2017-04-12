package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Song;

import java.util.List;

/**
 * Created by allenzwli on 2017/4/12.
 */
public interface MusicService {
    void saveSong(Song song);
    List<Song> getAllSongs();
}
