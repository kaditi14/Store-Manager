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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class DeleteItemActivity extends AppCompatActivity {

    EditText item;
    Button delete;
   // ImageButton refreshdelete;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        this.setTitle("DELETE ITEM");

        auth=FirebaseAuth.getInstance();

        item=(EditText)findViewById(R.id.item);
        delete=(Button)findViewById(R.id.delete_btn);
        //refreshdelete=(ImageButton)findViewById(R.id.refresh_delete);



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_name=item.getText().toString().trim();
                if(TextUtils.isEmpty(item_name) || item_name.length()==0 ||item_name.equals("")||item_name==null)
                {
                    item.setError("Enter item name");
                }
                else {
                    FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("Product").child(item_name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(DeleteItemActivity.this,"Item deleted successfully",Toast.LENGTH_SHORT).show();
                                item.setText("");
                            }
                            else {
                                Toast.makeText(DeleteItemActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
