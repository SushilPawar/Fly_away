package com.example.flyaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Seat_selection extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,aa,bb,cc,dd,booknow;
    EditText pa,pn,pm,pg,ps,ppa,ppn,ppm,ppg,pps,pppa,pppn,pppm,pppg,ppps,ppppa,ppppn,ppppm,ppppg,pppps;
    Spinner spinner;
    LinearLayout s1,s2,s3,s4;
    String username;
    SQLite_helper db;
    TextView flight_title;
    String fn,ft,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        db = new SQLite_helper(this);
        fn = getIntent().getStringExtra("flightname");
        ft = getIntent().getStringExtra("timing");
        date = getIntent().getStringExtra("date");
        findView();
        flight_title.setText(fn);
        SharedPreferences sharedPreferences = getSharedPreferences("session",MODE_PRIVATE);
         username = sharedPreferences.getString("username","");

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    private void findView() {

        booknow = findViewById(R.id.booknow);
        flight_title= findViewById(R.id.flight_title);
        pa =findViewById(R.id.p1a);
        pn =findViewById(R.id.p1n);
        pm =findViewById(R.id.p1m);
        pg =findViewById(R.id.p1g);
        ps =findViewById(R.id.p1s);

        ppa =findViewById(R.id.p2a);
        ppn =findViewById(R.id.p2n);
        ppm =findViewById(R.id.p2m);
        ppg =findViewById(R.id.p2g);
        pps =findViewById(R.id.p2s);

        pppa =findViewById(R.id.p3a);
        pppn =findViewById(R.id.p3n);
        pppm =findViewById(R.id.p3m);
        pppg =findViewById(R.id.p3g);
        ppps = findViewById(R.id.p3s);

        ppppa =findViewById(R.id.p4a);
        ppppn =findViewById(R.id.p4n);
        ppppm =findViewById(R.id.p4m);
        ppppg =findViewById(R.id.p4g);
        pppps =findViewById(R.id.p4s);

        s1 = findViewById(R.id.seat1);
        s2 = findViewById(R.id.seat2);
        s3 = findViewById(R.id.seat3);
        s4 = findViewById(R.id.seat4);
        spinner = findViewById(R.id.numberofpass);
        a =findViewById(R.id.A);b =findViewById(R.id.B);c =findViewById(R.id.C);d =findViewById(R.id.D);e =findViewById(R.id.E);
        f =findViewById(R.id.F);g =findViewById(R.id.G);h =findViewById(R.id.H);i =findViewById(R.id.I);j =findViewById(R.id.J);
        k =findViewById(R.id.K);l =findViewById(R.id.L);m =findViewById(R.id.M);n =findViewById(R.id.N);o =findViewById(R.id.O);
        p =findViewById(R.id.P);q =findViewById(R.id.Q);r =findViewById(R.id.R);s =findViewById(R.id.S);t =findViewById(R.id.T);
        u =findViewById(R.id.U);v =findViewById(R.id.V);w =findViewById(R.id.W);x =findViewById(R.id.X);y =findViewById(R.id.Y);
        z =findViewById(R.id.Z);aa =findViewById(R.id.AA);bb =findViewById(R.id.BB);cc =findViewById(R.id.CC);dd =findViewById(R.id.DD);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        if(text.equals("One"))
        {
            s1.setVisibility(View.VISIBLE);
            s2.setVisibility(View.GONE);
            s3.setVisibility(View.GONE);
            s4.setVisibility(View.GONE);
            booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pa.getText().toString().isEmpty() || pg.getText().toString().isEmpty()||pm.getText().toString().isEmpty()|| pn.getText().toString().isEmpty()||ps.getText().toString().isEmpty())
                    {
                        Toast.makeText(Seat_selection.this, "Please Fill Passengers All Details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int seat = Integer.parseInt(ps.getText().toString());
                        if(seat>30)
                        {
                            Toast.makeText(Seat_selection.this, "Seat Doesn't Exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(pm.getText().toString().length()<10)
                            {
                                Toast.makeText(Seat_selection.this, "Enter Valid Mobile no", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if(pg.getText().toString().equals("Male")|| pg.getText().toString().equals("Female")||pg.getText().toString().equals("male")||pg.getText().toString().equals("female"))
                                {
                                    Boolean seatavailable = db.checkseats(ps.getText().toString(),flight_title.getText().toString());
                                    if(seatavailable == true)
                                    {
                                        Toast.makeText(Seat_selection.this, "Seat no. "+ps.getText().toString()+" Already Booked!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Boolean status =  db.passengerdetails(pn.getText().toString(),ps.getText().toString(),pm.getText().toString(),pa.getText().toString(),pg.getText().toString(),username,fn,ft,date);
                                        if(status == true)
                                        {
                                            Toast.makeText(Seat_selection.this, "Flight Ticket Booked Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Seat_selection.this,Ticket_summary.class);
                                            intent.putExtra("flight_name",flight_title.getText().toString());
                                            intent.putExtra("seat",ps.getText().toString());
                                            intent.putExtra("username",username);
                                            intent.putExtra("time",ft);
                                            intent.putExtra("data",date);
                                            startActivity(intent);
                                            finish();

                                        }
                                        else
                                        {
                                            Toast.makeText(Seat_selection.this, "Unable to book Ticket", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                   
                                }
                                else
                                {
                                    Toast.makeText(Seat_selection.this, "Gender Doesn't Exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                }
            });
        }
        else if(text.equals("Two"))
        {
            s1.setVisibility(View.VISIBLE);
            s2.setVisibility(View.VISIBLE);
            s3.setVisibility(View.GONE);
            s4.setVisibility(View.GONE);
            booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pa.getText().toString().isEmpty() || pg.getText().toString().isEmpty()||pm.getText().toString().isEmpty()||
                            pn.getText().toString().isEmpty()||ps.getText().toString().isEmpty()||ppa.getText().toString().isEmpty() || ppg.getText().toString().isEmpty()||ppm.getText().toString().isEmpty()||
                            ppn.getText().toString().isEmpty()||pps.getText().toString().isEmpty())
                    {
                        Toast.makeText(Seat_selection.this, "Please Fill Passengers All Details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int seat = Integer.parseInt(ps.getText().toString());
                        int seat1 = Integer.parseInt(pps.getText().toString());
                        if(seat>30||seat1>30)
                        {
                            Toast.makeText(Seat_selection.this, "Seat Doesn't Exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(seat==seat1)
                            {
                                Toast.makeText(Seat_selection.this, "Please Select Different Seats", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if(pg.getText().toString().equals("Male")|| pg.getText().toString().equals("Female")||
                                        pg.getText().toString().equals("male")||pg.getText().toString().equals("female")||ppg.getText().toString().equals("Male")|| ppg.getText().toString().equals("Female")||
                                        ppg.getText().toString().equals("male")||ppg.getText().toString().equals("female"))
                                {
                                    Boolean seatchk = db.checkseats(ps.getText().toString(),flight_title.getText().toString());
                                    if(seatchk == true)
                                    {
                                        Toast.makeText(Seat_selection.this, "Seat no. "+ps.getText().toString()+" Already Booked!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Boolean seatchk1 = db.checkseats(pps.getText().toString(),flight_title.getText().toString());
                                        if(seatchk1 == true)
                                        {
                                            Toast.makeText(Seat_selection.this, "Seat no. "+pps.getText().toString()+" Already Booked!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            db.passengerdetails(pn.getText().toString(),ps.getText().toString(),pm.getText().toString(),pa.getText().toString(),pg.getText().toString(),username,fn,ft,date);
                                            Boolean status1 =  db.passengerdetails(ppn.getText().toString(),pps.getText().toString(),ppm.getText().toString(),ppa.getText().toString(),ppg.getText().toString(),username,fn,ft,date);
                                            if(status1 == true )
                                            {
                                                Intent intent = new Intent(Seat_selection.this,Ticket_summary.class);
                                                intent.putExtra("flight_name",flight_title.getText().toString());
                                                intent.putExtra("seat",ps.getText().toString()+","+pps.getText().toString());
                                                intent.putExtra("username",username);
                                                intent.putExtra("time",ft);
                                                intent.putExtra("data",date);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(Seat_selection.this, "NOT OK", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                }
                                else
                                {
                                    Toast.makeText(Seat_selection.this, "Gender Doesn't Exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                }
            });
        }
        else if(text.equals("Three"))
        {
            s1.setVisibility(View.VISIBLE);
            s2.setVisibility(View.VISIBLE);
            s3.setVisibility(View.VISIBLE);
            s4.setVisibility(View.GONE);
            booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pa.getText().toString().isEmpty() || pg.getText().toString().isEmpty()||pm.getText().toString().isEmpty()||
                            pn.getText().toString().isEmpty()||ps.getText().toString().isEmpty()||ppa.getText().toString().isEmpty() || ppg.getText().toString().isEmpty()||ppm.getText().toString().isEmpty()||
                            ppn.getText().toString().isEmpty()||pps.getText().toString().isEmpty()||pppa.getText().toString().isEmpty() ||
                            pppg.getText().toString().isEmpty()||pppm.getText().toString().isEmpty()||
                            pppn.getText().toString().isEmpty()||ppps.getText().toString().isEmpty())
                    {
                        Toast.makeText(Seat_selection.this, "Please Fill Passengers All Details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int seat = Integer.parseInt(ps.getText().toString());
                        int seat1 = Integer.parseInt(pps.getText().toString());
                        int seat2 = Integer.parseInt(ppps.getText().toString());
                        if(seat>30||seat1>30||seat2>30)
                        {
                            Toast.makeText(Seat_selection.this, "Seat Doesn't Exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(seat2==seat1 ||seat == seat1 || seat2 == seat )
                            {
                                Toast.makeText(Seat_selection.this, "Please Select Different Seats", Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                if(pppg.getText().toString().equals("Male")|| pppg.getText().toString().equals("Female")||
                                        pppg.getText().toString().equals("male")||pppg.getText().toString().equals("female"))
                                {
                                    Boolean schk = db.checkseats(ps.getText().toString(),flight_title.getText().toString());
                                    if(schk == true)
                                    {
                                        Toast.makeText(Seat_selection.this, "Seat no. "+ps.getText().toString()+" Already Booked!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Boolean schk1 = db.checkseats(pps.getText().toString(),flight_title.getText().toString());
                                        if(schk1 == true)
                                        {
                                            Toast.makeText(Seat_selection.this, "Seat no. "+pps.getText().toString()+" Already Booked!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Boolean schk2 = db.checkseats(ppps.getText().toString(),flight_title.getText().toString());
                                            if(schk2 == true)
                                            {
                                                Toast.makeText(Seat_selection.this, "Seat no. "+ppps.getText().toString()+" Already Booked!", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                db.passengerdetails(pn.getText().toString(),ps.getText().toString(),pm.getText().toString(),pa.getText().toString(),pg.getText().toString(),username,fn,ft,date);
                                                db.passengerdetails(ppn.getText().toString(),pps.getText().toString(),ppm.getText().toString(),ppa.getText().toString(),ppg.getText().toString(),username,fn,ft,date);
                                                Boolean status1 =  db.passengerdetails(pppn.getText().toString(),ppps.getText().toString(),pppm.getText().toString(),pppa.getText().toString(),pppg.getText().toString(),username,fn,ft,date);
                                                if(status1 == true)
                                                {
                                                    Intent intent = new Intent(Seat_selection.this,Ticket_summary.class);
                                                    intent.putExtra("flight_name",flight_title.getText().toString());
                                                    intent.putExtra("seat",ps.getText().toString()+","+pps.getText().toString()+","+ppps.getText().toString());
                                                    intent.putExtra("username",username);
                                                    intent.putExtra("time",ft);
                                                    intent.putExtra("data",date);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(Seat_selection.this, "Unable To book Flight", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Seat_selection.this, "Gender Doesn't Exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                }
            });
        }
        else if(text.equals("Four"))
        {
            s1.setVisibility(View.VISIBLE);
            s2.setVisibility(View.VISIBLE);
            s3.setVisibility(View.VISIBLE);
            s4.setVisibility(View.VISIBLE);
            booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pa.getText().toString().isEmpty() || pg.getText().toString().isEmpty()||pm.getText().toString().isEmpty()||
                            pn.getText().toString().isEmpty()||ps.getText().toString().isEmpty()||ppa.getText().toString().isEmpty() || ppg.getText().toString().isEmpty()||ppm.getText().toString().isEmpty()||
                            ppn.getText().toString().isEmpty()||pps.getText().toString().isEmpty()||
                            pppa.getText().toString().isEmpty() ||
                            pppg.getText().toString().isEmpty()||pppm.getText().toString().isEmpty()||
                            pppn.getText().toString().isEmpty()||ppps.getText().toString().isEmpty()||ppppa.getText().toString().isEmpty() ||
                            ppppg.getText().toString().isEmpty()||ppppm.getText().toString().isEmpty()||
                            ppppn.getText().toString().isEmpty()||pppps.getText().toString().isEmpty())
                    {
                        Toast.makeText(Seat_selection.this, "Please Fill Passengers All Details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int seat = Integer.parseInt(ps.getText().toString());
                        int seat1 = Integer.parseInt(pps.getText().toString());
                        int seat2 = Integer.parseInt(ppps.getText().toString());
                        int seat3 = Integer.parseInt(pppps.getText().toString());
                        if(seat>30||seat1>30||seat2>30||seat3>30)
                        {
                            Toast.makeText(Seat_selection.this, "Seat Doesn't Exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(seat2==seat1 ||seat == seat1 || seat2 == seat ||seat==seat3||seat1==seat3||seat2==seat3)
                            {
                                Toast.makeText(Seat_selection.this, "Please Select Different Seats", Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                if(ppppg.getText().toString().equals("Male")|| ppppg.getText().toString().equals("Female")||
                                        ppppg.getText().toString().equals("male")||ppppg.getText().toString().equals("female"))
                                {


                                    Boolean schk = db.checkseats(ps.getText().toString(),flight_title.getText().toString());
                                    if(schk == true)
                                    {
                                        Toast.makeText(Seat_selection.this, "Seat no. "+ps.getText().toString()+" Already Booked!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Boolean schk1 = db.checkseats(pps.getText().toString(), flight_title.getText().toString());
                                        if (schk1 == true) {
                                            Toast.makeText(Seat_selection.this, "Seat no. " + pps.getText().toString() + " Already Booked!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Boolean schk2 = db.checkseats(ppps.getText().toString(), flight_title.getText().toString());
                                            if (schk2 == true) {
                                                Toast.makeText(Seat_selection.this, "Seat no. " + ppps.getText().toString() + " Already Booked!", Toast.LENGTH_SHORT).show();
                                            } else
                                                {
                                                    Boolean schk3 = db.checkseats(pppps.getText().toString(),flight_title.getText().toString());
                                                    if(schk3 == true)
                                                    {
                                                        Toast.makeText(Seat_selection.this, "Seat no. " + pppps.getText().toString() + " Already Booked!", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        db.passengerdetails(pn.getText().toString(), ps.getText().toString(), pm.getText().toString(), pa.getText().toString(), pg.getText().toString(), username, fn, ft, date);
                                                        db.passengerdetails(ppn.getText().toString(), pps.getText().toString(), ppm.getText().toString(), ppa.getText().toString(), ppg.getText().toString(), username, fn, ft, date);
                                                        db.passengerdetails(pppn.getText().toString(), ppps.getText().toString(), pppm.getText().toString(), pppa.getText().toString(), pppg.getText().toString(), username, fn, ft, date);

                                                        Boolean status1 = db.passengerdetails(ppppn.getText().toString(), pppps.getText().toString(), ppppm.getText().toString(), ppppa.getText().toString(), ppppg.getText().toString(), username, fn, ft, date);
                                                        if (status1 == true) {
                                                            Intent intent = new Intent(Seat_selection.this, Ticket_summary.class);
                                                            intent.putExtra("flight_name", flight_title.getText().toString());
                                                            intent.putExtra("seat", ps.getText().toString() + "," + pps.getText().toString() + "," + ppps.getText().toString() + "," + pppps.getText().toString());
                                                            intent.putExtra("username", username);
                                                            intent.putExtra("time", ft);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(Seat_selection.this, "NOT OK", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                            }
                                        }
                                    }

                                }
                                else
                                {
                                    Toast.makeText(Seat_selection.this, "Gender Doesn't Exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}