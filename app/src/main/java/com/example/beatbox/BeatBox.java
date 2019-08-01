package com.example.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Related to asset managment:finding assets,keeping track
// of then,and eventually playing them as sound.
public class BeatBox {
    //Constant for logging
    private static final String TAG = "BeatBox";
    //Remember which folder you saved wrestling grunts.
    private static final String SOUND_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAsset;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAsset = context.getAssets();
        //The new way of set the audio attributes
        // in a sound pool after android lollipop
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool=new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(MAX_SOUNDS)
                .build();
        loadSounds();
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){
            return;
        }
        mSoundPool.play(soundId,1.0f,1.0f,1,0,1.0f);
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAsset.list(SOUND_FOLDER);
            Log.i(TAG, "Found" + soundNames.length + " sounds");
        } catch (IOException ieo) {
            Log.e(TAG, "Could not list assets", ieo);
            return;
        }
        //Build the list
        for (String filename : soundNames) {
            try {
                String assetPath = SOUND_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            }catch (IOException ioe){
                Log.e(TAG,"Could not load sound"+filename,ioe);
            }
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd= mAsset.openFd(sound.getAssetPath());
        int soundId=mSoundPool.load(afd,1);
        sound.setSoundId(soundId);
    }

    public void release(){
        mSoundPool.release();
    }
}
