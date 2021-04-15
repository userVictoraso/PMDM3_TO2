package com.example.victoraso.myapplication.BookingActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.Repository.BookingRepository;

import java.util.List;

public class BookingViewModel extends AndroidViewModel {

    private final BookingRepository mRepository;

    private LiveData<List<Booking>> mBookingLists;

    public BookingViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BookingRepository(application);
        mBookingLists = mRepository.getAllBookingLists();
    }

    public void setmBookingLists() {
        mBookingLists = mRepository.getAllBookingLists();
    }

    public LiveData<List<Booking>> getBookings() {
        return mBookingLists;
    }

    public LiveData<List<Booking>> getSpecifiedBooking(long date, int hour) {
        mBookingLists = mRepository.search(date, hour);
        return mBookingLists;
    }
    public void insert(Booking booking){
        mRepository.insert(booking);
    }

    public void delete(Booking booking) {
        mRepository.deleteBooking(booking);
    }

}
