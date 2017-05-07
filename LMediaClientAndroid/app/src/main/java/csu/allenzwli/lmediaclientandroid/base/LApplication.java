package csu.allenzwli.lmediaclientandroid.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by allenzwli on 2017/5/8.
 */

public class LApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    public static Context getContext() {
        return mContext;
    }
}
