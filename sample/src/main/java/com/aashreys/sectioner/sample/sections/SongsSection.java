package com.aashreys.sectioner.sample.sections;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aashreys.sectioner.MultiItemSection;
import com.aashreys.sectioner.sample.R;
import com.aashreys.sectioner.sample.viewholders.SongViewHolder;

/**
 * Created by aashreys on 09/08/16.
 */
public class SongsSection extends MultiItemSection<String, SongViewHolder> {

    @Override
    protected SongViewHolder createViewHolder(ViewGroup parent) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_song, parent, false));
    }

    @Override
    protected void bindViewHolder(SongViewHolder holder, int sectionPosition, int adapterPosition) {
        holder.onBind(dataList.get(sectionPosition), sectionPosition, adapterPosition);
    }
}
