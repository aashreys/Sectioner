package com.aashreys.sectioner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * A implementation of the {@link RecyclerView.Adapter} for interfacing with {@link SectionManager}
 * to display different {@link Section}s. Use {@link #getSectionManager()} to obtain a {@link
 * SectionManager} with which you can manipulate the {@link RecyclerView}'s underlying data list.
 * <p/>
 * Created by aashreys on 19/03/16.
 */
public class SectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SectionedRecyclerViewAdapter.class.getSimpleName();

    @NonNull private SectionManager sectionManager;

    public SectionedRecyclerViewAdapter() {
        this.sectionManager = new SectionManager(this);
    }

    @NonNull
    public SectionManager getSectionManager() {
        return sectionManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return sectionManager.getSectionForAdapterViewType(viewType).createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int adapterPosition) {
        sectionManager.getSectionForAdapterPosition(adapterPosition)
                .bindViewHolder(
                        holder,
                        sectionManager.getItemSectionPosition(adapterPosition),
                        adapterPosition
                );
    }

    @Override
    public int getItemViewType(int position) {
        return sectionManager.getViewTypeForAdapterPosition(position);
    }

    @Override
    public int getItemCount() {
        return sectionManager.getItemCount();
    }

}
