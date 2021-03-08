package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search2Activity extends AppCompatActivity {
    EditText search_edit_text;
    Button search_next;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    ArrayList<String> item_list;
    ArrayList<String> price_list;
    ArrayList<String> quantity_list;

    SearchAdapter searchAdapter;

    FirebaseAuth auth;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        this.setTitle("SEARCH ITEM");
        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        search_next=(Button)findViewById(R.id.search_another_btn);

        auth=FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        item_list = new ArrayList<>();
        price_list = new ArrayList<>();
        quantity_list = new ArrayList<>();


        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    setAdapter(editable.toString());
                }
                else {

                    item_list.clear();
                    price_list.clear();
                    quantity_list.clear();
                    recyclerView.removeAllViews();
                }
            }
        });

        search_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
    }

        private void setAdapter(final String s)
        {

          FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                  item_list.clear();
                  price_list.clear();
                  quantity_list.clear();
                  recyclerView.removeAllViews();
                  int cnt=0;

                  searchAdapter=new SearchAdapter(Search2Activity.this,item_list,price_list,quantity_list);
                  recyclerView.setAdapter(searchAdapter);
                  for(DataSnapshot snapshot:dataSnapshot.getChildren())
                  {
                      String pid=snapshot.getKey();
                     // String cat_name=snapshot.child("Category").getValue(String.class);
                      String item_name=snapshot.child("Item").getValue(String.class);
                      String price=snapshot.child("Price").getValue(String.class);
                      String Quantity=snapshot.child("Quantity").getValue(Integer.class).toString();

                      if(item_name.toLowerCase().contains(s.toLowerCase()))
                      {

                          item_list.add(item_name);
                          price_list.add(price);
                          quantity_list.add(Quantity);
                          cnt++;
                      }

                  }
                  searchAdapter.notifyDataSetChanged();
              }
              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
        }

}
