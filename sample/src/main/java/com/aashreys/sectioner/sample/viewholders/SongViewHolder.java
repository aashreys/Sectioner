package com.aashreys.sectioner.sample.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aashreys.sectioner.sample.R;

/**
 * Created by aashreys on 09/08/16.
 */
public class SongViewHolder extends RecyclerView.ViewHolder {

    private View        songView;
    private TextView    songNameText;
    private ImageButton playButton;

    public SongViewHolder(View itemView) {
        super(itemView);
        this.songView = itemView;
        this.songNameText = (TextView) itemView.findViewById(R.id.text_song_name);
        this.playButton = (ImageButton) itemView.findViewById(R.id.button_play);
    }

    public void onBind(
            final String songName,
            final int sectionPosition,
            final int adapterPosition
    ) {
        songNameText.setText(songName);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Playing song: " + songName, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        songView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        view.getContext(),
                        String.format(
                                "Item Section position: %s, Item Adapter Position: %s",
                                sectionPosition,
                                adapterPosition
                        ),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}