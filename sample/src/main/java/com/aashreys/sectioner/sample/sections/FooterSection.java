package com.aashreys.sectioner.sample.sections;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aashreys.sectioner.SingleItemSection;
import com.aashreys.sectioner.sample.R;
import com.aashreys.sectioner.sample.viewholders.FooterViewHolder;

/**
 * Created by aashreys on 09/08/16.
 */
public class FooterSection extends SingleItemSection<String, FooterViewHolder> {

    public FooterSection() {
        super("");
    }

    @Override
    protected FooterViewHolder createViewHolder(ViewGroup parent) {
        return new FooterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_footer, parent, false));
    }

    @Override
    protected void bindViewHolder(
            FooterViewHolder holder,
            int sectionPosition,
            int adapterPosition
    ) {
        // Do nothing
    }
}
