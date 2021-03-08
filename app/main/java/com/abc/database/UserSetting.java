package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserSetting extends AppCompatActivity {

    Button change_pwd,delete_acc,logout;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        change_pwd=(Button)findViewById(R.id.change_pwd);
        delete_acc=(Button)findViewById(R.id.delete_acc);
        logout=(Button)findViewById(R.id.logout);

        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserSetting.this,PasswordActivity.class));
            }
        });
        delete_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth=FirebaseAuth.getInstance();
                user=auth.getCurrentUser();

                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseDatabase.getInstance().getReference(User.user_id).removeValue();
                            Toast.makeText(UserSetting.this,"Account deleted",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserSetting.this,LauncherActiviity.class));

                        }
                        else{
                            Toast.makeText(UserSetting.this,"Error occured",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth=FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(UserSetting.this,LauncherActiviity.class));
            }
        });
    }
}
