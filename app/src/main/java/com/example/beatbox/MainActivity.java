package com.example.beatbox;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
