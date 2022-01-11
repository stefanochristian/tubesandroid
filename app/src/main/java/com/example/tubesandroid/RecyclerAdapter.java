package com.example.tubesandroid;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {
    private List<catmodel> catList;
    private List<catmodel> catListAll;
    private RecyclerViewClickInterface recyclerViewClickInterface;
    public RecyclerAdapter(List<catmodel> list, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.catList = list;
        catListAll = new ArrayList<>(list);
        Log.d("SAME", catListAll.toString() + "-");
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.name.setText(catList.get(position).name);
        Picasso.get().load(catList.get(position).photo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<catmodel> filteredList = new ArrayList<>();
            if(charSequence.length() == 0 || charSequence == null){
                //Log.d("SAMER", catListAll.toString() + "-");
                filteredList.addAll(catListAll);
            }else {
                for (catmodel programming: catListAll) {
                    if(programming.name.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(programming);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values  = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            catList.clear();
            catList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgv_search);
            name = itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
