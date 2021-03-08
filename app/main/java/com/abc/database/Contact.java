package com.abc.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieHandler;

import javax.xml.transform.Templates;

public class Contact extends AppCompatActivity {

    TextView shruti, aarti;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        shruti = findViewById(R.id.shruti);
        aarti = findViewById(R.id.aarti);

        shruti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = shruti.getText().toString();
                Intent callintent=new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:"+number));

                if(ActivityCompat.checkSelfPermission(Contact.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                startActivity(callintent);

            }
        });
        aarti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = aarti.getText().toString();
                Intent callintent=new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:"+number));

                if(ActivityCompat.checkSelfPermission(Contact.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                startActivity(callintent);
            }
        });
    }


}

