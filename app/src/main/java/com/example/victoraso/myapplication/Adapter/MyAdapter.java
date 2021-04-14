package com.example.victoraso.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    List<Booking> bookings;

    public MyAdapter(Context c, ArrayList<Booking> bookings) {
        this.c = c;
        this.bookings = bookings;
    }

    public void setBookings(List<Booking> bookingsList) {
        this.bookings = bookingsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new MyHolder(view); //this returns our view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        holder.horseRider.setText(bookings.get(i).getHorseRider());
        holder.horseName.setText(bookings.get(i).getHorseName());

        holder.setItemClickListener(new ItemClickListener() {
            public void onItemClickListener(View v, int position) {
                Context context = c.getApplicationContext();
                CharSequence text = bookings.get(position).getHorseRider() + " - " + bookings.get(position).getHorseName() +
                        " - " + bookings.get(position).getPhone();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return bookings == null ? 0 : bookings.size();
    }
}

