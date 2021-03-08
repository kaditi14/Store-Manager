package com.abc.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LauncherActiviity extends AppCompatActivity {

    Button reg,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_activiity);

        reg=(Button)findViewById(R.id.register);
        log=(Button)findViewById(R.id.login);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActiviity.this,MainActivity.class));

            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActiviity.this,loginActivity.class));

            }
        });
    }
}
