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

public class Username_password extends AppCompatActivity {

    EditText username,pass,repass;
    Button register;
    TextView finalwar;
    SQLite_helper db;
    private Uri imagepath;
    private Bitmap imagetostore;
    private static final  int PICK_IMAGE_REQUEST = 100;
    ImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_password);

        finalwar = findViewById(R.id.finalwarning);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.reenter_password);
        register = findViewById(R.id.register_user);
        profile_image = findViewById(R.id.profile_pic);
        db = new SQLite_helper(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String pin = intent.getStringExtra("pin");
        String mobile = intent.getStringExtra("mobile");
        String email = intent.getStringExtra("email");


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent objectintent =new Intent();
                    objectintent.setType("image/*");
                    objectintent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(objectintent,PICK_IMAGE_REQUEST);
                }
                catch (Exception e)
                {
                    Toast.makeText(Username_password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty()||pass.getText().toString().isEmpty()||repass.getText().toString().isEmpty())
                {
                    finalwar.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(pass.getText().toString().matches(repass.getText().toString()))
                    {
                        Boolean username1 = db.checkusername(username.getText().toString());
                        if(username1 == true)
                        {
                            Toast.makeText(Username_password.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    else
                        {
                            Boolean result = db.register(name,address,pin,mobile,email,username.getText().toString(),pass.getText().toString(),imagetostore);
                            if(result == true)
                            {
                                Toast.makeText(Username_password.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                finalwar.setVisibility(View.INVISIBLE);
                                Intent intent1 = new Intent(Username_password.this,Login_page.class);
                                startActivity(intent1);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Username_password.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        finalwar.setText("Passwords not matching");
                        finalwar.setVisibility(View.VISIBLE);
                         }
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data!=null && data.getData()!=null)
        {
            imagepath = data.getData();
            try {
                imagetostore  = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                profile_image.setImageBitmap(imagetostore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}