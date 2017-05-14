package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.pojo.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by allenzwli on 2017/5/6.
 */
public interface VideoService {
    Map saveVideo(MultipartFile videoFile, int adminId);
    List<Video> getVideosPerPage(int page);

    Map getVideosByStartIndexAndLength(String fuzzy, String orderCol, String orderDir, int startIndex, int length);
    Map deleteVideos(String needDeleteJsonArray);

    List<Video> getAllVideos();
}
