package csu.allenzwli.lmediaclientandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.util.ATEUtil;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class MusicAdapter extends BaseAdapter{

    private Context mContext;
    private List<Song> mSongBeanLists;

    public MusicAdapter(Context context){
        mContext=context;
        mSongBeanLists=new ArrayList<Song>();
    }

    public List<Song> getmSongBeanLists() {
        return mSongBeanLists;
    }

    @Override
    public int getCount() {
        return mSongBeanLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mSongBeanLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mSongBeanLists.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MusicViewHolder holder=null;
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_music, null, false);
            holder=new MusicViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (MusicViewHolder) view.getTag();
        }
        final Song song = mSongBeanLists.get(i);
        holder.songNameTV.setText(song.getSongName());
        holder.artistTV.setText(song.getArtist());
        holder.albumTV.setText(song.getAlbum());
        Glide.with(mContext).load(song.getPictureUrl())
                .error(ATEUtil.getDefaultAlbumDrawable(mContext))
                .placeholder(ATEUtil.getDefaultAlbumDrawable(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(holder.albumIV);
        return view;
    }

    class MusicViewHolder {
        TextView songNameTV;
        ImageView albumIV;
        TextView artistTV;
        TextView albumTV;

        public MusicViewHolder(View contentView) {
            songNameTV= (TextView) contentView.findViewById(R.id.songNameTV);
            artistTV= (TextView) contentView.findViewById(R.id.artistTV);
            albumTV= (TextView) contentView.findViewById(R.id.albumTV);
            albumIV= (ImageView) contentView.findViewById(R.id.albumIV);
        }
    }
}
