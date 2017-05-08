package csu.allenzwli.lmediaclientandroid.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.MemoryCache;

import butterknife.InjectView;
import csu.allenzwli.lmediaclientandroid.R;
import csu.allenzwli.lmediaclientandroid.base.BaseActivity;
import csu.allenzwli.lmediaclientandroid.model.Song;
import csu.allenzwli.lmediaclientandroid.presenter.MusicPlayerPresenter;
import csu.allenzwli.lmediaclientandroid.presenter.imp.MusicPlayerPresenterImp;
import csu.allenzwli.lmediaclientandroid.service.MusicPlayService;
import csu.allenzwli.lmediaclientandroid.ui.widget.PlayerSeekBar;
import csu.allenzwli.lmediaclientandroid.util.CommonUtils;
import csu.allenzwli.lmediaclientandroid.util.Constants;
import csu.allenzwli.lmediaclientandroid.util.MusicPlayState;
import csu.allenzwli.lmediaclientandroid.util.PlayListSingleton;
import csu.allenzwli.lmediaclientandroid.util.TLog;
import csu.allenzwli.lmediaclientandroid.view.MusicPlayerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MusicPlayerActivity extends BaseActivity implements MusicPlayerView{

    @InjectView(R.id.musics_player_background)
    ImageView mBackgroundImage;

    @InjectView(R.id.musics_player_disc_view)
    CircleImageView mPlayerDiscView;

    @InjectView(R.id.musics_player_play_ctrl_btn)
    ImageView mPlayerCtrlBtn;

    @InjectView(R.id.musics_player_play_next_btn)
    ImageView mPlayerNextBtn;

    @InjectView(R.id.musics_player_play_prev_btn)
    ImageView mPlayerPrevBtn;

    @InjectView(R.id.musics_player_seekbar)
    PlayerSeekBar mPlayerSeekBar;

    @InjectView(R.id.musics_player_name)
    TextView mTitle;

    @InjectView(R.id.musics_player_songer_name)
    TextView mSonger;

    @InjectView(R.id.musics_player_current_time)
    TextView mCurrentTime;

    @InjectView(R.id.musics_player_total_time)
    TextView mTotalTime;

    @InjectView(R.id.volume_seek)
    SeekBar mVolumeSeek;

    private MusicPlayerPresenter mMusicsPresenter;
    private boolean isPlaying = true;
    private static final int BLUR_RADIUS = 100;

    private PlayBundleBroadCast mBundleBroadCast;
    private PlayPositionBroadCast mPositionBroadCast;
    private PlaySecondProgressBroadCast mSecondProgressBroadCast;

    private MyVolumeReceiver mVolumeReceiver;

    private Song mCurrentSong;

    private AudioManager mAudioManager;

    @Override
    protected void initViewsAndEvents() {

        mBundleBroadCast = new PlayBundleBroadCast();
        IntentFilter bundleFilter = new IntentFilter();
        bundleFilter.addAction(Constants.ACTION_MUSIC_BUNDLE_BROADCAST);
        registerReceiver(mBundleBroadCast, bundleFilter);

        mPositionBroadCast = new PlayPositionBroadCast();
        IntentFilter posFilter = new IntentFilter();
        posFilter.addAction(Constants.ACTION_MUSIC_CURRENT_PROGRESS_BROADCAST);
        registerReceiver(mPositionBroadCast, posFilter);

        mSecondProgressBroadCast = new PlaySecondProgressBroadCast();
        IntentFilter secondProgressFilter = new IntentFilter();
        secondProgressFilter.addAction(Constants.ACTION_MUSIC_SECOND_PROGRESS_BROADCAST);
        registerReceiver(mSecondProgressBroadCast, secondProgressFilter);

        registerVolumeReceiver();

        mMusicsPresenter=new MusicPlayerPresenterImp(this,this);
        //Bitmap bitmap = ImageBlurUtil.BoxBlurFilterBitmap(BitmapFactory.decodeResource(getResources(),
         //       R.drawable.player_bg));
        //mBackgroundImage.setImageBitmap(bitmap);

        mPlayerCtrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mMusicsPresenter.onPausePlay();
                } else {
                    mMusicsPresenter.onRePlay();
                }
            }
        });

        mPlayerNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicsPresenter.onNextClick();
            }
        });

        mPlayerPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicsPresenter.onPrevClick();
            }
        });

        mPlayerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMusicsPresenter.seekTo(seekBar.getProgress());
            }
        });
        mCurrentSong= (Song) getIntent().getSerializableExtra(MusicPlayState.PLAYING_SONG);
        int pos=getIntent().getIntExtra(MusicPlayState.PLAYING_INDEX,0);
        if(mCurrentSong!=null){
            if(!mCurrentSong.getFileUrl().equals(PlayListSingleton.getInstance().getmCurrentSongUrl())){
                Intent intent=new Intent(this, MusicPlayService.class);
                intent.putExtra(MusicPlayState.PLAYING_INDEX,pos);
                startService(intent);
                mPlayerSeekBar.setProgress(0);
            }else{
                mMusicsPresenter.refreshPageInfo(mCurrentSong,(int)mCurrentSong.getDuration());
            }
        }else{
        }

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mVolumeSeek.setMax(maxVolume);
        mVolumeSeek.setProgress(currentVolume);
        mVolumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0); //tempVolume:音量绝对值
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_music_player;
    }

    @Override
    public void rePlayMusic() {
        isPlaying = true;
        mPlayerCtrlBtn.setImageResource(R.drawable.btn_pause_selector);
        sendBroadcast(new Intent(MusicPlayState.ACTION_MUSIC_REPLAY));
    }

    @Override
    public void startPlayMusic() {
        isPlaying = true;
        mPlayerCtrlBtn.setImageResource(R.drawable.btn_pause_selector);
        Intent intent=new Intent(MusicPlayState.ACTION_MUSIC_PLAY);
        sendBroadcast(intent);
    }

    @Override
    public void stopPlayMusic() {
        isPlaying = false;
        mPlayerCtrlBtn.setImageResource(R.drawable.btn_play_selector);
        sendBroadcast(new Intent(MusicPlayState.ACTION_MUSIC_STOP));
    }

    @Override
    public void pausePlayMusic() {
        isPlaying = false;
        mPlayerCtrlBtn.setImageResource(R.drawable.btn_play_selector);
        sendBroadcast(new Intent(MusicPlayState.ACTION_MUSIC_PAUSE));
    }

    @Override
    public void playNextMusic() {
        isPlaying = true;
        mPlayerCtrlBtn.setImageResource(R.drawable.btn_play_selector);
        sendBroadcast(new Intent(MusicPlayState.ACTION_MUSIC_NEXT));
    }

    @Override
    public void playPrevMusic() {
        isPlaying = true;
        mPlayerCtrlBtn.setImageResource(R.drawable.btn_play_selector);
        sendBroadcast(new Intent(MusicPlayState.ACTION_MUSIC_PREV));
    }

    @Override
    public void seekToPosition(int position) {
        sendBroadcast(new Intent(MusicPlayState.ACTION_SEEK_TO).putExtra(Constants.KEY_PLAYER_SEEK_TO_PROGRESS, position));
    }

    @Override
    public void refreshPlayProgress(int progress) {
        mPlayerSeekBar.setProgress(progress);
        String currentTime = CommonUtils.convertTime(progress);
        if (null != currentTime && !TextUtils.isEmpty(currentTime)) {
            mCurrentTime.setText(currentTime);
        }
    }

    @Override
    public void refreshPlaySecondProgress(int progress) {
        mPlayerSeekBar.setSecondaryProgress(progress);
    }

    @Override
    public void refreshPageInfo(Song entity, int totalDuration) {
        mPlayerCtrlBtn.setImageResource(R.drawable.btn_pause_selector);

        if (null != entity) {
            mTitle.setText(entity.getSongName());
            StringBuilder sb = new StringBuilder();
            sb.append("--\t");
            sb.append(entity.getArtist());
            sb.append("\t--");
            mSonger.setText(sb.toString().trim());
        }

        if (totalDuration > 0) {
            mPlayerSeekBar.setMax(totalDuration);
        }

        String imageUrl = entity.getPictureUrl();
        if (!CommonUtils.isEmpty(imageUrl)) {
            Glide.with(this).load(imageUrl).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPlayerDiscView);


        } else {
            //Bitmap bitmap = ImageBlurUtil.BoxBlurFilterBitmap(BitmapFactory.decodeResource(getResources(),
             //       R.drawable.player_bg));
            //mBackgroundImage.setImageBitmap(bitmap);
        }

        String totalTime = CommonUtils.convertTime(totalDuration);
        if (null != totalTime && !TextUtils.isEmpty(totalTime)) {
            mTotalTime.setText(totalTime);
        }

    }

    private class PlayBundleBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (null != action && !TextUtils.isEmpty(action)) {
                if (action.equals(Constants.ACTION_MUSIC_BUNDLE_BROADCAST)) {
                    Bundle extras = intent.getExtras();
                    if (null != extras) {
                        Song song = (Song) extras.getSerializable(Constants.KEY_MUSIC_PARCELABLE_DATA);
                        int totalDuration = extras.getInt(Constants.KEY_MUSIC_TOTAL_DURATION);
                        //TLog.v("bundle",song.getFileUrl()+":"+totalDuration);
                        mMusicsPresenter.refreshPageInfo(song, totalDuration);
                    }
                }
            }
        }

    }

    private class PlayPositionBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (null != action && !TextUtils.isEmpty(action)) {
                if (action.equals(Constants.ACTION_MUSIC_CURRENT_PROGRESS_BROADCAST)) {
                    Bundle extras = intent.getExtras();
                    if (null != extras) {
                        int progress = extras.getInt(Constants.KEY_MUSIC_CURRENT_DUTATION);
                        mMusicsPresenter.refreshProgress(progress);
                    }
                }
            }

        }
    }

    private class PlaySecondProgressBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (null != action && !TextUtils.isEmpty(action)) {
                if (action.equals(Constants.ACTION_MUSIC_SECOND_PROGRESS_BROADCAST)) {
                    Bundle extras = intent.getExtras();
                    if (null != extras) {
                        int percent = extras.getInt(Constants.KEY_MUSIC_SECOND_PROGRESS);
                        int totalDuration = extras.getInt(Constants.KEY_MUSIC_TOTAL_DURATION);
                        mMusicsPresenter.refreshSecondProgress(totalDuration*percent/100);
                        //TLog.d("jindu",totalDuration*percent+","+mPlayerSeekBar.getMax());
                    }
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if(mMusicsPresenter != null) {
            mMusicsPresenter.onStopPlay();
        }*/
        unregisterReceiver(mBundleBroadCast);
        unregisterReceiver(mPositionBroadCast);
        unregisterReceiver(mSecondProgressBroadCast);
        unregisterReceiver(mVolumeReceiver);
    }

    /**
     * 注册当音量发生变化时接收的广播
     */
    private void registerVolumeReceiver(){
        mVolumeReceiver = new MyVolumeReceiver() ;
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("android.media.VOLUME_CHANGED_ACTION") ;
        registerReceiver(mVolumeReceiver, filter) ;
    }

    /**
     * 处理音量变化时的界面显示
     * @author long
     */
    private class MyVolumeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;// 当前的媒体音量
                mVolumeSeek.setProgress(currVolume) ;
            }
        }
    }
}
