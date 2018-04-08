package zhangphil.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import zhangphil.recyclerview.library.ExpandableItemAdapter;
import zhangphil.recyclerview.library.Item;
import zhangphil.recyclerview.library.ItemVH;

public class MainActivity extends AppCompatActivity {
    private final int TYPE_GROUP = 0xfa01;
    private final int TYPE_CHILD = 0xfa02;

    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManage = new LinearLayoutManager(this);
        layoutManage.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManage);

        mAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        String[] groupNames = {"A", "B", "C", "D", "E", "F", "G"};
        for (int i = 0; i < groupNames.length; i++) {
            Group group = new Group();
            group.id = i;
            if (i == 0)
                group.isExpand = true;
            else {
                group.isExpand = false;
            }
            group.title = groupNames[i];

            int count = (int) (Math.random() * 10) % 5 + 1;
            for (int j = 0; j < count; j++) {
                Child child = new Child();
                child.position = j;
                child.group = group;

                group.addSubItem(child);
            }

            mAdapter.addItem(group);
        }

        mAdapter.setExpandableToggleListener(new ExpandableItemAdapter.ExpandableToggleListener() {
            @Override
            public void onExpand(Item item) {
                Toast.makeText(getApplicationContext(), ((Group) item).title + " 展开", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCollapse(Item item) {
                Toast.makeText(getApplicationContext(), ((Group) item).title + " 收起", Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //更新第0组
                Group mGroup = (Group) mAdapter.getItem(0);
                mGroup.clearSubItems();

                mGroup.title = "zhangphil";

                Child child = new Child();
                child.group = mGroup;
                child.position = 2018;

                mGroup.addSubItem(child);

                mAdapter.setItem(mGroup);

                mAdapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    public class RecyclerViewAdapter extends ExpandableItemAdapter {
        @Override
        public ItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            ItemVH itemVH = null;
            switch (viewType) {
                case TYPE_GROUP:
                    view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                    itemVH = new GroupVH(view);
                    break;

                case TYPE_CHILD:
                    view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
                    itemVH = new ChildVH(view);
                    break;
            }

            return itemVH;
        }

        @Override
        public void onBindViewHolder(ItemVH holder, int position) {
            Item item = getItem(position);
            switch (getItemViewType(position)) {
                case TYPE_GROUP:
                    final Group g = (Group) item;
                    GroupVH groupVH = (GroupVH) holder;
                    groupVH.text.setText(g.title);

                    groupVH.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toggle(g);
                            notifyDataSetChanged();
                        }
                    });
                    break;

                case TYPE_CHILD:
                    Child c = (Child) item;
                    ChildVH childVH = (ChildVH) holder;
                    childVH.text1.setText(c.group.title);
                    childVH.text2.setText(c.position + "");
                    break;
            }
        }
    }

    private class Group extends Item {
        public String title;

        @Override
        public int getType() {
            return TYPE_GROUP;
        }
    }

    private class Child extends Item {
        public Group group;

        @Override
        public int getType() {
            return TYPE_CHILD;
        }
    }

    private class GroupVH extends ItemVH {
        public TextView text;

        public GroupVH(View itemView) {
            super(itemView);
            text = itemView.findViewById(android.R.id.text1);
            text.setBackgroundColor(Color.RED);
        }

        @Override
        public int getType() {
            return TYPE_GROUP;
        }
    }

    private class ChildVH extends ItemVH {
        public TextView text1;
        public TextView text2;

        public ChildVH(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
            text1.setTextColor(Color.LTGRAY);
            text2.setTextColor(Color.BLUE);
        }

        @Override
        public int getType() {
            return TYPE_CHILD;
        }
    }
}
