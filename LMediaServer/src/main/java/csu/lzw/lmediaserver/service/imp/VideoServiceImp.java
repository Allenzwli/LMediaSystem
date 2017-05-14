package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.VideoMapper;
import csu.lzw.lmediaserver.pojo.Video;
import csu.lzw.lmediaserver.service.VideoService;
import csu.lzw.lmediaserver.util.Base64Util;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allenzwli on 2017/5/6.
 */
@Service("VideoService")
@Transactional(rollbackFor = Exception.class)
public class VideoServiceImp implements VideoService{
    @Resource
    private VideoMapper mVideoMapper;



    public Map saveVideo(MultipartFile videoFile, int adminId) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        if(videoFile!=null&&!videoFile.isEmpty()){
            String originalFileName=videoFile.getOriginalFilename().trim();
            String savedFileName= Base64Util.getBase64(originalFileName);
            String realPath= StaticConfig.BASE_LOCAL_VIDEO_FILE_PATH+savedFileName;
            String videoType=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
            if(videoType.equals(".mp4")) {
                File file = new File(realPath);
                try {
                    videoFile.transferTo(file);
                } catch (IOException e) {
                    //e.printStackTrace();
                    resultMap.put("data",3);
                    resultMap.put("msg",e.toString());
                }
                Video video=new Video();
                video.setFileSize(file.length());
                video.setFileUrl(StaticConfig.VIDEO_FILE_URL_PREFIX+savedFileName);
                video.setAdminId(adminId);
                video.setVideoName(originalFileName);
                mVideoMapper.saveVideo(video);
                resultMap.put("data",0);
                resultMap.put("msg","视频:"+originalFileName+" 已上传成功");
            } else {
                resultMap.put("data",1);
                resultMap.put("msg","上传失败，文件类型应为mp4");
            }
        }else{
            resultMap.put("data",2);
            resultMap.put("msg","上传失败，上传的文件不能为空");
        }
        return resultMap;
    }

    public List<Video> getVideosPerPage(int page) {
        return mVideoMapper.getVideosByStartAndLength(StaticConfig.VIDEO_PER_PAGE_SIZE*(page-1),StaticConfig.VIDEO_PER_PAGE_SIZE,null,null,null);
    }

    public Map getVideosByStartIndexAndLength(String fuzzy, String orderCol, String orderDir, int startIndex, int length) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("pageData",mVideoMapper.getVideosByStartAndLength(startIndex,length,fuzzy,orderCol,orderDir));
        resultMap.put("total",mVideoMapper.getVideosAllCount(fuzzy));
        return resultMap;
    }



    public Map deleteVideos(String needDeleteJsonArray) {
        Map<String,Object>resultMap=new HashMap<String,Object>();
        List<Video> needDeleteVideoList=null;
        try {
            needDeleteVideoList=new ObjectMapper().readValue(needDeleteJsonArray,new TypeReference<List<Video>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("data",1);
            resultMap.put("msg","删除失败，json转换失败");
        }
        int idArray[]=new int[needDeleteVideoList.size()];
        for(int i=0;i<needDeleteVideoList.size();i++){
            idArray[i]=needDeleteVideoList.get(i).getId();
            File file=new File(StaticConfig.BASE_LOCAL_VIDEO_FILE_PATH+needDeleteVideoList.get(i).getVideoName());
            if(file.exists()) {
                file.delete();
            }
            else{
                resultMap.put("data",2);
                resultMap.put("msg","文件已不在服务器上");
            }
        }
        mVideoMapper.deleteVideos(idArray);
        resultMap.put("data",0);
        resultMap.put("msg","删除成功");
        return resultMap;
    }

    public List<Video> getAllVideos() {
        return mVideoMapper.getAllVideos();
    }
}
