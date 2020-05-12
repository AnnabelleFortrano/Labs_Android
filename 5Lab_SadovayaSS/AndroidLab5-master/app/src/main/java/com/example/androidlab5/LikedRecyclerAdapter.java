package com.example.androidlab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LikedRecyclerAdapter extends RecyclerView.Adapter<LikedRecyclerAdapter.ViewHolder> {
    LayoutInflater inflater;

    public LikedRecyclerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.liked_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.webView.getSettings().setDisplayZoomControls(false);
        holder.webView.getSettings().setLoadWithOverviewMode(true);
        holder.webView.getSettings().setUseWideViewPort(true);
        holder.webView.loadUrl(LikedRepository.createInstance().getUrls().get(position));
    }

    @Override
    public int getItemCount() {
        return LikedRepository.createInstance().getUrls().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final WebView webView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.liked_view);
        }
    }
}
