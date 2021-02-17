package com.example.flyaway;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Update_profile extends AppCompatActivity {

    EditText name,address,pin,mobile,email;
    Button update;
    SQLite_helper db;
    ImageView update_dp;
    private Uri imagepath;
    private Bitmap update_imagetostore;
    private static final int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        name = findViewById(R.id.upname);
        address = findViewById(R.id.upaddress);
        pin = findViewById(R.id.uppincode);
        mobile = findViewById(R.id.upmobile);
        email = findViewById(R.id.upemail);
        update= findViewById(R.id.upprofilebtn);
        update_dp = findViewById(R.id.update_profile_image);
        db = new SQLite_helper(this);

            SharedPreferences result = getSharedPreferences("session",MODE_PRIVATE);
            String username = result.getString("username",null);


            Cursor cursor =db.updatepro(username);
            if(cursor.getCount() == 0)
            {
                Toast.makeText(this,"No DATA FOUND",Toast.LENGTH_SHORT);
            }
            else {

                cursor.moveToFirst();
                do {
                    byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                    Bitmap objectbitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                    update_dp.setImageBitmap(objectbitmap);
                    name.setText(cursor.getString(cursor.getColumnIndex("name")));
                    address.setText(cursor.getString(cursor.getColumnIndex("address")));
                    mobile.setText(cursor.getString(cursor.getColumnIndex("mobile")));
                    pin.setText(cursor.getString(cursor.getColumnIndex("pincode")));
                    email.setText(cursor.getString(cursor.getColumnIndex("email")));
                }
                while (cursor.moveToNext());
            }
            db.close();


            update_dp.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(Update_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().isEmpty()||address.getText().toString().isEmpty()||mobile.getText().toString().isEmpty()||pin.getText().toString().isEmpty()||email.getText().toString().isEmpty())
                {
                    Toast.makeText(Update_profile.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String emailPattern = "[a-z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(email.getText().toString().matches(emailPattern))
                    {
                        db.update_profile(username,name.getText().toString(),address.getText().toString(),pin.getText().toString(),mobile.getText().toString(),email.getText().toString());
                        finish();
                        startActivity(getIntent());

                    }
                    else
                        {
                            Toast.makeText(Update_profile.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                        }

                     }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data!=null && data.getData()!=null)
        {
            imagepath = data.getData();
            try {
                update_imagetostore  = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                update_dp.setImageBitmap(update_imagetostore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}