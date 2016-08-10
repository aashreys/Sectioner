package com.aashreys.sectioner.sample.sections;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aashreys.sectioner.SingleItemSection;
import com.aashreys.sectioner.sample.R;
import com.aashreys.sectioner.sample.viewholders.HeaderViewHolder;

/**
 * Created by aashreys on 09/08/16.
 */
public class HeaderSection extends SingleItemSection<Object, HeaderViewHolder> {

    public HeaderSection() {
        super("");
    }

    @Override
    protected HeaderViewHolder createViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_header, parent, false)
        );
    }

    @Override
    protected void bindViewHolder(
            HeaderViewHolder holder,
            int sectionPosition,
            int adapterPosition
    ) {
        // Do nothing
    }
}
