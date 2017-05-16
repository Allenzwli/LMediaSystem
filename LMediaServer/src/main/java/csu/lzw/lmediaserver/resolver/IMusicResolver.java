package csu.lzw.lmediaserver.resolver;

import csu.lzw.lmediaserver.pojo.Song;

/**
 * Created by allenzwli on 2017/5/16.
 */
public interface IMusicResolver {
    String getSongName();
    String getArtist();
    String getAlbum();
    String getPictureUrl();
    long getDuration();
}
