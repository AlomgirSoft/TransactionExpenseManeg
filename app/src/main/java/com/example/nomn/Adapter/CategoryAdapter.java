package com.example.nomn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomn.Model.Category;
import com.example.nomn.R;
import com.example.nomn.databinding.ItemCategoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Category>categoryArrayList;

    public interface  CategoryClickLisener{
        void categoryClick(Category category);
    }
  CategoryClickLisener categoryClickLisener;
    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList,  CategoryClickLisener categoryClickLisener) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.categoryClickLisener= categoryClickLisener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category= categoryArrayList.get(position);
        holder.binding.CategoryTv.setText(category.getCategoryName());
        holder.binding.catetoryIcon.setImageResource(category.getCategoryImage());

        holder.binding.catetoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));
        holder.itemView.setOnClickListener(v->{
            categoryClickLisener.categoryClick(category);
        });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
           ItemCategoryBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemCategoryBinding.bind(itemView);
        }
    }
}
