package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Video;
import csu.lzw.lmediaserver.service.AdminService;
import csu.lzw.lmediaserver.service.VideoService;
import csu.lzw.lmediaserver.util.MediaUtil;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/videos")
public class VideoController {

    private Logger mLogger = Logger.getLogger(VideoController.class);

    @Resource
    private AdminService mAdminService;

    @Resource
    private VideoService mVideoService;

    @RequestMapping("/manage")
    public String videoManageDirect(){
        return "video_manage";
    }

    @RequestMapping("/add")
    public String videoAddDirect(){
        return "video_add";
    }


    @ResponseBody
    @RequestMapping("list")
    public List<Video> getVideosList(@RequestParam(required = false) int page){
        return mVideoService.getVideosPerPage(page);
    }

    @ResponseBody
    @RequestMapping("/get")
    public Map getvideoByStartAndLength(int startIndex,int pageSize,String fuzzy,String orderColumn,String orderDir){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("pageData",mVideoService.getVideosByStartIndexAndLength(fuzzy,orderColumn,orderDir,startIndex,pageSize));
        resultMap.put("total",mVideoService.getVideoAllCount(fuzzy));
        return resultMap;
    }


    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map videoUpload(@RequestParam(value = "songFile", required = false) MultipartFile videoFile, int adminId, String token){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(!mAdminService.validAdminToken(token,adminId)){
            resultMap.put("data",4);
            resultMap.put("msg","上传失败，无效的管理员身份");
        }else{
            if(videoFile!=null&&!videoFile.isEmpty()){
                String originalFileName=videoFile.getOriginalFilename();
                String realPath= StaticConfig.BASE_LOCAL_VIDEO_FILE_PATH+originalFileName;
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
                    video.setFileUrl(StaticConfig.VIDEO_FILE_URL_PREFIX+originalFileName);
                    video.setAdminId(adminId);
                    video.setVideoName(originalFileName);
                    mVideoService.saveVideo(video);
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
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map videoDelete(@RequestParam(value = "needDeleteJsonArray")String needDeleteJsonArray, String token, int adminId){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(!mAdminService.validAdminToken(token,adminId)){
            resultMap.put("data",3);
            resultMap.put("msg","删除失败，无效的管理员身份");
        }else{
            List<Video> needDeleteVideoList=null;
            try {
                needDeleteVideoList=new ObjectMapper().readValue(needDeleteJsonArray,new TypeReference<List<Video>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
            int idArray[]=new int[needDeleteVideoList.size()];
            for(int i=0;i<needDeleteVideoList.size();i++){
                idArray[i]=needDeleteVideoList.get(i).getId();
                File file=new File(StaticConfig.BASE_LOCAL_VIDEO_FILE_PATH+needDeleteVideoList.get(i).getVideoName());
                if(file.exists())
                    file.delete();
            }
            mVideoService.deleteVideos(idArray);
            resultMap.put("data",0);
        }
        return resultMap;
    }
}
