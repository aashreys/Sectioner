package com.aashreys.sectioner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Provides APIs for manipulating entire {@link Section}s added to the {@link RecyclerView} and
 * provides {@link Section} level control over the {@link RecyclerView}'s children.
 * <p>
 * Created by aashreys on 20/03/16.
 */
public class SectionManager {

    private static final String TAG = SectionManager.class.getSimpleName();

    private final Object writeLock = new Object();

    /**
     * The {@link SectionedRecyclerViewAdapter} associated with this {@link SectionManager}
     */
    @NonNull private SectionedRecyclerViewAdapter adapter;

    /**
     * Internal list of {@link Section}s which serves as data for this {@link SectionManager}
     */
    @NonNull private List<Section> sections;

    /**
     * Maps the first item position in a non-empty {@link Section} to its respective {@link
     * Section}.
     */
    @NonNull private TreeMap<Integer, Integer> itemPosToSectionPosMap;

    /**
     * Maps a non-empty {@link Section} to the position of its first item.
     */
    @NonNull private TreeMap<Integer, Integer> sectionPosToItemPosMap;

    /**
     * Represents the cumulative number of items contained in all {@link Section}s of this {@link
     * SectionManager}. Size does not include {@link Section}s which have been disabled via the
     * {@link Section#setEnabled(boolean)} API.
     */
    private int itemsSize;

    /**
     * Creates a {@link SectionManager} and binds it to a {@link SectionedRecyclerViewAdapter}.
     *
     * @param adapter - {@link SectionedRecyclerViewAdapter} to bind to.
     */
    public SectionManager(@NonNull SectionedRecyclerViewAdapter adapter) {
        this.sections = new ArrayList<>();
        this.adapter = adapter;
        this.itemPosToSectionPosMap = new TreeMap<>();
        this.sectionPosToItemPosMap = new TreeMap<>();
    }

    /**
     * Returns the {@link SectionedRecyclerViewAdapter associated with this {@link SectionManager}.
     */
    @NonNull
    public SectionedRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    /**
     * Returns all {@link Section}s added to this {@link SectionManager}.
     */
    @NonNull
    public List<Section> getSections() {
        return sections;
    }

    /**
     * Returns a {@link Section} a position in this {@link SectionManager}.
     *
     * @param sectionPosition position of {@link Section} to return
     * @return {@link Section} at specified position
     * @throws IndexOutOfBoundsException
     */
    public Section get(int sectionPosition) {
        return sections.get(sectionPosition);
    }

    /**
     * Adds {@link Section}s to the end of {@link #sections}, updates the internal mappings and
     * notifies the {@link #adapter}.
     *
     * @param sections {@link Section}s to add
     */
    public void addAll(Section... sections) {
        int oldItemSize;
        synchronized (writeLock) {
            oldItemSize = itemsSize;
            for (Section section : sections) {
                section.setManager(this);
                this.sections.add(section);
            }
            createItemSectionMappings();
        }
        adapter.notifyItemRangeInserted(oldItemSize, itemsSize - oldItemSize);
    }

    /**
     * Maps the position of the first item in a section to its respective {@link Section} and
     * vice-versa. Must be called after every change in {@link #sections} or its underlying {@link
     * Section}'s item list.
     *
     * @see #itemPosToSectionPosMap
     * @see #sectionPosToItemPosMap
     * @see #getSectionPositionForAdapterPosition(int)
     */
    protected void createItemSectionMappings() {
        itemsSize = 0;
        itemPosToSectionPosMap = new TreeMap<>();
        sectionPosToItemPosMap = new TreeMap<>();
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).isEnabled() && sections.get(i).size() > 0) {
                itemPosToSectionPosMap.put(itemsSize, i);
                sectionPosToItemPosMap.put(i, itemsSize);
                itemsSize += sections.get(i).size();
            }
        }
    }

    /**
     * Adds a {@link Section} at a given position in {@link #sections}, updates the internal
     * mappings and notifies the {@link #adapter}.
     *
     * @param position position to add {@link Section} at.
     * @param section  {@link Section} to add.
     */
    public void add(int position, Section section) {
        synchronized (writeLock) {
            section.setManager(this);
            sections.add(position, section);
            createItemSectionMappings();
        }
        adapter.notifyItemRangeInserted(
                getFirstItemAdapterPositionForSectionPosition(position), section.size()
        );
    }

    /**
     * Gets the adapter position for the first item in a {@link Section} specified by a position in
     * {@link #sections}.
     *
     * @param sectionPosition position of the {@link Section}
     */
    protected int getFirstItemAdapterPositionForSectionPosition(int sectionPosition) {
        if (sectionPosToItemPosMap.containsKey(sectionPosition)) {
            // This is a non-empty section. Return it's first position
            return sectionPosToItemPosMap.get(sectionPosition);
        } else {
            // This is an empty section, get the first item position from the map for a non-empty
            // section preceding this
            Map.Entry<Integer, Integer> entry = sectionPosToItemPosMap.floorEntry(sectionPosition);
            return entry != null ? entry.getValue() : 0;
        }
    }

    /**
     * Gets the adapter position for the first item in a given {@link Section} present in
     * {@link #sections}.
     *
     * @param section {@link Section} for whose item the position returned
     */
    protected int getFirstItemAdapterPositionForSection(Section section) {
        return getFirstItemAdapterPositionForSectionPosition(sections.indexOf(section));
    }

    /**
     * Removes the {@link Section} at a given position from {@link #sections}, updates the internal
     * mappings and notifies the {@link #adapter}.
     *
     * @param position position to remove {@link Section} from.
     */
    public void remove(int position) {
        remove(sections.get(position));
    }

    /**
     * Removes the first occurrence of a {@link Section} from {@link #sections}, updates the
     * internal mappings and notifies the {@link #adapter}.
     *
     * @param section {@link Section} to remove.
     */
    public void remove(Section section) {
        int positionStart;
        synchronized (writeLock) {
            positionStart
                    = getFirstItemAdapterPositionForSectionPosition(sections.indexOf(section));
            sections.remove(section);
            section.setManager(null);
            createItemSectionMappings();
        }
        adapter.notifyItemRangeRemoved(positionStart, section.size());
    }

    /**
     * Replaces the first occurrence of a {@link Section} in {@link #sections}, updates the
     * internal mappings and notifies the {@link #adapter}.
     *
     * @param section {@link Section} to replace with
     */
    public void replace(Section section) {
        replace(sections.indexOf(section), section);
    }

    /**
     * Replaces a {@link Section} at the given position with a {@link Section}, updates the
     * internal mappings and notifies the {@link #adapter}.
     *
     * @param position position to replace {@link Section} at.
     * @param section  new {@link Section} to replace with.
     */
    public void replace(int position, Section section) {
        int newSectionItemCount, oldSectionItemCount;
        synchronized (writeLock) {
            newSectionItemCount = section.size();
            oldSectionItemCount = sections.get(position).size();
            sections.get(position).setManager(null);
            sections.remove(position);
            section.setManager(this);
            sections.add(position, section);
            createItemSectionMappings();
        }
        int itemsDiff = newSectionItemCount - oldSectionItemCount;
        int sectionFirstItemPos = getFirstItemAdapterPositionForSectionPosition(position);
        if (itemsDiff > 0) {
            // Items have changed and have been added
            adapter.notifyItemRangeChanged(sectionFirstItemPos, oldSectionItemCount);
            adapter.notifyItemRangeInserted(
                    sectionFirstItemPos + oldSectionItemCount,
                    Math.abs(itemsDiff)
            );
        } else if (itemsDiff < 0) {
            // Items have changed and have been removed
            adapter.notifyItemRangeChanged(sectionFirstItemPos, newSectionItemCount);
            adapter.notifyItemRangeRemoved(
                    sectionFirstItemPos + newSectionItemCount,
                    Math.abs(itemsDiff)
            );
        } else {
            // Items have changed in place
            adapter.notifyItemRangeChanged(sectionFirstItemPos, newSectionItemCount);
        }
    }

    /**
     * Checks if a {@link Section} is contained in {@link #sections}.
     *
     * @param section {@link Section} to check for.
     * @return true if {@param section} is found.
     */
    public boolean contains(Section section) {
        return section != null && sections.contains(section);
    }

    /**
     * Clears the sections list - {@link #sections}, thereby flushing all held {@link Section}s and
     * their items. Also updates the internal mappings and notifies the {@link #adapter}.
     */
    public void clear() {
        synchronized (writeLock) {
            for (Section section : sections) {
                section.setManager(null);
            }
            this.sections = new ArrayList<>();
            createItemSectionMappings();
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Gets the Section items total number of items contained in this {@link SectionManager}}. Size
     * does not items contained in disabled {@link Section}s.
     */
    public int getItemCount() {
        return itemsSize;
    }

    /**
     * Gets the total number of {@link Section}s contained in this {@link SectionManager}
     */
    public int getSectionCount() {
        return sections.size();
    }

    /**
     * Returns the first position of a {@link Section} in {@link #sections} if it is found, else
     * returns -1.
     */
    public int indexOf(Section section) {
        return sections.indexOf(section);
    }

    /**
     * Returns a {@link Section} for a given view type. For use with {@link
     * SectionedRecyclerViewAdapter} only.
     *
     * @param adapterViewType view type provided by {@link SectionedRecyclerViewAdapter} in {@link
     *                        SectionedRecyclerViewAdapter#onCreateViewHolder(ViewGroup, int)}
     * @return {@link Section}
     */
    protected Section getSectionForAdapterViewType(int adapterViewType) {
        return sections.get(adapterViewType);
    }

    protected int getViewTypeForAdapterPosition(int adapterPosition) {
        return getSectionPositionForAdapterPosition(adapterPosition);
    }

    protected int getSectionPositionForAdapterPosition(int adapterPosition) {
        return itemPosToSectionPosMap.floorEntry(adapterPosition).getValue();
    }

    protected int getItemSectionPosition(int adapterPosition) {
        return adapterPosition -
                getFirstItemAdapterPositionForSectionPosition(getSectionPositionForAdapterPosition(
                        adapterPosition));
    }

    protected Section getSectionForAdapterPosition(int adapterPosition) {
        return sections.get(getSectionPositionForAdapterPosition(adapterPosition));
    }
}
