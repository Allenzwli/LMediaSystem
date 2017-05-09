package csu.allenzwli.lmediaclientandroid.ui.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.util.List;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.adapter.MusicAdapter;
import csu.allenzwli.lmediaclientandroid.base.BaseLazyFragment;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.MusicPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.OnlineMusicPresenterImp;
import csu.allenzwli.lmediaclientandroid.ui.MusicPlayerActivity;
import csu.allenzwli.lmediaclientandroid.util.ApiConstants;
import csu.allenzwli.lmediaclientandroid.util.MusicPlayState;
import csu.allenzwli.lmediaclientandroid.util.PlayListSingleton;
import csu.allenzwli.lmediaclientandroid.view.OnlineMusicView;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class OnlineMusicFragment extends BaseLazyFragment implements OnlineMusicView,SwipeRefreshLayout.OnRefreshListener,
                                                                            AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    @InjectView(R.id.list_view)
    ListView mListView;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.loading)
    RotateLoading loading;

    private MusicAdapter mOnlineMusicAdapter;

    private MusicPresenter mOnlineMusicPresenter;

    @Override
    protected void onFirstUserVisible() {
        mOnlineMusicPresenter=new OnlineMusicPresenterImp(mContext,this);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mOnlineMusicPresenter.loadMusicListData(false);
                }
            }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mOnlineMusicAdapter=new MusicAdapter(mContext);
        mListView.setAdapter(mOnlineMusicAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    @Override
    public void showLoading() {
        loading.start();
    }

    @Override
    public void hideLoading() {
        loading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshMusicListData(List<Song> songBeanLists) {
        if(songBeanLists!=null&&songBeanLists.size()>0){
            mOnlineMusicAdapter.getmSongBeanLists().clear();
            mOnlineMusicAdapter.getmSongBeanLists().addAll(songBeanLists);
            mOnlineMusicAdapter.notifyDataSetChanged();
            PlayListSingleton.getInstance().refreshSongList(songBeanLists);
        }
    }

    @Override
    public void onRefresh() {
        mOnlineMusicPresenter.loadMusicListData(true);
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_common;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mOnlineMusicPresenter.onItemClickListener(i,mOnlineMusicAdapter.getmSongBeanLists().get(i));
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }


    @Override
    public void navigateToLocalMusicItem(int position, Song songBean) {
        PlayListSingleton.getInstance().refreshSongList(mOnlineMusicAdapter.getmSongBeanLists());
        Intent intent=new Intent(mContext,MusicPlayerActivity.class);
        intent.putExtra(MusicPlayState.PLAYING_SONG,mOnlineMusicAdapter.getmSongBeanLists().get(position));
        intent.putExtra(MusicPlayState.PLAYING_INDEX,position);
        startActivity(intent);
    }

    @Override
    public void showMaterialDialogWhenItemLongClick(int position, final Song song) {
        //showToast(song.getFileUrl());
        new MaterialDialog.Builder(mContext)
                .content("是否要下载歌曲: "+song.getSongName())
                .positiveText("是")
                .negativeText("否")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(song.getFileUrl()));
                        String fileDir= Environment.getExternalStorageDirectory().getAbsolutePath();
                        File file=new File(fileDir+"/LMediaClient");
                        if(!file.exists())
                            file.mkdirs();
                        fileDir=file.getAbsolutePath();
                        String fileName="L-"+song.getSongName()+"_"+song.getArtist()+"_"+song.getAlbum()+".mp3";
                        file=new File(fileDir,fileName);
                        if(file.exists()) {
                            showToast("文件已存在，不需要重新下载");
                            return;
                        }else {
                            showToast("开始创建下载任务:"+song.getFileUrl()+"\n下拉通知栏查看下载进度");
                        }
                        request.setDestinationInExternalPublicDir(fileDir,fileName );
                        DownloadManager downloadManager= (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                        downloadManager.enqueue(request);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        mOnlineMusicPresenter.OnItemLongClickListner(i,mOnlineMusicAdapter.getmSongBeanLists().get(i));
        return true;
    }

}
