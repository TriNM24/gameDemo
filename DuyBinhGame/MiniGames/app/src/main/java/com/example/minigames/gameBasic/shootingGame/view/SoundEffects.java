package com.example.minigames.gameBasic.shootingGame.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import com.example.minigames.R;

public enum SoundEffects {
    INSTANCE;

    public static final int SOUND_EXPLOSION=1;
    public static final int SOUND_GUY=2;
    public static final int SOUND_BULLET=3;

    Context mContext;

    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> mSoundPoolMap;
    private AudioManager mAudioManager;
    private int streamVolume;

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setContext(Context context) {

        mContext = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        mSoundPoolMap = new HashMap<Integer, Integer>();
        mSoundPoolMap.put(SOUND_EXPLOSION, mSoundPool.load(context, R.raw.explosion,1));
        mSoundPoolMap.put(SOUND_GUY,mSoundPool.load(context,R.raw.beep9,1));
        mSoundPoolMap.put(SOUND_BULLET,mSoundPool.load(context,R.raw.beep4,1));
        // TODO Add sounds corresponding to the bullet and Android Guy

        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        streamVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

    }

    public int getNumSounds() {
        return mSoundPoolMap.size();
    }

    public void playSound(int sound) {
        mSoundPool.play(mSoundPoolMap.get(sound),streamVolume, streamVolume, 10, 0, 5f);

    }
}
