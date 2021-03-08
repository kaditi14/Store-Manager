package com.abc.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    Context context;

    ArrayList<String> item_list;
    ArrayList<String> price_list;
    ArrayList<String> quantity_list;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView itemTextView,priceTextView,qtyTextView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView=(TextView)itemView.findViewById(R.id.itemtextView);
            priceTextView=(TextView)itemView.findViewById(R.id.priceTextView);
            qtyTextView=(TextView)itemView.findViewById(R.id.qtyTextView);


        }
    }

    public SearchAdapter(Context context, ArrayList<String> item_list, ArrayList<String> price_list, ArrayList<String> quantity_list) {

        this.context = context;

        this.item_list = item_list;
        this.price_list = price_list;
        this.quantity_list = quantity_list;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.activity_search_list_item,parent,false);

        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.itemTextView.setText(item_list.get(position));
        holder.priceTextView.setText(price_list.get(position));
        holder.qtyTextView.setText(quantity_list.get(position));


    }



    @Override
    public int getItemCount() {
        return item_list.size();
    }
}
