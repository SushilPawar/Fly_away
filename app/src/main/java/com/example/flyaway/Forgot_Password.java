package com.example.flyaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot_Password extends AppCompatActivity {

    EditText fpuser,newpass,repass;
    Button Update;
    SQLite_helper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        fpuser = findViewById(R.id.fpuser);
        newpass = findViewById(R.id.fpnp);
        repass = findViewById(R.id.fprp);
        Update = findViewById(R.id.fpbtn);

        db = new SQLite_helper(this);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fpuser.getText().toString().isEmpty()||newpass.getText().toString().isEmpty()||repass.getText().toString().isEmpty())
                {
                    Toast.makeText(Forgot_Password.this, "Please fill All fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean userexist = db.checkusername(fpuser.getText().toString());
                    if(userexist == true)
                    {
                        if(repass.getText().toString().matches(newpass.getText().toString()))
                        {
                            db.forgot_password(fpuser.getText().toString(),newpass.getText().toString());
                            Intent intent = new Intent(Forgot_Password.this,Login_page.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Forgot_Password.this, "Passwords Not Matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else 
                    {
                        Toast.makeText(Forgot_Password.this, "Username does not exists", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }
        });
    }
}