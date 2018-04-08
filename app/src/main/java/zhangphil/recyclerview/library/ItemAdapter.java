package zhangphil.recyclerview.library;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class ItemAdapter extends RecyclerView.Adapter<ItemVH> {
    private ArrayList<Item> mItems;

    public ItemAdapter() {
        mItems = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    public Item getItem(int pos) {
        return mItems.get(pos);
    }

    public void addItem(Item item) {
        mItems.add(item);
    }

    public void addAll(ArrayList<Item> lists) {
        mItems.addAll(lists);
    }

    public ArrayList<Item> getAllItem() {
        return mItems;
    }

    /**
     * 清空所有Item。
     */
    public void clear() {
        mItems.clear();
    }

    /**
     * 寻找具有特定type和id的Item。
     *
     * @param type
     * @param id
     * @return
     */
    public Item getItem(int type, int id) {
        Item mItem = null;
        for (int i = 0; i < mItems.size(); i++) {
            Item item = mItems.get(i);
            if (item.getType() == type && item.id == id) {
                mItem = item;
            }
        }

        return mItem;
    }

    /**
     * 更新Item
     *
     * @param item
     */
    public void setItem(Item item) {
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

        if (item.isExpand) {
            if (item.getSubItems().size() > 0) {
                getAllItem().addAll(pos + 1, item.getSubItems());
            }
        } else {

        }
    }
}
