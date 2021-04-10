package com.example.victoraso.myapplication.EditActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.Repository.BookingRepository;

public class EditBookingViewModel extends AndroidViewModel {

    private final BookingRepository mRepository;

    private final MutableLiveData<String> mBookingId = new MutableLiveData<>();

    private LiveData<Booking> mBooking;

    public EditBookingViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BookingRepository(application);
        mBooking = Transformations.switchMap(
                mBookingId,
                mRepository::getBooking
        );
    }

    public void start(String id) {
        if(id.equals(mBookingId.getValue())) {
            return;
        }
        mBookingId.setValue(id);
    }

    public LiveData<Booking> getmBooking() {
        return mBooking;
    }

    public void update(Booking booking){
        mRepository.update(booking);
    }


}
