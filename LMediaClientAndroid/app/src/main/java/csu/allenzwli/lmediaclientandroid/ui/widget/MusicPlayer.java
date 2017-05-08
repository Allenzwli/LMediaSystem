/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package csu.allenzwli.lmediaclientandroid.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;

import java.util.List;
import java.util.Random;

import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.util.Constants;
import csu.allenzwli.lmediaclientandroid.util.MusicPlayState;
import csu.allenzwli.lmediaclientandroid.util.PlayListSingleton;
import csu.allenzwli.lmediaclientandroid.util.TLog;

public class MusicPlayer implements OnCompletionListener, OnErrorListener, OnBufferingUpdateListener, OnPreparedListener {

    private final static String TAG = MusicPlayer.class.getSimpleName();

    private final static long SLEEP_TIME = 1000;

    private MediaPlayer mMediaPlayer;

    private List<Song> mMusicList;

    private int mCurPlayIndex;

    private int mPlayState;

    private Context mContext;

    private int mBufferProgress;

    public MusicPlayer(Context context) {
        initParameter(context);
    }

    public List<Song> getmMusicList() {
        return mMusicList;
    }

    private void initParameter(Context context) {
        mContext = context;

        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        //mMusicList = new ArrayList<Song>();
        mMusicList= PlayListSingleton.getInstance().getApplicationSongList();
        mPlayState = MusicPlayState.MPS_LIST_EMPTY;
    }

    public void exit() {
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMusicList.clear();
        mCurPlayIndex = -1;
        mPlayState = MusicPlayState.MPS_LIST_EMPTY;
    }


    public int getMusicListCount() {
        return null == mMusicList || mMusicList.isEmpty() ? 0 : mMusicList.size();
    }

    public int getPlayState() {
        return mPlayState;
    }

    public void play() {
        preparedMusic(mCurPlayIndex);
    }

    public void play(int position) {
        preparedMusic(position);
    }

    public void replay() {
        if (mPlayState == MusicPlayState.MPS_LIST_EMPTY) {
            return;
        }

        mMediaPlayer.start();
        mPlayState = MusicPlayState.MPS_PLAYING;
        sendPlayCurrentPosition();
    }

    public void pause() {
        if (mPlayState != MusicPlayState.MPS_PLAYING) {
            return;
        }

        mMediaPlayer.pause();
        mPlayState = MusicPlayState.MPS_PAUSE;
    }

    public void stop() {
        if (mPlayState != MusicPlayState.MPS_PLAYING && mPlayState != MusicPlayState.MPS_PAUSE) {
            return;
        }

        mMediaPlayer.stop();
        mPlayState = MusicPlayState.MPS_STOP;
    }

    public void playNext() {
        mCurPlayIndex++;
        mCurPlayIndex = reviceIndex(mCurPlayIndex);

        preparedMusic(mCurPlayIndex);
    }

    public void playPrev() {
        mCurPlayIndex--;
        mCurPlayIndex = reviceIndex(mCurPlayIndex);

        preparedMusic(mCurPlayIndex);
    }

    public void seekTo(int progress) {
        if(mMusicList.get(mCurPlayIndex).getFileUrl().startsWith("http")&&mBufferProgress<progress) {
            return;
        }
        mMediaPlayer.seekTo(progress);
    }

    public int getCurPosition() {
        if (mPlayState == MusicPlayState.MPS_PLAYING || mPlayState == MusicPlayState.MPS_PAUSE) {
            return mMediaPlayer.getCurrentPosition();
        }

        return 0;
    }

    public int getDuration() {
        if (mPlayState == MusicPlayState.MPS_LIST_EMPTY) {
            return 0;
        }

        return mMediaPlayer.getDuration();
    }


    private int reviceIndex(int index) {
        if (index < 0) {
            index = mMusicList.size() - 1;
        }

        if (index >= mMusicList.size()) {
            index = 0;
        }

        return index;
    }

    private int reviceSeekValue(int value) {
        if (value < 0) {
            value = 0;
        }

        if (value > 100) {
            value = 100;
        }

        return value;
    }

    private void preparedMusic(int index) {
        if (index < 0 || index >= getMusicListCount()) {
            return;
        }

        mCurPlayIndex = index;

        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMusicList=PlayListSingleton.getInstance().getApplicationSongList();
        String dataSource = mMusicList.get(mCurPlayIndex).getFileUrl();
        PlayListSingleton.getInstance().setmCurrentSongUrl(dataSource);
        PlayListSingleton.getInstance().setmCurrentSongIndex(index);

        try {
            mMediaPlayer.setDataSource(dataSource);
            mMediaPlayer.prepareAsync();
            mPlayState = MusicPlayState.MPS_PREPARED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mCurPlayIndex != mMusicList.size() - 1) {
            playNext();
        } else {
            stop();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        TLog.e(TAG, "MusicPlayer		onError!!!\n");
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        TLog.d(TAG, "second percent --> " + percent);
        //if (percent < 100) {
            mBufferProgress=getDuration()*percent/100;
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_MUSIC_SECOND_PROGRESS_BROADCAST);
            intent.putExtra(Constants.KEY_MUSIC_SECOND_PROGRESS, percent);
            intent.putExtra(Constants.KEY_MUSIC_TOTAL_DURATION, getDuration());
            mContext.sendBroadcast(intent);
        //}
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
        mPlayState = MusicPlayState.MPS_PLAYING;
        sendPlayBundle();
        sendPlayCurrentPosition();
    }

    private void sendPlayBundle() {
        Intent intent = new Intent(Constants.ACTION_MUSIC_BUNDLE_BROADCAST);
        Bundle extras = new Bundle();
        extras.putInt(Constants.KEY_MUSIC_TOTAL_DURATION, getDuration());
        extras.putSerializable(Constants.KEY_MUSIC_PARCELABLE_DATA,mMusicList.get(mCurPlayIndex));
        intent.putExtras(extras);
        //TLog.v("sendPlayBundle",mMusicList.get(mCurPlayIndex).getFileUrl()+":"+getDuration());
        mContext.sendBroadcast(intent);
    }

    private void sendPlayCurrentPosition() {
        new Thread() {
            public void run() {
                Intent intent = new Intent(Constants.ACTION_MUSIC_CURRENT_PROGRESS_BROADCAST);
                while (mMediaPlayer.isPlaying()) {
                    intent.putExtra(Constants.KEY_MUSIC_CURRENT_DUTATION, getCurPosition());
                    mContext.sendBroadcast(intent);
                    try {
                        sleep(SLEEP_TIME);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
