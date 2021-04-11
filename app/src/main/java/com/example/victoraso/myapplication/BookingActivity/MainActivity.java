package com.example.victoraso.myapplication.BookingActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.victoraso.myapplication.Adapter.MyAdapter;
import com.example.victoraso.myapplication.Adapter.RecyclerTouchListener;
import com.example.victoraso.myapplication.AddActivity.AddBookingActivity;
import com.example.victoraso.myapplication.EditActivity.EditBookingActivity;
import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private BookingViewModel mViewModel;
    ActivityMainBinding binding;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());

        mViewModel = new ViewModelProvider(this, factory)
                .get(BookingViewModel.class);

        setUpList();
        setUpFAB();
        setUpUpdate();
    }

    public void setUpList() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
        myAdapter = new MyAdapter(this, null);
        binding.recyclerView.setAdapter(myAdapter);

        mViewModel.getBookings().observe(this, myAdapter::setBookings);
    }

    public void setUpFAB() {
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddBookingActivity.class));
            }
        });
    }

    public void setUpUpdate() {
        binding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener
                (getApplicationContext(), binding.recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        String id = String.valueOf(mViewModel.getBookings().getValue().get(position).getId());
                        Intent intent = new Intent(MainActivity.this, EditBookingActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        final Booking booking = mViewModel.getBookings().getValue().get(position);
                        AlertDialog dialog = new AlertDialog
                                .Builder(MainActivity.this)
                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mViewModel.delete(booking);
                                        Toast.makeText(getApplicationContext(), "Reserva eliminada", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setTitle("Confirmar")
                                .setMessage("¿Deseas eliminar la reserva de " + booking.getHorseRider() +
                                        " para las " + booking.getHour() + " horas con fecha " + booking.getDate() + "?")
                                .create();
                        dialog.show();
                    }
                }));
    }
}
