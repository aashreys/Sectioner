package com.aashreys.sectioner.sample.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aashreys.sectioner.sample.R;

/**
 * Created by aashreys on 09/08/16.
 */
public class SeparatorViewHolder extends RecyclerView.ViewHolder {

    private TextView title;

    public SeparatorViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.text_title);
    }

    public void onBind(String name) {
        title.setText(name);
    }
}
