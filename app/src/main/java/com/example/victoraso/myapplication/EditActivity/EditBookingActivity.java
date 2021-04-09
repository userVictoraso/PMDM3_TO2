package com.example.victoraso.myapplication.EditActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.databinding.ActivityEditBookingBinding;

public class EditBookingActivity extends AppCompatActivity {
    private ActivityEditBookingBinding binding;
    private EditBookingViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewModelProvider.AndroidViewModelFactory factory
                = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        mViewModel = new ViewModelProvider(this, factory)
                .get(EditBookingViewModel.class);

        String id = getIntent().getStringExtra("id");
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
        if (id.equals(null)) {
            finish();
        }

        mViewModel.start(id);
        subscribeToUi();

        //TODO: TENEMOS QUE BORRAR LA ENTRADA DE LA BD Y AÑADIR UN NUEVO OBJETO; VA A SER MAS FÁCIL
    }

    private void subscribeToUi() {
        mViewModel.getmBooking().observe(this,
                booking -> {
                    setFields(booking);
                });
    }

    private void setFields(Booking booking) {
        binding.nameRiderField.setText(booking.getHorseRider());
        binding.phoneField.setText(booking.getPhone());
        binding.nameHorseField.setText(booking.getHorseName());
        binding.dateField.setText(booking.getDate());
        binding.hourField.setText(Integer.toString(booking.getHour()));
        binding.comentaryField.setText(booking.getComentary());
    }

}