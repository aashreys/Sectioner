package com.aashreys.sectioner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * A Section represents a collection of data items - {@link Item} - which correspond to a single
 * View Type in the RecyclerView Adapter.
 * This class provides APIs for manipulating individual items in a {@link Section}, useful for
 * granular item level control over a {@link RecyclerView}'s children.
 *
 * @author aashreys on 23/03/16.
 */
public abstract class Section<Item, ViewHolder extends RecyclerView.ViewHolder> {

    @Nullable protected SectionManager manager;

    private boolean isEnabled = true;

    public Section() {}

    protected void setManager(@Nullable SectionManager manager) {
        this.manager = manager;
    }

    /**
     * Creates and returns a {@link ViewHolder} for binding your {@link View} to an {@link Item}
     * from this Section. You can inflate your {@link View} and pass it to the {@link ViewHolder}
     * in this method.
     *
     * @param parent - the parent RecyclerView.
     * @return {@link ViewHolder} corresponding to this {@link Section}'s {@link View}.
     */
    protected abstract ViewHolder createViewHolder(ViewGroup parent);

    /**
     * Binds the {@link View} associated with this {@link Section}.
     *
     * @param holder          the {@link ViewHolder} created in {@link #createViewHolder(ViewGroup)}
     * @param sectionPosition the position of {@link ViewHolder} in this section.
     * @param adapterPosition the position of this {@link ViewHolder} in the adapter.
     */
    protected void bindViewHolder(ViewHolder holder, int sectionPosition, int adapterPosition) {
        // Override me
    }

    /**
     * Adds data {@link Item}s to the end of this {@link Section} and notifies the adapter.
     *
     * @param items {@link Item}s to add.
     * @see MultiItemSection#addAll(Object[])
     * @see SingleItemSection#addAll(Object[])
     */
    public abstract void addAll(@NonNull Item... items);

    /**
     * Adds a single {@link Item} to the end of this {@link Section} and notifies the adapter.
     *
     * @param item {@link Item} to add.
     */
    public abstract void add(@NonNull Item item);

    /**
     * Adds a single {@link Item} to a specific position in this {@link Section} and notifies the
     * adapter.
     *
     * @param position position to add the {@link Item} to.
     * @param item     {@link Item} to add.
     * @throws IndexOutOfBoundsException if {@param position} is not found or invalid.
     */
    public abstract void add(int position, @NonNull Item item);

    /**
     * Removes the first occurrence of a single {@link Item} in this {@link Section} and notifies
     * the adapter.
     *
     * @param item {@link Item} to remove
     */
    public abstract void remove(@NonNull Item item);

    /**
     * Removes the {@link Item} at a specified position.
     *
     * @param position position to remove {@link Item} from
     * @throws IndexOutOfBoundsException if {@param position} is not found or invalid.
     */
    public abstract void remove(int position);

    /**
     * Replaces the first occurrence of an {@link Item} with the new {@link Item} in this {@link
     * Section} and <i>optionally</i> notifies the adapter. The notification being optional can be
     * used to either immediately notify the adapter or allow the user to manually reflect the
     * update at a later time or in response to an event.
     *
     * @param item          {@link Item} to replace with an updated instance
     * @param notifyAdapter controls whether or not the adapter is notified to the replacement.
     */
    public abstract void replace(@NonNull Item item, boolean notifyAdapter);

    /**
     * Similar to {@link #replace(Object, boolean)}, except that the replacement is made at the
     * specified position.
     *
     * @param position      position to remove item at
     * @param item          {@link Item} to replace the removed item with
     * @param notifyAdapter controls whether or not the adapter is notified to the replacement.
     */
    public abstract void replace(int position, @NonNull Item item, boolean notifyAdapter);

    /**
     * Clears this {@link Section} adds the supplied {@link Item}s. The adapter is only notified
     * once, at the end of the replacement operation and the notification is that of the difference
     * in the size of the list, not the entire removal and re-addition of items, hence making it a
     * more efficient notification method.
     * <p/>
     * It is recommended to use this method instead of chaining calls to {@link #clear} and
     * then {@link #addAll(Object[])}.
     *
     * @param items {@link Item}s to replace the current {@link Item}s with.
     */
    public abstract void clearAllAndReplace(Item... items);

    /**
     * Checks if an {@link Item} is contained in this {@link Section}.
     *
     * @param item {@link Item} to check for
     * @return True if the {@link Item} is contained, False otherwise.
     */
    public abstract boolean contains(Item item);

    /**
     * Removes all {@link Item}s from this {@link Section}.
     */
    public abstract void clear();

    /**
     * Returns the first position of an {@link Item} in this {@link Section}.
     *
     * @param item {@link Item} to find position for.
     * @return First position of the item if it is found in this Section, -1 otherwise.
     */
    public abstract int indexOf(@NonNull Item item);

    /**
     * Returns the last position of an {@link Item} in this {@link Section}.
     *
     * @param item {@link Item} to find position for.
     * @return Last position of the item if it is found in this section, -1 otherwise.
     */
    public abstract int lastIndexOf(@NonNull Item item);

    /**
     * Convenience method to check if this {@link Section} is empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of {@link Item}s present in this {@link Section}.
     */
    public abstract int size();

    /**
     * Getter
     *
     * @return True if this {@link Section} is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * Enables/disables this {@link Section}. When enabled this {@link Section} will appear in a
     * {@link RecyclerView} list. Upon disabling this {@link Section} will disappear from the list
     * as if it had never been added. This is a convenience method so that developers can hide
     * {@link Section}s without having to mess around with the internal {@link Item} list.
     */
    public void setEnabled(boolean isEnabled) {
        if (this.isEnabled != isEnabled) {
            this.isEnabled = isEnabled;
            updatePositionMapping();
            if (isEnabled) {
                _notifyItemRangeAdded(0, size());
            } else {
                _notifyItemRangeRemoved(size(), 0);
            }
        }

    }

    /**
     * Helper method to invoke {@link SectionManager#createItemSectionMappings()} on the {@link
     * SectionManager} this {@link Section} is associated with.
     */
    protected void updatePositionMapping() {
        if (manager != null) {
            this.manager.createItemSectionMappings();
        }
    }

    /**
     * Helper method to notify the adapter for this {@link Section} of the addition of multiple new
     * {@link Item}s to this {@link Section}.
     *
     * @param oldSize Size of the {@link Section} before the addition
     * @param newSize Size of the {@link Section} after the addition
     */
    protected void _notifyItemRangeAdded(int oldSize, int newSize) {
        if (manager != null) {
            manager.getAdapter().notifyItemRangeInserted(
                    manager.getFirstItemAdapterPositionForSection(this) + oldSize - 1,
                    newSize - oldSize
            );
        }
    }

    /**
     * Helper method to notify the adapter for this {@link Section} of the removal of multiple new
     * {@link Item}s to this {@link Section}.
     *
     * @param oldSize Size of the {@link Section} before the removal
     * @param newSize Size of the {@link Section} after the removal
     */
    protected void _notifyItemRangeRemoved(int oldSize, int newSize) {
        if (manager != null) {
            manager.getAdapter().notifyItemRangeRemoved(
                    manager.getFirstItemAdapterPositionForSection(this) + oldSize - 1,
                    oldSize - newSize
            );
        }
    }

    /**
     * Helper method to notify the adapter for this {@link Section} that a range of {@link Item}s
     * has been changed.
     */
    protected void _notifyItemRangeChanged(int startPosition, int itemsChanged) {
        if (manager != null) {
            manager.getAdapter().notifyItemRangeChanged(
                    manager.getFirstItemAdapterPositionForSection(this) + startPosition,
                    itemsChanged
            );
        }
    }

    /**
     * Helper method to notify the adapter for this {@link Section} that an {@link Item} has been
     * added.
     *
     * @param itemPosition Position at which the {@link Item} was added.
     */
    protected void _notifyItemAdded(int itemPosition) {
        if (manager != null) {
            manager.getAdapter().notifyItemInserted(
                    manager.getFirstItemAdapterPositionForSection(this) + itemPosition
            );
        }
    }

    /**
     * Helper method to notify the adapter for this {@link Section} that an {@link Item} has been
     * removed.
     *
     * @param itemPosition Position from which the {@link Item} was removed.
     */
    protected void _notifyItemRemoved(int itemPosition) {
        if (manager != null) {
            manager.getAdapter().notifyItemRemoved(
                    manager.getFirstItemAdapterPositionForSection(this) + itemPosition
            );
        }
    }

    /**
     * Helper method to notify the adapter for this {@link Section} that an {@link Item} has been
     * replaced.
     *
     * @param itemPosition Position at which the {@link Item} was replaced.
     */
    protected void _notifyItemReplaced(int itemPosition) {
        if (manager != null) {
            manager.getAdapter().notifyItemChanged(
                    manager.getFirstItemAdapterPositionForSection(this) + itemPosition
            );
        }
    }

}
