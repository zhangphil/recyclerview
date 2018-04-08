package zhangphil.recyclerview.library;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Item {
    /**
     * 上层用户可以把此id作为Item的标识。
     */
    public int id;

    public int position;

    public boolean isExpand = true;

    /**
     * 当添加子Item时候，需要把当前的uniqueId作为子Item的parentUniqueId记录。
     * <p>
     * 注意：虽然是public属性，但上层用户不应该使用uniqueId和parentUniqueId寻找和比对Item。
     * uniqueId和parentUniqueId仅用来处理底层的数据逻辑。
     */
    public String parentUniqueId = null;
    public final String uniqueId = UUID.randomUUID().toString();

    private ArrayList<Item> items = new ArrayList<>();

    public abstract int getType();

    /**
     * 添加该Item的子Item
     *
     * @param item
     */
    public void addSubItem(Item item) {
        item.parentUniqueId = uniqueId;
        items.add(item);
    }

    public void clearSubItems() {
        items.clear();
    }

    public ArrayList<Item> getSubItems() {
        return items;
    }
}
