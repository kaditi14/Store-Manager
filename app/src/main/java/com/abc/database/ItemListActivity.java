package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;


public class ItemListActivity extends AppCompatActivity {

    private RecyclerView item_list;
    private DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseRecyclerAdapter<Item, ItemViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product");
        //databaseReference.keepSynced(true);
        item_list = (RecyclerView) findViewById(R.id.item_list);
        item_list.setHasFixedSize(true);
        item_list.setLayoutManager(new LinearLayoutManager(this));

    }

    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(databaseReference, new SnapshotParser<Item>() {
            @NonNull
            @Override
            public Item parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new Item(snapshot.child("Item").getValue().toString(),snapshot.child("Price").getValue().toString(),snapshot.child("Quantity").getValue().toString());
            }
        }).setLifecycleOwner(this).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int i, @NonNull Item item) {

                        itemViewHolder.name.setText(item.getItem());

                        final String item_id = getRef(i).getKey();
                        itemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String item1=item_id;
                                Intent intent=new Intent(ItemListActivity.this,iteminfo.class);
                                intent.putExtra("item_id",item1);
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_single_layout, parent, false);
                        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
                        return itemViewHolder;
                    }
                };
        //firebaseRecyclerAdapter.notifyDataSetChanged();

        item_list.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}



