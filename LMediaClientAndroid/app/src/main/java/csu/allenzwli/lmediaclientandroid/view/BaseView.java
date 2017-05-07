package csu.allenzwli.lmediaclientandroid.view;

/**
 * Created by allenzwli on 2017/5/7.
 */

public interface BaseView {
    /**
     * show loading message
     */
    void showLoading();

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(String msg);

}
