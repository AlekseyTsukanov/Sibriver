package ru.alex.sibrivertest.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.alex.sibrivertest.R;
import ru.alex.sibrivertest.ormlitedatabase.dao.ItemModelDAO;
import ru.alex.sibrivertest.ormlitedatabase.helper.HelperFactory;
import ru.alex.sibrivertest.ormlitedatabase.model.ItemModel;

public class Adapter extends SelectableAdapter<Adapter.ViewHolder> {

    private static final int ITEM_COUNT = 10;
    private List<ItemModel> itemModels;
    private ItemModelDAO itemModelDAO;
    private ViewHolder.ClickListener clickListener;
    Context context;

    public Adapter(ViewHolder.ClickListener clickListener, Context context) {
        super();
        this.context = context;
        try {
            itemModelDAO = HelperFactory.getDatabaseHelper().getItemModelDAO();
            this.clickListener = clickListener;
            for (int i = 0; i < ITEM_COUNT; ++i) {
                itemModels = itemModelDAO.getAllItems();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        itemModels.clear();
        notifyDataSetChanged();
    }

    public void restoreItems() throws SQLException {
        try {
            itemModels = itemModelDAO.getAllItems();
            notifyDataSetChanged();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int position) throws SQLException {
        itemModels.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) throws SQLException {
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    removeItem(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            itemModels.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemModel itemModel = itemModels.get(position);
        holder.nameTv.setText(itemModel.getName());
        holder.addressTv.setText(itemModel.getAddress());
        holder.createdTv.setText(itemModel.getCreated());
        holder.statusTv.setText(String.valueOf(itemModel.getStatus()));
        switch (itemModel.getStatus()){
            case 0:
                holder.statusTv.setBackgroundResource(R.color.statusGreen);
                holder.statusTv.setText(context.getResources().getString(R.string.newItem));
                break;
            case 1:
                holder.statusTv.setBackgroundResource(R.color.statusYellow);
                holder.statusTv.setText(context.getResources().getString(R.string.inProgress));
                break;
            case 2:
                holder.statusTv.setBackgroundResource(R.color.statusComplete);
                holder.statusTv.setText(context.getResources().getString(R.string.complete));
                break;
            case 3:
                holder.statusTv.setBackgroundResource(R.color.statusOrange);
                holder.statusTv.setText(context.getResources().getString(R.string.withQuestion));
                break;
        }
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        TextView nameTv, addressTv, createdTv, statusTv;
        View selectedOverlay;

        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            nameTv = (TextView)itemView.findViewById(R.id.nameTv);
            addressTv = (TextView)itemView.findViewById(R.id.addressTv);
            createdTv = (TextView)itemView.findViewById(R.id.createdTv);
            statusTv = (TextView)itemView.findViewById(R.id.statusTv);
            selectedOverlay = itemView.findViewById(R.id.selectedOverlay);

            this.listener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getPosition());
            }

            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int position);
            public boolean onItemLongClicked(int position);
        }
    }
}