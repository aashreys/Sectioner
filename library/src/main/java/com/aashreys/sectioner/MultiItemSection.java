package com.aashreys.sectioner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of {@link Section} designed to containing multiple data {@link Data}s
 * corresponding to a single View Type.
 * <p>
 * Created by aashreys on 20/03/16.
 */
public abstract class MultiItemSection<Data, ViewHolder extends RecyclerView.ViewHolder> extends
        Section<Data, ViewHolder> {

    private final Object writeLock = new Object();

    @NonNull protected List<Data> dataList;

    /**
     * Creates an empty {@link MultiItemSection}.
     */
    public MultiItemSection() {
        super();
        this.dataList = new ArrayList<>();
    }

    /**
     * Creates a new {@link MultiItemSection} with added {@link Data}s. This does not trigger an
     * animated addition in the {@link RecyclerView}.
     *
     * @param dataList - {@link Data}s to add.
     */
    public MultiItemSection(@NonNull List<Data> dataList) {
        super();
        this.dataList = dataList;
    }

    @Override
    public void add(@NonNull Data... datas) {
        int oldSize;
        synchronized (writeLock) {
            oldSize = dataList.size();
            Collections.addAll(dataList, datas);
            updatePositionMapping();
        }
        _notifyItemRangeInserted(oldSize, size() - oldSize);
    }

    @Override
    public void add(int itemPosition, @NonNull Data data) {
        synchronized (writeLock) {
            dataList.add(itemPosition, data);
            updatePositionMapping();
        }
        _notifyItemAdded(itemPosition);
    }

    @Override
    public void remove(@NonNull Data data) {
        int itemPosition;
        synchronized (writeLock) {
            itemPosition = dataList.indexOf(data);
            dataList.remove(data);
            updatePositionMapping();
        }
        _notifyItemRemoved(itemPosition);
    }

    @Override
    public void remove(int itemPosition) {
        synchronized (writeLock) {
            dataList.remove(itemPosition);
            updatePositionMapping();
        }
        _notifyItemRemoved(itemPosition);
    }

    @Override
    public void replace(@NonNull Data data, boolean notifyAdapter) {
        int itemPosition;
        synchronized (writeLock) {
            itemPosition = dataList.indexOf(data);
            dataList.remove(data);
            dataList.add(itemPosition, data);
        }
        if (notifyAdapter) {
            _notifyItemReplaced(itemPosition);
        }
    }

    @Override
    public void replace(int itemPosition, @NonNull Data data, boolean notifyAdapter) {
        synchronized (writeLock) {
            dataList.remove(itemPosition);
            dataList.add(itemPosition, data);
        }
        if (notifyAdapter) {
            _notifyItemReplaced(itemPosition);
        }
    }

    @Override
    public void clearAndAdd(Data... datas) {
        int oldSize, newSize;
        synchronized (writeLock) {
            oldSize = dataList.size();
            dataList.clear();
            Collections.addAll(dataList, datas);
            updatePositionMapping();
            newSize = dataList.size();
        }
        if (oldSize > newSize) {
            _notifyItemRangeChanged(0, newSize);
            _notifyItemRangeRemoved(newSize, oldSize - newSize);
        } else if (newSize > oldSize) {
            _notifyItemRangeChanged(0, oldSize);
            _notifyItemRangeInserted(oldSize, newSize - oldSize);
        } else {
            _notifyItemRangeChanged(0, oldSize);
        }
    }

    @Override
    public boolean contains(Data data) {
        return data != null && dataList.contains(data);
    }

    @Override
    public void clear() {
        int oldSize;
        synchronized (writeLock) {
            oldSize = dataList.size();
            this.dataList = new ArrayList<>();
            updatePositionMapping();
        }
        _notifyItemRangeRemoved(0, oldSize);
    }

    @Override
    public int firstIndexOf(@NonNull Data data) {
        return dataList.indexOf(data);
    }

    @Override
    public int lastIndexOf(@NonNull Data data) {
        return dataList.lastIndexOf(data);
    }

    @Override
    public int size() {
        return dataList.size();
    }
}
