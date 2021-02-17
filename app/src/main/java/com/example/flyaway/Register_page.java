package com.example.flyaway;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;

public class Register_page extends AppCompatActivity {
    TextView login,warning;

    EditText name,email,mobile,pin,address;
    Button next;

    String emailPattern = "[a-z0-9._-]+@[a-z]+\\.+[a-z]+";

    SQLite_helper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_page);

        warning = findViewById(R.id.register_warning);
        next = findViewById(R.id.user_next);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        mobile = findViewById(R.id.user_mobile);
        pin = findViewById(R.id.user_pincode);
        address = findViewById(R.id.user_address);

        db = new SQLite_helper(this);

        login = findViewById(R.id.login);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || mobile.getText().toString().isEmpty()|| address.getText()
                .toString().isEmpty()|| pin.getText().toString().isEmpty())
                {
                    warning.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(email.getText().toString().matches(emailPattern))
                    {
                        if(mobile.getText().toString().length()<10)
                        {
                            warning.setText("Please enter valid mobile number");
                            warning.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Boolean user =db.checkuser(email.getText().toString());
                            if(user == true)
                            {
                                warning.setText("Email Already Exists");
                                warning.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Intent intent = new Intent(Register_page.this,Username_password.class);
                                intent.putExtra("name",name.getText().toString());
                                intent.putExtra("address",address.getText().toString());
                                intent.putExtra("pin",pin.getText().toString());
                                intent.putExtra("mobile",mobile.getText().toString());
                                intent.putExtra("email",email.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                    else
                    {
                        warning.setText("Wrong email type");
                        warning.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_page.this,Login_page.class);
                startActivity(intent);
            }
        });
    }


}