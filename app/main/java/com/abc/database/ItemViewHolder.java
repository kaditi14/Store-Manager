package com.abc.database;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder{
           View mView;
           TextView name;


   public ItemViewHolder(@NonNull View itemView) {
       super(itemView);
       name=itemView.findViewById(R.id.item_name_text_view);

       mView=itemView;
   }


}
