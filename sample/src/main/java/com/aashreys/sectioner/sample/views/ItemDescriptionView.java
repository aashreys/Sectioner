package com.aashreys.sectioner.sample.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aashreys.sectioner.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aashreys on 09/08/16.
 */
public class ItemDescriptionView extends LinearLayout {

    private static final String
            TEMPLATE_SECTION_NUMBER   = "Section Number: %s",
            TEMPLATE_SECTION_POSITION = "Section Position: %s",
            TEMPLATE_ADAPTER_POSITION = "Adapter Position: %s";

    @BindView(R.id.text_section_number)   TextView sectionNumber;
    @BindView(R.id.text_section_position) TextView sectionPosition;
    @BindView(R.id.text_adapter_position) TextView adapterPosition;


    public ItemDescriptionView(Context context) {
        super(context);
    }

    public ItemDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ItemDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemDescriptionView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void _init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_item_description, this, true);
        ButterKnife.bind(this);
    }


    public void setSectionNumber(int num) {
        sectionNumber.setText(String.format(TEMPLATE_SECTION_NUMBER, num));
    }

    public void setSectionPosition(int pos) {
        sectionPosition.setText(String.format(TEMPLATE_SECTION_POSITION, pos));
    }

    public void setAdapterPosition(int pos) {
        adapterPosition.setText(String.format(TEMPLATE_ADAPTER_POSITION, pos));
    }

}
