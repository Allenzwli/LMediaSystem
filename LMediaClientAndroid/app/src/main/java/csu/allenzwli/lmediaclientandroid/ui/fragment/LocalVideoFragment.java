package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.view.View;
import android.widget.TextView;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoFragment extends BaseLazyFragment {

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_local_common;
    }
}
