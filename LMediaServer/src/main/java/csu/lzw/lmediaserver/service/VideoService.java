package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.pojo.Video;

import java.util.List;

/**
 * Created by allenzwli on 2017/5/6.
 */
public interface VideoService {
    void saveVideo(Video video);
    List<Video> getVideosPerPage(int page);
    List<Video> getVideosByStartIndexAndLength(String fuzzy,String orderCol,String orderDir,int startIndex, int length);
    int getVideoAllCount(String fuzzy);
    void deleteVideos(int[] idArray);
}
