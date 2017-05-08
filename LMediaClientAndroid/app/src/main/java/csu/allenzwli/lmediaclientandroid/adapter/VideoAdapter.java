package csu.allenzwli.lmediaclientandroid.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

        holder.mJCVideoPlayerStandard.setUp(video.getFileUrl(),JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,video.getVideoName());
        holder.mJCVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(video.getThumbUrl()));
        return view;
    }

    class VideoViewHolder{
        JCVideoPlayerStandard mJCVideoPlayerStandard;
        public VideoViewHolder(View contentView) {

            mJCVideoPlayerStandard= (JCVideoPlayerStandard) contentView.findViewById(R.id.jc_player);
        }
    }
}
