package com.example.androidlab6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_cart, parent, false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = Cart.getInstance().getItemsArray()[position];
        holder.name.setText(item.getName());
        holder.price.setText(Integer.toString(item.getPrice()));
        holder.count.setText( Integer.toString(Cart.getInstance().getCount(item)));
    }

    @Override
    public int getItemCount() {
        return Cart.getInstance().getItemsArray().length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_text_recycler_cart);
            price = itemView.findViewById(R.id.price_text_recycler_cart);
            count = itemView.findViewById(R.id.count_text_recycler_cart);
        }
    }
}
