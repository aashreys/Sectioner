package com.aashreys.sectioner.sample.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aashreys.sectioner.sample.R;

/**
 * Created by aashreys on 09/08/16.
 */
public class AlbumViewHolder extends RecyclerView.ViewHolder {

    private View      albumView;
    private TextView  albumNameText;
    private TextView  artistNameText;
    private ImageView albumImage;

    public AlbumViewHolder(View itemView) {
        super(itemView);
        this.albumView = itemView;
        this.albumNameText = (TextView) itemView.findViewById(R.id.text_album);
        this.artistNameText = (TextView) itemView.findViewById(R.id.text_artist);
        this.albumImage = (ImageView) itemView.findViewById(R.id.image_album);
    }

    public void onBind(
            String albumName,
            String artistName,
            final int sectionPosition,
            final int adapterPosition
    ) {
        albumNameText.setText(albumName);
        artistNameText.setText(artistName);
        albumImage.setImageDrawable(albumView.getContext()
                .getResources()
                .getDrawable(R.drawable.ic_record));
        albumView.setOnClickListener(new View.OnClickListener() {
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
