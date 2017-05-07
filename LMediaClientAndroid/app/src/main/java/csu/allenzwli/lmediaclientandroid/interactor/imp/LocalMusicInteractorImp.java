package csu.allenzwli.lmediaclientandroid.interactor.imp;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.callback.LoadResultCallBack;
import csu.allenzwli.lmediaclientandroid.interactor.CommonInteractor;
import csu.allenzwli.lmediaclientandroid.model.Song;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class LocalMusicInteractorImp implements CommonInteractor {

    private LoadResultCallBack<List<Song>> mLoadResultListener;
    private Context mContext;

    public LocalMusicInteractorImp(LoadResultCallBack<List<Song>> listener,Context context){
        mLoadResultListener=listener;
        mContext=context;
    }

    @Override
    public void getCommonListData() {
        new GetLocalMusicTask().execute();
    }

    public class GetLocalMusicTask extends AsyncTask<Void,Void,Void>{

        private List<Song> musicList;
        @Override
        protected Void doInBackground(Void... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            Song mp3Info=null;
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                mp3Info = new Song();
                String title = cursor.getString((cursor
                        .getColumnIndex(MediaStore.Audio.Media.TITLE)));
                String artist = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                /*long size = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Audio.Media.SIZE));*/
                long albumId = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long duration = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DURATION));
                String url = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DATA));
                int isMusic = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                if (isMusic != 0) {
                    mp3Info.setSongName(title);
                    mp3Info.setArtist(artist);
                    mp3Info.setAlbum(album);
                    mp3Info.setDuration(duration);
                    mp3Info.setFileUrl(url);
                    mp3Info.setPictureUrl(ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId).toString());
                    musicList.add(mp3Info);
                }
            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPreExecute() {
            musicList=new ArrayList<Song>();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(musicList.size()>0){
                mLoadResultListener.onSuccess(0,musicList);
            }else{
                mLoadResultListener.onError(1,"空空如也");
            }
        }
    }
}
