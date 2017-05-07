package csu.allenzwli.lmediaclientandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.model.Video;
import csu.allenzwli.lmediaclientandroid.util.ATEUtil;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> mLocalVideoLists;
    private Context mContext;

    public VideoAdapter(Context context, List<Video> localVideoLists){
        mContext=context;
        mLocalVideoLists=localVideoLists;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoAdapter.VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        final Video video=mLocalVideoLists.get(position);
        holder.videoNameTV.setText(video.getVideoName());
        holder.fileSizeTV.setText(Formatter.formatFileSize(mContext, Long.valueOf(video.getFileSize())));
        Glide.with(holder.itemView.getContext()).load(video.getThumbUrl())
                .error(ATEUtil.getDefaultAlbumDrawable(mContext))
                .placeholder(ATEUtil.getDefaultAlbumDrawable(mContext))
                .into(holder.thumbIV);
    }

    @Override
    public int getItemCount() {
        return mLocalVideoLists.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        private TextView videoNameTV;
        private TextView fileSizeTV;
        private ImageView thumbIV;

        public VideoViewHolder(View contentView) {
            super(contentView);
            videoNameTV= (TextView) contentView.findViewById(R.id.videoNameTV);
            fileSizeTV= (TextView) contentView.findViewById(R.id.fileSizeTV);
            thumbIV= (ImageView) contentView.findViewById(R.id.thumbIV);
        }
    }
}
