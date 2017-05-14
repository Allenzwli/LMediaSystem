package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.MusicMapper;
import csu.lzw.lmediaserver.mapper.VideoMapper;
import csu.lzw.lmediaserver.service.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by allenzwli on 2017/5/14.
 */
@Service("HomeService")
@Transactional(rollbackFor = Exception.class)
public class HomeServiceImp implements HomeService {

    @Resource
    private MusicMapper mMusicMapper;

    @Resource
    private VideoMapper mVideoMapper;

    public int getSongAllCount() {
        return mMusicMapper.getSongsAllCount(null);
    }

    public int getVideoAllCount() {
        return mVideoMapper.getVideosAllCount(null);
    }
}
