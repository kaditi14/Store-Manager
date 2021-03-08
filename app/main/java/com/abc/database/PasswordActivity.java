package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordActivity extends AppCompatActivity {

    EditText email,old_pwd,new_pwd;
    Button change;
    FirebaseUser user;
    ImageButton ref_chn;
    String user_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        this.setTitle("CHANGE PASSWORD");

        user_ID=getIntent().getStringExtra("user_id");

        email=(EditText)findViewById(R.id.email_text);
        old_pwd=(EditText)findViewById(R.id.old_pwd);
        new_pwd=(EditText)findViewById(R.id.new_pwd);
        change=(Button) findViewById(R.id.change_btn);
       // ref_chn=(ImageButton)findViewById(R.id.ref_change);
        final String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString().trim();
                String old_pwd1 = old_pwd.getText().toString().trim();
                final String new_pwd1 = new_pwd.getText().toString().trim();

                if (TextUtils.isEmpty(email1) || email1.length() == 0 || email1.equals("") || email1 == null) {
                    email.setError("Enter E-mail id");
                } else if (TextUtils.isEmpty(old_pwd1) || old_pwd1.length() == 0 || old_pwd1.equals("") || old_pwd1 == null) {
                    old_pwd.setError("Enter Password");
                } else if (TextUtils.isEmpty(new_pwd1) || new_pwd1.length() == 0 || new_pwd1.equals("") || new_pwd1 == null) {
                    new_pwd.setError("Enter Password");
                } else if (old_pwd1.length() < 6) {
                    old_pwd.setError("Password must be atleast 6 char long");
                } else if (new_pwd1.length() < 6) {
                    new_pwd.setError("Password must be atleast 6 char long");
                } else if (!email1.matches(emailPattern)) {
                    email.setError("Invalid email-id");
                } else {

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(email1, old_pwd1);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(new_pwd1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            FirebaseDatabase.getInstance().getReference(user.getUid()).child("password").setValue(new_pwd1);
                                            email.setText("");
                                            old_pwd.setText("");
                                            new_pwd.setText("");
                                            Toast.makeText(PasswordActivity.this, "Password updated", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(PasswordActivity.this, "Error Password not updated", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(PasswordActivity.this, "E-mail Id not registered", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
}
