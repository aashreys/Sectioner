package com.aashreys.sectioner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of {@link Section} designed to containing multiple data {@link Item}s
 * corresponding to a single View Type.
 * <p>
 * Created by aashreys on 20/03/16.
 */
public abstract class MultiItemSection<Item, ViewHolder extends RecyclerView.ViewHolder> extends
        Section<Item, ViewHolder> {

    private final Object writeLock = new Object();

    @NonNull protected List<Item> itemList;

    /**
     * Creates an empty {@link MultiItemSection}.
     */
    public MultiItemSection() {
        super();
        this.itemList = new ArrayList<>();
    }

    /**
     * Creates a new {@link MultiItemSection} with added {@link Item}s. This does not trigger an
     * animated addition in the {@link RecyclerView}.
     *
     * @param itemList - {@link Item}s to add.
     */
    public MultiItemSection(@NonNull List<Item> itemList) {
        super();
        this.itemList = itemList;
    }

    @Override
    public void addAll(@NonNull Item... items) {
        int oldSize;
        synchronized (writeLock) {
            oldSize = itemList.size();
            Collections.addAll(itemList, items);
            updatePositionMapping();
        }
        _notifyItemRangeAdded(oldSize, size());
    }

    @Override
    public void add(@NonNull Item item) {
        int itemPosition;
        synchronized (writeLock) {
            itemList.add(item);
            itemPosition = itemList.size() - 1;
            updatePositionMapping();
        }
        _notifyItemAdded(itemPosition);
    }

    @Override
    public void add(int itemPosition, @NonNull Item item) {
        synchronized (writeLock) {
            itemList.add(itemPosition, item);
            updatePositionMapping();
        }
        _notifyItemAdded(itemPosition);
    }

    @Override
    public void remove(@NonNull Item item) {
        int itemPosition;
        synchronized (writeLock) {
            itemPosition = itemList.indexOf(item);
            itemList.remove(item);
            updatePositionMapping();
        }
        _notifyItemRemoved(itemPosition);
    }

    @Override
    public void remove(int itemPosition) {
        synchronized (writeLock) {
            itemList.remove(itemPosition);
            updatePositionMapping();
        }
        _notifyItemRemoved(itemPosition);
    }

    @Override
    public void replace(@NonNull Item item, boolean notifyAdapter) {
        int itemPosition;
        synchronized (writeLock) {
            itemPosition = itemList.indexOf(item);
            itemList.remove(item);
            itemList.add(itemPosition, item);
        }
        if (notifyAdapter) {
            _notifyItemReplaced(itemPosition);
        }
    }

    @Override
    public void replace(int itemPosition, @NonNull Item item, boolean notifyAdapter) {
        synchronized (writeLock) {
            itemList.remove(itemPosition);
            itemList.add(itemPosition, item);
        }
        if (notifyAdapter) {
            _notifyItemReplaced(itemPosition);
        }
    }

    @Override
    public void clearAndAdd(Item... items) {
        int oldSize, newSize;
        synchronized (writeLock) {
            oldSize = itemList.size();
            itemList.clear();
            Collections.addAll(itemList, items);
            updatePositionMapping();
            newSize = itemList.size();
        }
        if (oldSize > newSize) {
            _notifyItemRangeRemoved(oldSize, newSize);
        } else if (newSize > oldSize) {
            _notifyItemRangeAdded(oldSize, newSize);
        } else {
            _notifyItemRangeChanged(0, oldSize);
        }
    }

    @Override
    public boolean contains(Item item) {
        return item != null && itemList.contains(item);
    }

    @Override
    public void clear() {
        int oldSize;
        synchronized (writeLock) {
            oldSize = itemList.size();
            this.itemList = new ArrayList<>();
            updatePositionMapping();
        }
        _notifyItemRangeRemoved(oldSize, size());
    }

    @Override
    public int indexOf(@NonNull Item item) {
        return itemList.indexOf(item);
    }

    @Override
    public int lastIndexOf(@NonNull Item item) {
        return itemList.lastIndexOf(item);
    }

    @Override
    public int size() {
        return itemList.size();
    }
}
