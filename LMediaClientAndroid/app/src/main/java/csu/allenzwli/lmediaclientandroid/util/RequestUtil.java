package csu.allenzwli.lmediaclientandroid.util;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import csu.allenzwli.lmediaclientandroid.BuildConfig;
import csu.allenzwli.lmediaclientandroid.base.LApplication;

/**
 * Created by allenzwli on 2017/5/8.
 */

public class RequestUtil {
    public static final int OUT_TIME = 10000;
    public static final int TIMES_OF_RETRY = 1;

    public static RequestQueue mRequestQueue = Volley.newRequestQueue(LApplication.getContext());

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        //给每个请求重设超时、重试次数
        request.setRetryPolicy(new DefaultRetryPolicy(
                OUT_TIME,
                TIMES_OF_RETRY,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);

        if (BuildConfig.DEBUG) {
            TLog.d("VolleyRequestUtil",request.getUrl());
        }

    }

    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

}
