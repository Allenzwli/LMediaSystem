package csu.allenzwli.lmediaclientandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.model.Video;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.VideoViewHolder> {

    private List<Video> mLocalVideoLists;
    private Context mContext;

    public LocalVideoAdapter(Context context,List<Video> localVideoLists){
        mContext=context;
        mLocalVideoLists=localVideoLists;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new LocalVideoAdapter.VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        final Video video=mLocalVideoLists.get(position);

    }

    @Override
    public int getItemCount() {
        return mLocalVideoLists.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        private TextView videoNameTV;
        private ImageView thumbIV;
        private TextView fileSizeTV;
        private TextView durationTV;

        public VideoViewHolder(View contentView) {
            super(contentView);


        }
    }
}
