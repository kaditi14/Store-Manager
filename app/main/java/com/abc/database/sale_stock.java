package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sale_stock extends AppCompatActivity {
    EditText item_name,quantity;
    Button sale;
    FirebaseAuth auth;
    int cnt=0;
    double qty,new_qty,sell_qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_stock);
        this.setTitle("SELL ITEM");



        auth=FirebaseAuth.getInstance();

        item_name = (EditText)findViewById(R.id.item_name);
        quantity =(EditText) findViewById(R.id.quantity);
        sale = (Button)findViewById(R.id.sale);


        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 cnt=0;

                final String item1=item_name.getText().toString().trim();
                 final String str =quantity.getText().toString().trim();
                 sell_qty=Double.parseDouble(str);

                FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product").child(item1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            int str = dataSnapshot.child("Quantity").getValue(Integer.class);
                            qty = str;
                            new_qty = qty - sell_qty;


                            if (cnt == 0) {
                                FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product").child(item1).child("Quantity").setValue(new_qty);

                                Toast.makeText(sale_stock.this, "Database Updated", Toast.LENGTH_SHORT).show();
                                cnt = 1;
                            }



                    }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError){

                        }

                            });




                    }


                });



            }

    }



