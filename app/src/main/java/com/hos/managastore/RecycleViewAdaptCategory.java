package com.hos.managastore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdaptCategory extends RecyclerView.Adapter<RecycleViewAdaptCategory.CategoryViewHolder> {

    ArrayList<Category> obj;

    public RecycleViewAdaptCategory(ArrayList<Category> obj) {
        this.obj = obj;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items,null,false);
        CategoryViewHolder VH=new CategoryViewHolder(v);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category c=obj.get(position);
        holder.IV.setImageResource(c.getImg());
        holder.TV.setText(c.getName());
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView IV;
        TextView TV;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            IV=itemView.findViewById(R.id.imageView);
            TV=itemView.findViewById(R.id.tvName);
        }
    }
}
