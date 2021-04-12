package com.example.victoraso.myapplication.AddActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.victoraso.myapplication.BookingActivity.BookingViewModel;
import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.Utils.Utils;
import com.example.victoraso.myapplication.databinding.ActivityAddBookingBinding;

import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBookingActivity extends AppCompatActivity {
    private final Calendar myCalendar = Calendar.getInstance();
    ActivityAddBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setTitle("Añadir reserva");

        ViewModelProvider.AndroidViewModelFactory factory
                = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        BookingViewModel vm = new ViewModelProvider(this, factory)
                .get(BookingViewModel.class);

        showCalendar();
        showHour();
        setupCreateButton(vm);
    }

    //TODO: ESTO SE PODRÏA PONER EN UNA MISMA CLASE Y RECICLARLO EN EL EDIT
    private void showCalendar() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        binding.dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddBookingActivity.this, date,
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

                new TimePickerDialog(AddBookingActivity.this,
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

    private void setupCreateButton(BookingViewModel vm) {
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
                    Booking bookingInsert = new Booking(name, phone, horseName, Utils.getDateTimestamp(date), Integer.parseInt(hour), comentary);
                    vm.insert(bookingInsert);

                    //SEND MESSAGE TO WHATSAPP
                    sendMessage(bookingInsert);

                    // Ir a la lista
                    finish();
                });
    }

    public void sendMessage(Booking booking) {
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String message = "Caballo: " + booking.getHorseName() + " | Fecha: " + booking.getDate() + " | Hora: " + booking.getHour();
        try {
            String url = "https://api.whatsapp.com/send?phone=" + "34" + booking.getPhone() +"&text=" + URLEncoder.encode(message, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}