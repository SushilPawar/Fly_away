package com.example.flyaway;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

public class SQLite_helper extends SQLiteOpenHelper {

    private static final String db_name ="users.db";
    Context context;
    private byte[] imagetobyte;
    private byte[] update_imagetobyte;

    public SQLite_helper(@Nullable Context context)
    {
        super(context, db_name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE users(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT,pincode TEXT,mobile TEXT,email TEXT,username TEXT,password TEXT,image BLOB);";
        String query1 = "CREATE TABLE seats(seat_no TEXT,passenger_name TEXT,mobile TEXT,age TEXT,gender TEXT,username TEXT,flight_name TEXT,time TEXT,date TEXT); ";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS seats");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
    }

    public Boolean register(String name, String address, String pincode, String mobile, String email, String username, String password, Bitmap image)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap imagetostorebit  = image;
        imagetostorebit.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imagetobyte = byteArrayOutputStream.toByteArray();


        contentValues.put("name",name);
        contentValues.put("address",address);
        contentValues.put("pincode",pincode);
        contentValues.put("mobile",mobile);
        contentValues.put("email",email);
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("image",imagetobyte);

        long result = db.insert("users",null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Boolean passengerdetails(String name,String seat,String mobile,String age,String gender,String username,String flight,String time,String date)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("passenger_name",name);
        contentValues.put("seat_no",seat);
        contentValues.put("mobile",mobile);
        contentValues.put("age",age);
        contentValues.put("gender",gender);
        contentValues.put("username",username);
        contentValues.put("flight_name",flight);
        contentValues.put("time",time);
        contentValues.put("date",date);
        long result = db.insert("seats",null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean checkuser(String email)
    {
        SQLiteDatabase Mydb = this.getReadableDatabase();
        Cursor cursor = Mydb.rawQuery("SELECT * FROM users WHERE email= ?",new String[] {email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean checkusername(String username)
    {
        SQLiteDatabase Mydb = this.getReadableDatabase();
        Cursor cursor = Mydb.rawQuery("SELECT * FROM users WHERE username= ?",new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkusernamepassword(String username,String password)
    {
        SQLiteDatabase Mydb = this.getWritableDatabase();
        Cursor cursor = Mydb.rawQuery("SELECT * FROM users WHERE username = ? and password = ?",new String[] {username,password});
        if (cursor.getCount()>0)
            return true;
        else return false;
    }

    public void update_password(String username,String password)
    {
        SQLiteDatabase Mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long result = Mydb.update("users",contentValues,"username = ?",new String[]{username});

        if(result == -1)
        {
            Toast.makeText(context, "Failed to Update Password", Toast.LENGTH_SHORT).show();
        }
        else 
        {
            Toast.makeText(context, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void forgot_password(String username,String password)
    {
        SQLiteDatabase Mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long result = Mydb.update("users",contentValues,"username = ?",new String[]{username});

        if(result == -1)
        {
            Toast.makeText(context, "Failed to Update Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void update_profile(String username,String name,String address,String pincode,String mobile,String email)
    {
        try {
            SQLiteDatabase Mydb = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            Bitmap update_imagetostorebit  = image;
//            update_imagetostorebit.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//            update_imagetobyte = byteArrayOutputStream.toByteArray();

            contentValues.put("name",name);
            contentValues.put("address",address);
            contentValues.put("pincode",pincode);
            contentValues.put("mobile",mobile);
            contentValues.put("email",email);
           // contentValues.put("image",update_imagetobyte);

            long result = Mydb.update("users",contentValues,"username = ?",new String[]{username});

            if(result == -1)
            {
                Toast.makeText(context, "Failed to Update Profile", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor history(String username)
    {
        String query = "SELECT * FROM seats WHERE username = '"+username+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor updatepro(String username)
    {
        String query = "SELECT * FROM users WHERE username = '"+username+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



    public boolean checkseats(String seat,String flighname)
    {
        SQLiteDatabase Mydb = this.getReadableDatabase();
        Cursor cursor = Mydb.rawQuery("SELECT * FROM seats WHERE flight_name= ? AND seat_no = ?",new String[] {flighname,seat});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


}
