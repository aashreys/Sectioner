package com.aashreys.sectioner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * An implementation of {@link Section} designed to contain a single data {@link Item}. Useful for
 * creating list headers, footers and separators.
 * <p/>
 * Created by aashreys on 20/03/16.
 */
public abstract class SingleItemSection<Item, ViewHolder extends RecyclerView.ViewHolder>
        extends Section<Item, ViewHolder> {

    private final Object writeLock = new Object();

    @NonNull protected Item item;

    private static final String MULTI_ITEM_OPERATION_ERROR = "MultiItem operations are unsupported in SingleItemSection";

    public SingleItemSection(Item item) {
        super();
        this.item = item;
    }

    @NonNull
    public Item getItem() {
        return this.item;
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int sectionPosition, int adapterPosition) {
        // Override me
    }

    public void addAll(@NonNull Item... items) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }
    
    @Override
    public void add(@NonNull Item item) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void add(int position, @NonNull Item item) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void remove(@NonNull Item item) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void remove(int position) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void replace(@NonNull Item item, boolean notifyAdapter) {
        synchronized (writeLock) {
            this.item = item;
        }
        if (notifyAdapter) {
            _notifyItemReplaced(0);
        }
    }

    @Override
    public void replace(int position, @NonNull Item item, boolean notifyAdapter) {
        if (position == 0) {
            replace(item, notifyAdapter);
        } else {
            throw new UnsupportedOperationException(
                    "Cannot replace item from position " + position +
                            " in SingleItemSection, since it has only one position - 0");
        }
    }

    @Override
    public void clearAllAndReplace(Item... items) {
        if (items.length == 1) {
            this.item = items[0];
        } else {
            throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
        }
    }

    @Override
    public boolean contains(Item item) {
        return item != null && this.item.equals(item);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Clearing a SingleItemSection is prohibited. You may change the value held by calling replace()");
    }

    @Override
    public int indexOf(@NonNull Item item) {
        if (this.item.equals(item)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public int lastIndexOf(@NonNull Item item) {
        return indexOf(item);
    }

    @Override
    public final int size() {
        return 1;
    }

}
