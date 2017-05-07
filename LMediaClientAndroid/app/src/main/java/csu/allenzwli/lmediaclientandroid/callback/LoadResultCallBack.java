package csu.allenzwli.lmediaclientandroid.callback;

/**
 * Created by allenzwli on 2017/5/7.
 */

public interface LoadResultCallBack <T> {

    void onSuccess(int code, T data);

    void onError(int code,String msg);

}

