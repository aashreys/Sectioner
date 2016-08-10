package com.aashreys.sectioner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * An implementation of {@link Section} designed to contain a single data {@link Data}. Useful for
 * creating list headers, footers and separators.
 * <p/>
 * Created by aashreys on 20/03/16.
 */
public abstract class SingleItemSection<Data, ViewHolder extends RecyclerView.ViewHolder>
        extends Section<Data, ViewHolder> {

    private final Object writeLock = new Object();

    @NonNull protected Data data;

    private static final String MULTI_ITEM_OPERATION_ERROR = "MultiItem operations are unsupported in SingleItemSection";

    public SingleItemSection(Data data) {
        super();
        this.data = data;
    }

    @NonNull
    public Data getData() {
        return this.data;
    }

    public void addAll(@NonNull Data... datas) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }
    
    @Override
    public void add(@NonNull Data data) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void add(int position, @NonNull Data data) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void remove(@NonNull Data data) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void remove(int position) {
        throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
    }

    @Override
    public void replace(@NonNull Data data, boolean notifyAdapter) {
        synchronized (writeLock) {
            this.data = data;
        }
        if (notifyAdapter) {
            _notifyItemReplaced(0);
        }
    }

    @Override
    public void replace(int position, @NonNull Data data, boolean notifyAdapter) {
        if (position == 0) {
            replace(data, notifyAdapter);
        } else {
            throw new UnsupportedOperationException(
                    "Cannot replace item from position " + position +
                            " in SingleItemSection, since it has only one position - 0");
        }
    }

    @Override
    public void clearAndAdd(Data... datas) {
        if (datas.length == 1) {
            this.data = datas[0];
        } else {
            throw new UnsupportedOperationException(MULTI_ITEM_OPERATION_ERROR);
        }
    }

    @Override
    public boolean contains(Data data) {
        return data != null && this.data.equals(data);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Clearing a SingleItemSection is prohibited. You may change the value held by calling replace()");
    }

    @Override
    public int indexOf(@NonNull Data data) {
        if (this.data.equals(data)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public int lastIndexOf(@NonNull Data data) {
        return indexOf(data);
    }

    @Override
    public final int size() {
        return 1;
    }

}
