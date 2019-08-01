package com.example.beatbox;

//Present the asset files to the user.
// And keep care of them.Play them etc
public class Sound {
    private String mAssetPath;
    private String mName;
    //By this assigning not to in
    // it is possible to set it to null
    private Integer mSoundId;

    //Split off the filename.Then use string
    // replace to remove the file extension
    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components=assetPath.split("/");
        String filename=components[components.length-1];
        mName=filename.replace(".wav","");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }

}
