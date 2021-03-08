package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class catActivity extends AppCompatActivity {

    Button add_item;
    EditText  item_name, qty, item_delete, price;
    FirebaseUser user;
    ImageButton refreshcat;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseinstance;
    double old_qty,new_qty,add_qty;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        this.setTitle("ADD ITEM");


        add_item = (Button) findViewById(R.id.add_item_btn);
        item_delete = (EditText) findViewById(R.id.item_name);
        item_name = (EditText) findViewById(R.id.item_name);
        qty = (EditText) findViewById(R.id.qty);
        price = (EditText) findViewById(R.id.price_btn);


        user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    
        add_item.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // startActivity(new Intent(catActivity.this,ProductActivity.class));

                                            final String item = item_name.getText().toString();
                                            final String str = qty.getText().toString();
                                            add_qty = Double.parseDouble(str);
                                            final String price1 = price.getText().toString();

                                            if (TextUtils.isEmpty(item) || item.length() == 0 || item.equals("") || item == null) {
                                                item_delete.setError("Enter Item name");
                                            } else if (TextUtils.isEmpty(price1) || price1.length() == 0 || price1.equals("") || price1 == null) {
                                                price.setError("Enter Price");
                                            } else if (add_qty <= 0) {
                                                qty.setError("Enter valid quantity");
                                            } else if (Double.parseDouble(price1) <= 0) {
                                                price.setError("Enter valid price");
                                            } else {

                                                FirebaseDatabase.getInstance().getReference(user.getUid()).child("Product").child(item).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            FirebaseDatabase.getInstance().getReference(user.getUid()).child("Product").child(item).child("Quantity").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    int str = dataSnapshot.getValue(Integer.class);
                                                                    old_qty = str;
                                                                    new_qty = old_qty + add_qty;


                                                                    if (new_qty <= 0) {
                                                                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Product").child(item).removeValue();
                                                                    } else {
                                                                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Product").child(item).child("Quantity").setValue(new_qty);
                                                                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Product").child(item).child("Price").setValue(price1);
                                                                        Toast.makeText(catActivity.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                                                                        item_name.setText("");
                                                                        qty.setText("");
                                                                        price.setText("");
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                        } else {

                                                            mFirebaseinstance = FirebaseDatabase.getInstance();
                                                            //  mFirebaseDatabase = mFirebaseinstance.getReference(user.getUid());

                                                            mFirebaseinstance.getReference(user.getUid()).child("Product").child(item).child("Item").setValue(item);
                                                            mFirebaseinstance.getReference(user.getUid()).child("Product").child(item).child("Quantity").setValue(add_qty);
                                                            mFirebaseinstance.getReference(user.getUid()).child("Product").child(item).child("Price").setValue(price1);
                                                            Toast.makeText(catActivity.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                                                            item_name.setText("");
                                                            qty.setText("");
                                                            price.setText("");

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }


                                                });

                                            }
                                        }
                                    });

        }


    }
