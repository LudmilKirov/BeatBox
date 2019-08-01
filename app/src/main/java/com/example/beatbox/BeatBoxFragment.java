package com.example.beatbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BeatBoxFragment extends Fragment {

    private  BeatBox mBeatBox;
    private Sound mSound;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBeatBox = new BeatBox(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beat_box,container,false);
        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.fragment_beat_box_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return view;
    }

    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Button mButton;
        public SoundHolder(LayoutInflater inflater,ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_sound,container,false));

            mButton=(Button) itemView.findViewById(R.id.list_item_sound_button);
            mButton.setOnClickListener(this);
        }

        public void  bindSound(Sound sound){
            mSound=sound;
            mButton.setText(mSound.getName());
        }
        @Override
        public void onClick(View view) {
            mBeatBox.play(mSound);
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<Sound> mSounds;

        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder soundHolder, int position) {
            Sound sound=mSounds.get(position);
            soundHolder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }

        public SoundAdapter(List<Sound> sounds){
            mSounds = sounds;
        }


    }


}

