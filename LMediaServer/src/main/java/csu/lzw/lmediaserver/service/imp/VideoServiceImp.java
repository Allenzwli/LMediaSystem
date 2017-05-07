package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.VideoMapper;
import csu.lzw.lmediaserver.pojo.Video;
import csu.lzw.lmediaserver.service.VideoService;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by allenzwli on 2017/5/6.
 */
@Service("VideoService")
@Transactional(rollbackFor = Exception.class)
public class VideoServiceImp implements VideoService{
    @Resource
    private VideoMapper mVideoMapper;

    public void saveVideo(Video video) {
        mVideoMapper.saveVideo(video);
    }

    public List<Video> getVideosPerPage(int page) {
        return mVideoMapper.getVideosByStartAndLength(StaticConfig.VIDEO_PER_PAGE_SIZE*(page-1),StaticConfig.VIDEO_PER_PAGE_SIZE,null,null,null);
    }

    public List<Video> getVideosByStartIndexAndLength(String fuzzy, String orderCol, String orderDir, int startIndex, int length) {
        return mVideoMapper.getVideosByStartAndLength(startIndex,length,fuzzy,orderCol,orderDir);
    }

    public int getVideoAllCount(String fuzzy) {
        return mVideoMapper.getVideosAllCount(fuzzy);
    }

    public void deleteVideos(int[] idArray) {
        mVideoMapper.deleteVideos(idArray);
    }

    public List<Video> getAllVideos() {
        return mVideoMapper.getAllVideos();
    }
}
