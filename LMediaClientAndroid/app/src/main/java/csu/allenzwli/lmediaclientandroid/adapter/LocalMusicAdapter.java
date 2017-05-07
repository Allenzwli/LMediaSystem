package csu.allenzwli.lmediaclientandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.util.ATEUtil;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.MusicViewHolder>{

    private Context mContext;
    private List<Song> mSongBeanLists;

    public LocalMusicAdapter(Context context,List<Song> songList){
        mContext=context;
        mSongBeanLists=songList;
    }
    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        final Song song = mSongBeanLists.get(position);
        holder.songNameTV.setText(song.getSongName());
        holder.artistTV.setText(song.getArtist());
        holder.albumTV.setText(song.getAlbum());
        Glide.with(holder.itemView.getContext()).load(song.getPictureUrl())
                .error(ATEUtil.getDefaultAlbumDrawable(mContext))
                .placeholder(ATEUtil.getDefaultAlbumDrawable(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(holder.albumIV);
    }

    @Override
    public int getItemCount() {
        return mSongBeanLists.size();
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {

        private TextView songNameTV;
        private ImageView albumIV;
        private TextView artistTV;
        private TextView albumTV;

        public MusicViewHolder(View contentView) {
            super(contentView);
            songNameTV= (TextView) contentView.findViewById(R.id.songNameTV);
            artistTV= (TextView) contentView.findViewById(R.id.artistTV);
            albumTV= (TextView) contentView.findViewById(R.id.albumTV);
            albumIV= (ImageView) contentView.findViewById(R.id.albumIV);

        }
    }
}
