package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity
{
    Button reg;
    EditText name,sname,pwd,email,ph_no;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private FirebaseUser user;
    SharedPreferences sp;
    boolean flag=false;
    ImageButton ref_reg;
    String name1,sname1,pwd1,email1,ph_no1,emailPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("REGISTER");

        reg=(Button)findViewById(R.id.reg_button);
       // ref_reg=(ImageButton)findViewById(R.id.ref_reg);
        sname=(EditText)findViewById(R.id.st_name);
        pwd=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email_id);
        ph_no=(EditText)findViewById(R.id.ph_no);
        name=(EditText)findViewById(R.id.name);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        auth=FirebaseAuth.getInstance();



       /* sp=getSharedPreferences("register",MODE_PRIVATE);


        if(sp.getBoolean("registered",false))
        {
            startActivity(new Intent(MainActivity.this,catActivity.class));
            finish();
        }
        */


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    String userpwd=pwd.getText().toString().trim();
                    String useremail=email.getText().toString().trim();
                    Log.d("123","xyz1234");

                   // user = FirebaseAuth.getInstance().getCurrentUser();
                   // auth = FirebaseAuth.getInstance();

                    auth.fetchSignInMethodsForEmail(email1).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if (isNewUser) {
                                auth.createUserWithEmailAndPassword(email1, pwd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            senduserdata();
                                            Toast.makeText(MainActivity.this, "All details are entered", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(MainActivity.this,NavigationActivity.class);
                                            //intent.putExtra("user_id",auth.getCurrentUser().getUid());
                                            startActivity(intent);

                                        }
                                        else {
                                            Toast.makeText(MainActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                              /*  String userId = mDatabase.push().getKey();
                                User.user_id = userId;

                                Record record = new Record(name1, sname1, pwd1, email1, ph_no1);
                                //mDatabase.child("Record").child(sname1).child("User").setValue(name1);
                                mDatabase.child(userId).child("Store name").setValue(sname1);
                                mDatabase.child(userId).child("Name").setValue(name1);
                                mDatabase.child(userId).child("E-mail").setValue(email1);
                                mDatabase.child(userId).child("Password").setValue(pwd1);
                                mDatabase.child(userId).child("Phone_no").setValue(ph_no1);*/
                               // startActivity(new Intent(MainActivity.this, NavigationActivity.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "E-mail ID already registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                    /*auth.createUserWithEmailAndPassword(email1, pwd1);


                            String userId = mDatabase.push().getKey();
                            User.user_id = userId;

                            Record record = new Record(name1, sname1, pwd1, email1, ph_no1);
                            //mDatabase.child("Record").child(sname1).child("User").setValue(name1);
                            mDatabase.child(userId).child("Store name").setValue(sname1);
                            mDatabase.child(userId).child("Name").setValue(name1);
                            mDatabase.child(userId).child("E-mail").setValue(email1);
                            mDatabase.child(userId).child("Password").setValue(pwd1);
                            mDatabase.child(userId).child("Phone_no").setValue(ph_no1);
                            //mDatabase.child(userId).child("Category");
*/








                        // Intent intent=new Intent(MainActivity.this,catActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //startActivity(new Intent(MainActivity.this,catActivity.class));
                       // sp.edit().putBoolean("registered", true).apply();



            }
        });

    }

    private boolean validate()
    {
        boolean result=false;
        name1=name.getText().toString().trim();
        sname1=sname.getText().toString().trim();
        pwd1=pwd.getText().toString().trim();
        email1=email.getText().toString().trim();
        ph_no1=ph_no.getText().toString().trim();


        if(TextUtils.isEmpty(name1) || name1.length()==0 ||name1.equals("")||name1==null)
        {
            name.setError("Enter name");
        }
        else if(TextUtils.isEmpty(sname1) || sname1.length()==0 ||sname1.equals("")||sname1==null)
        {
            sname.setError("Enter Store name");
        }
        else if(TextUtils.isEmpty(pwd1) || pwd1.length()==0 ||pwd1.equals("")||pwd1==null)
        {
            pwd.setError("Enter Password");
        }
        else if(TextUtils.isEmpty(email1)|| email1.length()==0 ||email1.equals("")||email1==null)
        {
            email.setError("Enter E-mail ID");
        }
        else if(TextUtils.isEmpty(ph_no1)|| ph_no1.length()==0 ||ph_no1.equals("")||ph_no1==null)
        {
            ph_no.setError("Enter Phone no.");
        }
        else if(pwd1.length()<6)
        {
            pwd.setError("Password must be atleast 6 char long");
        }
        else if(ph_no1.length()<10 && ph_no1.length()>10)
        {
            ph_no.setError("Invalid Ph_no");
        }
        else if(!email1.matches(emailPattern))
        {
            email.setError("Invalid email-id");
        }
        else
        {
            result=true;
        }
        return result;
    }

    private void senduserdata()
    {
        Log.d("123","xyz1235");
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        Log.d("123","xyz123");
        DatabaseReference myRef=firebaseDatabase.getReference();
        Record record = new Record(name1, sname1, pwd1, email1, ph_no1);
        myRef.child(auth.getCurrentUser().getUid()).setValue(record);
    }

}
