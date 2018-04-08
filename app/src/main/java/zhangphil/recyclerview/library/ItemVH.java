package zhangphil.recyclerview.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ItemVH extends RecyclerView.ViewHolder {

    public ItemVH(View itemView) {
        super(itemView);
    }

    public abstract int getType();
}
