package csu.allenzwli.lmediaclientandroid.interactor.imp;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.CommonInteractor;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.util.ThumbUtil;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoInteractorImp implements CommonInteractor {

    private LoadResultCallBack<List<Video>> mLoadResultListener;
    private Context mContext;

    public LocalVideoInteractorImp(Context context,LoadResultCallBack<List<Video>> listener){
        mContext=context;
        mLoadResultListener=listener;

    }


    @Override
    public void getCommonListData() {
        new GetLocalVideoTask().execute();
    }

    public class GetLocalVideoTask extends AsyncTask<Void,Void,Void> {

        private List<Video> videoList;
        @Override
        protected Void doInBackground(Void... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            Video video=null;
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                video=new Video();
                String title = cursor.getString((cursor
                        .getColumnIndex(MediaStore.Video.Media.TITLE)));
                long size = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Video.Media.SIZE));
                long duration = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Video.Media.DURATION));
                String url = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Video.Media.DATA));
                video.setDuration(duration);
                video.setFileSize(size);
                video.setVideoName(title);
                video.setFileUrl(url);
                String realThumbPicPath=mContext.getApplicationContext().getCacheDir().getAbsolutePath()+"/"+title+"_"+size+".jpg";
                ThumbUtil.saveVideoThumbBitmap(video.getFileUrl(),realThumbPicPath);
                video.setThumbUrl(realThumbPicPath);
                videoList.add(video);
            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPreExecute() {
            videoList=new ArrayList<Video>();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(videoList.size()>0){
                mLoadResultListener.onSuccess(0,videoList);
            }else{
                mLoadResultListener.onError(1,"空空如也");
            }
        }
    }
}
