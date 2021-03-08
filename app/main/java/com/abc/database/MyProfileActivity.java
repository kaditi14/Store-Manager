package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MyProfileActivity extends AppCompatActivity {

    DatabaseReference myRef;
    TextView name,stname,e_mail,ph_no;
    Button delete,changepwd;
    FirebaseAuth auth;
    FirebaseUser user;
    String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        this.setTitle("PROFILE");

        user_ID=getIntent().getStringExtra("user_id");

        name=(TextView)findViewById(R.id.name);
        stname=(TextView)findViewById(R.id.StoreName);
        e_mail=(TextView)findViewById(R.id.e_mail);
        ph_no=(TextView)findViewById(R.id.ph_no);
        delete=(Button)findViewById(R.id.delete);
        changepwd=(Button)findViewById(R.id.changepwd);

       // auth=FirebaseAuth.getInstance();

        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyProfileActivity.this,PasswordActivity.class);
                intent.putExtra("user_id",user_ID);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth= FirebaseAuth.getInstance();
                user=auth.getCurrentUser();


                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).removeValue();
                            startActivity(new Intent(MyProfileActivity.this,LauncherActiviity.class));
                            finish();
                            Toast.makeText(MyProfileActivity.this,"Account deleted",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(MyProfileActivity.this,"Error occured",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }

   @Override
    protected void onStart() {
        super.onStart();
       auth=FirebaseAuth.getInstance();

       myRef = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid());
       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot)
           {
               String name1 = dataSnapshot.child("name").getValue().toString();
               String stname1 = dataSnapshot.child("store_name").getValue().toString();
               String e_mail1 = dataSnapshot.child("email").getValue().toString();
               String ph_no1 = dataSnapshot.child("ph_no").getValue().toString();

               name.setText(name1);
               stname.setText(stname1);
               e_mail.setText(e_mail1);
               ph_no.setText(ph_no1);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError)
           {

           }
       });

    }

}
