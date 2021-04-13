package com.example.victoraso.myapplication.EditActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.Utils.Utils;
import com.example.victoraso.myapplication.databinding.ActivityEditBookingBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditBookingActivity extends AppCompatActivity {
    private final Calendar myCalendar = Calendar.getInstance();
    private ActivityEditBookingBinding binding;
    private EditBookingViewModel mViewModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setTitle("Editar reserva");

        ViewModelProvider.AndroidViewModelFactory factory
                = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        mViewModel = new ViewModelProvider(this, factory)
                .get(EditBookingViewModel.class);

        String idExtra = getIntent().getStringExtra("id");
        Toast.makeText(this, "ID: " + idExtra, Toast.LENGTH_LONG).show();
        if (idExtra.equals(null)) {
            finish();
        }

        id = Integer.parseInt(idExtra);

        mViewModel.start(id);
        subscribeToUi();
        showCalendar();
        showHour();
        setupUpdateButton();

    }

    private void subscribeToUi() {
        mViewModel.getmBooking().observe(this,
                booking -> {
                    setFields(booking);
                });
    }

    private void setupUpdateButton() {
        binding.createButton.setOnClickListener(
                view -> {
                    // Obtener valor del campo de texto
                    String name = binding.nameRiderField.getText().toString();
                    String phone = binding.phoneField.getText().toString();
                    String horseName = binding.nameHorseField.getText().toString();
                    String date = binding.dateField.getText().toString();
                    String hour = binding.hourField.getText().toString();
                    String comentary = binding.comentaryField.getText().toString();

                    // Ignorar acción si hay 0 caracteres
                    if (name.isEmpty() || phone.isEmpty() || horseName.isEmpty() || date.isEmpty() || hour.isEmpty() || comentary.isEmpty()) {
                        Toast.makeText(this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!Utils.checkPhone(phone)) {
                        Toast.makeText(this, "Tiene que ser un móvil real", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //CREAR OBJETO BOOKING
                    Booking bookingUpdate = new Booking(name, phone, horseName, Utils.getDateTimestamp(date), Integer.parseInt(hour), comentary);
                    bookingUpdate.setId(id);

                    mViewModel.update(bookingUpdate);

                    // Ir a la lista
                    finish();
                });
    }

    private void setFields(Booking booking) {
        binding.nameRiderField.setText(booking.getHorseRider());
        binding.phoneField.setText(booking.getPhone());
        binding.nameHorseField.setText(booking.getHorseName());
        binding.dateField.setText(Utils.getDateString(booking.getDate()));
        binding.hourField.setText(Integer.toString(booking.getHour()));
        binding.comentaryField.setText(booking.getComentary());
    }

    private void showCalendar() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        binding.dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Las horas disponibles son de 17 - 20", Toast.LENGTH_SHORT).show();
                new DatePickerDialog(EditBookingActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        binding.dateField.setText(sdf.format(myCalendar.getTime()));
    }

    private void showHour() {
        binding.hourField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = 0;

                new TimePickerDialog(EditBookingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (!Utils.checkHour(hourOfDay)) {
                                    Toast.makeText(getApplicationContext(), "Solo de 17 - 20", Toast.LENGTH_LONG).show();
                                } else {
                                    binding.hourField.setText(String.valueOf(hourOfDay));
                                }
                            }
                        }, hour, minute, true).show();
            }
        });
    }


}