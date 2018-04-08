package zhangphil.recyclerview.library;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.Iterator;

public abstract class ExpandableItemAdapter extends ItemAdapter {

    @NonNull
    @Override
    public abstract ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull ItemVH holder, int position);

    /**
     * 展开Item
     *
     * @param item
     */
    public void expand(Item item) {
        int pos = -1;

        for (int i = 0; i < getItemCount(); i++) {
            Item it = getItem(i);
            if (TextUtils.equals(it.uniqueId, item.uniqueId)) {
                pos = i;
            }
        }

        getAllItem().set(pos, item);

        Iterator<Item> iterator = getAllItem().iterator();
        while (iterator.hasNext()) {
            Item it = iterator.next();
            if (TextUtils.equals(it.parentUniqueId, item.uniqueId)) {
                iterator.remove();
            }
        }

        if (item.getSubItems().size() > 0) {
            getAllItem().addAll(pos + 1, item.getSubItems());
        }

        item.isExpand = true;

        if (mExpandableToggleListener != null) {
            mExpandableToggleListener.onExpand(item);
        }
    }

    /**
     * 收起Item
     *
     * @param item
     */
    public void collapse(Item item) {
        Iterator<Item> iterator = getAllItem().iterator();
        while (iterator.hasNext()) {
            Item it = iterator.next();
            if (TextUtils.equals(it.parentUniqueId, item.uniqueId)) {
                iterator.remove();
            }
        }

        item.isExpand = false;

        if (mExpandableToggleListener != null) {
            mExpandableToggleListener.onCollapse(item);
        }
    }

    public interface ExpandableToggleListener {
        public void onExpand(Item item);

        public void onCollapse(Item item);
    }

    private ExpandableToggleListener mExpandableToggleListener = null;

    public void setExpandableToggleListener(ExpandableToggleListener listener) {
        this.mExpandableToggleListener = listener;
    }

    public void toggle(Item item) {
        item.isExpand = !item.isExpand;

        if (item.isExpand) {
            expand(item);
        } else {
            collapse(item);
        }
    }
}
