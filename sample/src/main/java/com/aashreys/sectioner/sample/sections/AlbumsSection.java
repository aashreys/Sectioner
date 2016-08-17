package com.aashreys.sectioner.sample.sections;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aashreys.sectioner.MultiItemSection;
import com.aashreys.sectioner.sample.R;
import com.aashreys.sectioner.sample.viewholders.AlbumViewHolder;

/**
 * Created by aashreys on 09/08/16.
 */
public class AlbumsSection extends MultiItemSection<String, AlbumViewHolder> {

    @Override
    protected AlbumViewHolder createViewHolder(ViewGroup parent) {
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_album, parent, false));
    }

    @Override
    protected void bindViewHolder(
            AlbumViewHolder holder,
            int sectionPosition,
            int adapterPosition
    ) {
        holder.onBind(
                dataList.get(sectionPosition),
                "Pink Floyd",
                sectionPosition,
                adapterPosition
        );
    }
}
