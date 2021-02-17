package com.example.flyaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_page extends AppCompatActivity {

    EditText un,pass;
    Button login;
    TextView register,warning,forgot_password;
    SQLite_helper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        
        un = findViewById(R.id.login_username);
        pass = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        register = findViewById(R.id.register);
        forgot_password = findViewById(R.id.forgot_password);
        warning = findViewById(R.id.login_warning);
        db = new SQLite_helper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("session",MODE_PRIVATE);
        String status = sharedPreferences.getString("remember","");
        if(status.equals("true"))
        {
            Intent intent = new Intent(Login_page.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
        else
            {
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               Boolean status = db.checkusernamepassword(un.getText().toString(),pass.getText().toString());
               if(status == true)
               {
                   Toast.makeText(Login_page.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Login_page.this,Dashboard.class);
                   SharedPreferences sharedPreferences = getSharedPreferences("session",MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString("remember","true");
                   editor.putString("username",un.getText().toString());
                   editor.apply();
                   warning.setVisibility(View.INVISIBLE);
                   startActivity(intent);
                   finish();
               }
               else 
               {
                   warning.setVisibility(View.VISIBLE);
               }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_page.this,Register_page.class);
                startActivity(intent);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_page.this,Forgot_Password.class);
                startActivity(intent);
            }
        });
    }

}