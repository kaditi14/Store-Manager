package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateActivity extends AppCompatActivity {
    EditText price,item;
    Button update_price;
    ImageButton ref_update;
    DatabaseReference databaseReference;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.setTitle("UPDATE PRICE");

        auth=FirebaseAuth.getInstance();
        price=(EditText)findViewById(R.id.price_update);
        item=(EditText)findViewById(R.id.item_name);
        update_price=(Button)findViewById(R.id.update_price_btn);
        //ref_update=(ImageButton)findViewById(R.id.ref_update);




        update_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String new_price=price.getText().toString().trim();
                final String item_name=item.getText().toString().trim();

                if(TextUtils.isEmpty(new_price) || new_price.length()==0 ||new_price.equals("")||new_price==null)
                {
                    price.setError("Enter price");
                }
                else if(TextUtils.isEmpty(item_name) || item_name.length()==0 ||item_name.equals("")||item_name==null)
                {
                    item.setError("Enter Item name");
                }
                else if (Double.parseDouble(new_price) <= 0) {
                    price.setError("Enter valid price");
                }
                else {
                    FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product").child(item_name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product").child(item_name).child("Price").setValue(new_price).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            price.setText("");
                                            item.setText("");
                                            Toast.makeText(updateActivity.this, "Price updated successfullly", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(updateActivity.this, "Price not updated", Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(updateActivity.this, "Item not present", Toast.LENGTH_SHORT).show();

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
