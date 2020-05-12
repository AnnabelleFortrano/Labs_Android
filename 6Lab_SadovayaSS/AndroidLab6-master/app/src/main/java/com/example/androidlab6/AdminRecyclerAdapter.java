package com.example.androidlab6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.ViewHolder> {
    private Click click;
    public AdminRecyclerAdapter(Click click) {
        this.click = click;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_admin, parent,  false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Item item = ItemService.getInstance().getItems().get(position);
        holder.name.setText(item.getName());
        holder.price.setText(Integer.toString(item.getPrice()));
        holder.count.setText(Integer.toString(item.getCount()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemService.getInstance().getItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView count;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_text_recycler_admin);
            price = itemView.findViewById(R.id.price_text_recycler_admin);
            count = itemView.findViewById(R.id.count_text_recycler_admin);
            layout = itemView.findViewById(R.id.recycler_item_layout_admin);
        }
    }
}
