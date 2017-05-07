package csu.allenzwli.lmediaclientandroid.interactor.imp;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.CommonInteractor;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.util.RequestUtil;
import csu.allenzwli.lmediaclientandroid.util.TLog;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineMusicInteractorImp implements CommonInteractor {

    private LoadResultCallBack<List<Song>> mLoadResultListener;
    private Context mContext;

    public OnlineMusicInteractorImp(Context context,LoadResultCallBack<List<Song>> listener){
        mContext=context;
        mLoadResultListener=listener;
    }

    @Override
    public void getCommonListData() {

        RequestUtil.addRequest(new StringRequest(Request.Method.GET, ApiConstants.ApiUrl.ONLINE_MUSIC_LIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                List<Song> musicLists = new Gson().fromJson(arg0, new TypeToken<List<Song>>(){}.getType());
                if(musicLists!=null&&musicLists.size()>0){
                    mLoadResultListener.onSuccess(0,musicLists);
                }else{
                    mLoadResultListener.onError(1,"暂无歌曲");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                mLoadResultListener.onError(2,"请求歌曲列表失败");
            }
        }),getClass().toString());

    }
}
