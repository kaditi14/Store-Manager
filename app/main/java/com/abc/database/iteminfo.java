package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class iteminfo extends AppCompatActivity {
    public TextView name,qty,price;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    String item_id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteminfo);
        auth=FirebaseAuth.getInstance();
        final String Item_ID=getIntent().getStringExtra("item_id");
        item_id1=Item_ID;

        name=findViewById(R.id.name);
        qty=findViewById(R.id.quantity);
        price=findViewById(R.id.price);

        databaseReference=FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product").child(item_id1);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name1=dataSnapshot.child("Item").getValue().toString();
                String qty1=dataSnapshot.child("Quantity").getValue().toString();
                String price1=dataSnapshot.child("Price").getValue().toString();

                name.setText(name1);
                qty.setText(qty1);
                price.setText(price1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
