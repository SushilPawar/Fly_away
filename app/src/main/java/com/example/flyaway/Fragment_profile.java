package com.example.flyaway;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_profile extends Fragment {

    Button logout;
    LinearLayout up_profile,up_pass;
    ImageView profileimage;
    private Uri imagepath;
    private Bitmap imagetostore;
    private static final int PICK_IMAGE_REQUEST = 100;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_profile newInstance(String param1, String param2) {
        Fragment_profile fragment = new Fragment_profile();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        up_pass = view.findViewById(R.id.update_password);
        up_profile = view.findViewById(R.id.update_profile);
        profileimage = view.findViewById(R.id.profile_image);
        logout = view.findViewById(R.id.logout);

        SQLite_helper db = new SQLite_helper(getContext());

        SharedPreferences result = getActivity().getSharedPreferences("session",MODE_PRIVATE);
        String username = result.getString("username",null);

        Cursor cursor =db.updatepro(username);
        if(cursor.getCount() == 0)
        {
            Toast.makeText(getContext(),"No DATA FOUND",Toast.LENGTH_SHORT);
        }
        else {

            cursor.moveToFirst();
            do {
                byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                Bitmap objectbitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                profileimage.setImageBitmap(objectbitmap);
            }
            while (cursor.moveToNext());
        }
        db.close();

//        profileimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try
//                {
//                    Intent objectintent =new Intent();
//                    objectintent.setType("image/*");
//                    objectintent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(objectintent,PICK_IMAGE_REQUEST);
//                }
//                catch (Exception e)
//                {
//                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        up_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Update_profile.class);
                startActivity(intent);
            }
        });
        up_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Update_password.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("session",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember","false");
                editor.apply();
                Intent intent = new Intent(getActivity(),Login_page.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode ==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data!=null && data.getData()!=null)
//        {
//            imagepath = data.getData();
//            try {
//                imagetostore  = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagepath);
//                profileimage.setImageBitmap(imagetostore);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}