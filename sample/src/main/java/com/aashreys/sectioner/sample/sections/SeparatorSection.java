package com.aashreys.sectioner.sample.sections;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aashreys.sectioner.SingleItemSection;
import com.aashreys.sectioner.sample.R;
import com.aashreys.sectioner.sample.viewholders.SeparatorViewHolder;

/**
 * Created by aashreys on 09/08/16.
 */
public class SeparatorSection extends SingleItemSection<String, SeparatorViewHolder> {

    public SeparatorSection(String s) {
        super(s);
    }

    @Override
    protected SeparatorViewHolder createViewHolder(ViewGroup parent) {
        return new SeparatorViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_separator, parent, false));
    }

    @Override
    protected void bindViewHolder(
            SeparatorViewHolder holder,
            int sectionPosition,
            int adapterPosition
    ) {
        holder.onBind(getData());
    }
}
