package csu.allenzwli.lmediaclientandroid.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.util.ATEUtil;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class VideoAdapter extends BaseAdapter {

    private List<Video> mLocalVideoLists;
    private Context mContext;

    public VideoAdapter(Context context){
        mContext=context;
        mLocalVideoLists=new ArrayList<Video>();
    }

    public List<Video> getmLocalVideoLists() {
        return mLocalVideoLists;
    }

    @Override
    public int getCount() {
        return mLocalVideoLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mLocalVideoLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mLocalVideoLists.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VideoViewHolder holder=null;
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_video, null, false);
            holder=new VideoViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (VideoViewHolder) view.getTag();
        }
        final Video video=mLocalVideoLists.get(i);

        holder.mJCVideoPlayerStandard.setUp(video.getFileUrl(),JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"");
        holder.videoNameTV.setText(video.getVideoName());
        holder.mJCVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(video.getThumbUrl()));
        if(video.getFileUrl().startsWith("http")){
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new MaterialDialog.Builder(mContext)
                            .content("是否要下载视频: "+video.getVideoName())
                            .positiveText("是")
                            .negativeText("否")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(video.getFileUrl()));
                                    String fileDir= Environment.getExternalStorageDirectory().getAbsolutePath();
                                    File file=new File(fileDir+"/LMediaClient");
                                    if(!file.exists())
                                        file.mkdirs();
                                    fileDir=file.getAbsolutePath();
                                    String fileName="L-"+video.getVideoName()+"_"+video.getFileSize()+".mp4";
                                    file=new File(fileDir,fileName);
                                    if(file.exists()) {
                                        Snackbar.make(((Activity) mContext).getWindow().getDecorView(), "文件已存在，不需要重新下载", Snackbar.LENGTH_SHORT).show();
                                        return;
                                    }else {
                                        Snackbar.make(((Activity) mContext).getWindow().getDecorView(), "开始创建下载任务: "+video.getFileUrl()+"\n下拉通知栏查看下载进度", Snackbar.LENGTH_SHORT).show();
                                    }
                                    request.setDestinationInExternalPublicDir(fileDir,fileName);
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
                    return true;
                }
            });
        }
        return view;
    }

    class VideoViewHolder{
        JCVideoPlayerStandard mJCVideoPlayerStandard;
        TextView videoNameTV;
        CardView cardView;
        public VideoViewHolder(View contentView) {
            cardView= (CardView) contentView.findViewById(R.id.card);
            videoNameTV= (TextView) contentView.findViewById(R.id.videoNameTV);
            mJCVideoPlayerStandard= (JCVideoPlayerStandard) contentView.findViewById(R.id.jc_player);
        }
    }
}
