package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    TextView name,email;
    DatabaseReference myRef;
    String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_View);
        navigationView.setNavigationItemSelectedListener(this);

        user_ID=getIntent().getStringExtra("user_id");

    }

   /* @Override
    protected void onStart() {
        super.onStart();
        name=(TextView)findViewById(R.id.header_name);
        email=(TextView)findViewById(R.id.header_email);

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child(User.user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name1 = dataSnapshot.child("Name").getValue(String.class);
                String e_mail1 = dataSnapshot.child("E-mail").getValue(String.class);

                name.setText(name1);
                email.setText(e_mail1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.dashboard)
        {

            Intent intent=new Intent(NavigationActivity.this,DashboardActivity.class);
            intent.putExtra("user_id",user_ID);
            startActivity(intent);
        }
        else if(id==R.id.setting)
        {
            Intent intent=new Intent(NavigationActivity.this,MyProfileActivity.class);
            intent.putExtra("user_id",user_ID);
            startActivity(intent);
        }
        else if (id==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
           startActivity(new Intent(NavigationActivity.this,loginActivity.class));
            finish();

        }
        else if (id==R.id.rateus)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(NavigationActivity.this,RateUs.class));


        }
        else if (id==R.id.contact)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(NavigationActivity.this,Contact.class));


        }

        return false;
    }
}
