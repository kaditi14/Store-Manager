package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    Button login;
    EditText email,pwd;
    FirebaseAuth auth;
    private FirebaseUser user;
    SharedPreferences sp;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
   // ImageButton ref_login;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("LOGIN");

        login=(Button)findViewById(R.id.login_btn);
       // ref_login=(ImageButton)findViewById(R.id.refresh_login);
        email=(EditText)findViewById(R.id.email);
        pwd=(EditText)findViewById(R.id.pwd);

        auth=FirebaseAuth.getInstance();

        user=auth.getCurrentUser();

        if(user!=null)
        {
            finish();
            startActivity(new Intent(loginActivity.this,NavigationActivity.class));
        }





       /* sp=getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",false))
        {
            startActivity(new Intent(loginActivity.this,catActivity.class));
        }
        */

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString().trim();
                final String pwd1 = pwd.getText().toString().trim();

                if(TextUtils.isEmpty(email1) || email1.length()==0 ||email1.equals("")||email1==null)
                {
                    email.setError("Enter E-mail id");
                }
                else if(TextUtils.isEmpty(pwd1) || pwd1.length()==0 ||pwd1.equals("")||pwd1==null)
                {
                    pwd.setError("Enter Password");
                }
                else if(!email1.matches(emailPattern))
                {
                    email.setError("Invalid email-id");
                }
                else if(pwd1.length()<6)
                {
                    pwd.setError("Password must be atleast 6 char long");
                }
                else {
                  //  auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email1, pwd1).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(loginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(loginActivity.this,NavigationActivity.class);
                                intent.putExtra("user_id",auth.getCurrentUser().getUid());
                                email.setText("");
                                pwd.setText("");
                                //mDatabase = FirebaseDatabase.getInstance().getReference(User.user_id);
                                startActivity(intent);
                                finish();
                            }

                        }

                    });
                }
            }
        });
    }
}

