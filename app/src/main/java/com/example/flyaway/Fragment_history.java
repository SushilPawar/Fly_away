package com.example.flyaway;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_history extends Fragment {
    ArrayList<History_model> arrayList;
    SQLite_helper db;
    History_adapter adapter;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_history.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_history newInstance(String param1, String param2) {
        Fragment_history fragment = new Fragment_history();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView =view.findViewById(R.id.rview);
        arrayList = new ArrayList<>();
        db = new SQLite_helper(getActivity());
        adapter = new History_adapter(displaydata(),getActivity());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayout);

        return view;
    }
    public ArrayList<History_model> displaydata()
    {
        SharedPreferences result = getActivity().getSharedPreferences("session",MODE_PRIVATE);
        String username = result.getString("username",null);
        arrayList = new ArrayList<>();
        Cursor cursor =db.history(username);
        if(cursor.getCount() == 0)
        {
            Toast.makeText(getActivity(),"No DATA FOUND",Toast.LENGTH_SHORT);
        }
        else {

            cursor.moveToFirst();
            do {
                History_model history=new History_model();
                history.setFlight_name(cursor.getString(cursor.getColumnIndex("flight_name")));
                history.setSeat_no(cursor.getString(cursor.getColumnIndex("seat_no")));
                history.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                history.setDate(cursor.getString(cursor.getColumnIndex("date")));
                history.setTime(cursor.getString(cursor.getColumnIndex("time")));
                history.setPassenger_name(cursor.getString(cursor.getColumnIndex("passenger_name")));
                arrayList.add(history);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}