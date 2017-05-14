package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Admin;
import csu.lzw.lmediaserver.pojo.Video;
import csu.lzw.lmediaserver.service.AdminService;
import csu.lzw.lmediaserver.service.VideoService;
import csu.lzw.lmediaserver.util.Base64Util;
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
import javax.servlet.http.HttpServletRequest;
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
    public String videoManageDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return "login";
        }
        return "video_manage";
    }

    @RequestMapping("/add")
    public String videoAddDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return "login";
        }return "video_add";
    }


    @ResponseBody
    @RequestMapping("list")
    public List<Video> getVideosList(){
        return mVideoService.getAllVideos();
    }

    @ResponseBody
    @RequestMapping("/get")
    public Map getvideoByStartAndLength(int startIndex,int pageSize,String fuzzy,String orderColumn,String orderDir){
        return mVideoService.getVideosByStartIndexAndLength(fuzzy,orderColumn,orderDir,startIndex,pageSize);
    }


    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map videoUpload(@RequestParam(value = "songFile", required = false) MultipartFile videoFile, int adminId, String token){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(!mAdminService.validAdminToken(token,adminId)){
            resultMap.put("data",4);
            resultMap.put("msg","上传失败，无效的管理员身份");
        }else{
            resultMap=mVideoService.saveVideo(videoFile,adminId);
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
            resultMap=mVideoService.deleteVideos(needDeleteJsonArray);
        }
        return resultMap;
    }
}
