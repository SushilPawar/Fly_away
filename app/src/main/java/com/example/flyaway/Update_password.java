package com.example.flyaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_password extends AppCompatActivity {

    EditText op,np,rp;
    Button upb;
    SQLite_helper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        op = findViewById(R.id.old_password);
        np = findViewById(R.id.new_password);
        rp = findViewById(R.id.re_enter_password);
        upb = findViewById(R.id.updatepassbtn);
        db = new SQLite_helper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("session",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        upb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(op.getText().toString().isEmpty()||np.getText().toString().isEmpty()||rp.getText().toString().isEmpty())
                {
                    Toast.makeText(Update_password.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean result = db.checkusernamepassword(username,op.getText().toString());
                    if(result == true)
                    {
                        if(np.getText().toString().equals(rp.getText().toString()))
                        {
                             db.update_password(username,np.getText().toString());
                             op.setText("");
                             np.setText("");
                             rp.setText("");
                        }
                        else
                        {
                            Toast.makeText(Update_password.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Update_password.this, "Old Password is not Matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}