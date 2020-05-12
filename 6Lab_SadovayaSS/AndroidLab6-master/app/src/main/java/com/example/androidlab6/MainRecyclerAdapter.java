package com.example.androidlab6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    private Click click;
    public MainRecyclerAdapter(Click click) {
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Item item = ItemService.getInstance().getAvailableItems().get(position);
        holder.nameView.setText(item.getName());
        holder.priceView.setText( Integer.toString(item.getPrice()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemService.getInstance().getAvailableItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView priceView;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_text_recycler_main);
            priceView = itemView.findViewById(R.id.price_text_recycler_main);
            layout = itemView.findViewById(R.id.recycler_item_layout_main);
        }
    }
}
