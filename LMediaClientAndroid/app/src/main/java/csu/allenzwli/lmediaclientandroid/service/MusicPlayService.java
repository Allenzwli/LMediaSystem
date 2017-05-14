package csu.allenzwli.lmediaclientandroid.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import csu.allenzwli.lmediaclientandroid.ui.widget.MusicPlayer;
import csu.allenzwli.lmediaclientandroid.util.Constants;
import csu.allenzwli.lmediaclientandroid.util.MusicPlayState;
import csu.allenzwli.lmediaclientandroid.util.TLog;

public class MusicPlayService extends Service {

    private static MusicPlayer mPlayer = null;
    private PlayBroadCastReceiver mBroadCastReceiver = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MusicPlayer(this);

        mBroadCastReceiver = new PlayBroadCastReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicPlayState.ACTION_MUSIC_NEXT);
        filter.addAction(MusicPlayState.ACTION_MUSIC_PAUSE);
        filter.addAction(MusicPlayState.ACTION_MUSIC_PLAY);
        filter.addAction(MusicPlayState.ACTION_MUSIC_PREV);
        filter.addAction(MusicPlayState.ACTION_MUSIC_REPLAY);
        filter.addAction(MusicPlayState.ACTION_MUSIC_STOP);
        filter.addAction(MusicPlayState.ACTION_EXIT);
        filter.addAction(MusicPlayState.ACTION_SEEK_TO);

        registerReceiver(mBroadCastReceiver, filter);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        int index=intent.getIntExtra(MusicPlayState.PLAYING_INDEX,0);
        //TLog.d("4444444444444","4,,,,,,,"+index);
        mPlayer.play(index);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private class PlayBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicPlayState.ACTION_MUSIC_NEXT)) {
                mPlayer.playNext();
            } else if (action.equals(MusicPlayState.ACTION_MUSIC_PAUSE)) {
                mPlayer.pause();
            } else if (action.equals(MusicPlayState.ACTION_MUSIC_PLAY)) {
                mPlayer.play();
            } else if (action.equals(MusicPlayState.ACTION_MUSIC_PREV)) {
                mPlayer.playPrev();
            } else if (action.equals(MusicPlayState.ACTION_MUSIC_REPLAY)) {
                mPlayer.replay();
            } else if (action.equals(MusicPlayState.ACTION_MUSIC_STOP)) {
                mPlayer.stop();
            } else if (action.equals(MusicPlayState.ACTION_EXIT)) {
                mPlayer.exit();
            } else if (action.equals(MusicPlayState.ACTION_SEEK_TO)) {
                int progress = intent.getIntExtra(Constants.KEY_PLAYER_SEEK_TO_PROGRESS, 0);
                TLog.v("SEEK",progress+"");
                mPlayer.seekTo(progress);
            }
        }

    }
}
