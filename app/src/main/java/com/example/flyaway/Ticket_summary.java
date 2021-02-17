package com.example.flyaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ticket_summary extends AppCompatActivity {

    TextView fn,sn,ft,un;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_summary);

        fn = findViewById(R.id.fn);
        sn = findViewById(R.id.sn);
        ft = findViewById(R.id.ft);
        un = findViewById(R.id.un);
        done = findViewById(R.id.done);


        String name = getIntent().getStringExtra("flight_name");
        String seat = getIntent().getStringExtra("seat");
        String time = getIntent().getStringExtra("time");
        String user = getIntent().getStringExtra("username");

        fn.setText(name);
        sn.setText(seat);
        ft.setText(time);
        un.setText(user);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ticket_summary.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}