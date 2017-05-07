package csu.allenzwli.lmediaclientandroid.interactor.imp;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.CommonInteractor;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.util.TLog;
import csu.allenzwli.lmediaclientandroid.util.ThumbUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineVideoInteractorImp implements CommonInteractor {

    private LoadResultCallBack<List<Video>> mLoadResultListener;
    private Context mContext;

    public OnlineVideoInteractorImp(Context context,LoadResultCallBack<List<Video>> listener){
        mContext=context;
        mLoadResultListener=listener;
    }

    @Override
    public void getCommonListData() {
        new GetOnlineVideoListTask().execute();
    }

    public class GetOnlineVideoListTask extends AsyncTask<Void,Void,Void> {

        private List<Video> videoList;

        @Override
        protected Void doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder()
                    .url(ApiConstants.ApiUrl.ONLINE_VIDEO_LIST)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                TLog.v("response",response.body().toString());
                videoList=new Gson().fromJson(response.body().string(), new TypeToken<List<Video>>(){}.getType());
                for (Video video:videoList) {
                    String realThumbPicPath=mContext.getApplicationContext().getCacheDir().getAbsolutePath()+"/"+video.getVideoName()+"_"+video.getFileSize()+".jpg";
                    ThumbUtil.saveVideoThumbBitmap(video.getFileUrl(),realThumbPicPath);
                    video.setThumbUrl(realThumbPicPath);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            videoList=new ArrayList<Video>();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(videoList!=null&&videoList.size()>0){
                mLoadResultListener.onSuccess(0,videoList);
            }else{
                mLoadResultListener.onError(1,"暂无视频");
            }
        }
    }
}
