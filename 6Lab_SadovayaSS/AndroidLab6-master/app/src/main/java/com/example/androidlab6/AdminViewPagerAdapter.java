package com.example.androidlab6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminViewPagerAdapter extends RecyclerView.Adapter<AdminViewPagerAdapter.ViewHolder> {

    private Click click;

    public AdminViewPagerAdapter(Click click) {
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Item item = ItemService.getInstance().getItems().get(position);
        holder.name.setText(item.getName());
        holder.price.setText(Integer.toString(item.getPrice()));
        holder.count.setText(Integer.toString(item.getCount()));
        holder.description.setText(item.getDescription());
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Item updatedItem = new Item(
                            item.getId(),
                            holder.name.getText().toString(),
                            Integer.parseInt(holder.price.getText().toString()),
                            Integer.parseInt(holder.count.getText().toString()),
                            holder.description.getText().toString()
                            );
                    ItemService.getInstance().updateItem(updatedItem);
                    click.click(position);
                } catch (NumberFormatException e) {

                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemService.getInstance().deleteItem(item.getId());
                click.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemService.getInstance().getItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText name;
        EditText price;
        EditText count;
        EditText description;
        Button change;
        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_pager_admin);
            price = itemView.findViewById(R.id.price_pager_admin);
            count = itemView.findViewById(R.id.count_pager_admin);
            description = itemView.findViewById(R.id.desc_pager_admin);
            change = itemView.findViewById(R.id.viewpager_item_btn_admin);
            delete = itemView.findViewById(R.id.viewpager_delete_btn_admin);
        }
    }
}
