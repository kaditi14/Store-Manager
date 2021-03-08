package com.abc.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.function.DoubleUnaryOperator;

public class RateUs extends AppCompatActivity {


    Button submit;
    TextView msg;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        auth=FirebaseAuth.getInstance();

        final RatingBar ratingBar=findViewById(R.id.ratingBar);
        submit=findViewById(R.id.submit);
        msg=findViewById(R.id.msg);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                msg.setText("rating is:"+ratingBar.getRating());
            }
        });
    }
}
