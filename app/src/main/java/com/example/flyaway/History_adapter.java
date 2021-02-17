package com.example.flyaway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class History_adapter extends RecyclerView.Adapter <History_adapter.holder>
{
    ArrayList<History_model>arrayList;
    Context context;

    public History_adapter(ArrayList<History_model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View  view = inflater.inflate(R.layout.custome_history,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.h_fn.setText(arrayList.get(position).getFlight_name());
        holder.h_sn.setText(arrayList.get(position).getSeat_no());

        holder.h_d.setText(arrayList.get(position).getDate());
        holder.h_t.setText(arrayList.get(position).getTime());
        holder.h_pn.setText(arrayList.get(position).getPassenger_name());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class  holder extends RecyclerView.ViewHolder{

        TextView h_fn,h_sn,h_un,h_d,h_t,h_pn;
        public holder(@NonNull View itemView) {
            super(itemView);

            h_fn = itemView.findViewById(R.id.h_fn);
            h_sn = itemView.findViewById(R.id.h_sn);
            h_d = itemView.findViewById(R.id.h_d);
            h_t = itemView.findViewById(R.id.h_t);
            h_pn = itemView.findViewById(R.id.h_pn);
        }
    }
}
