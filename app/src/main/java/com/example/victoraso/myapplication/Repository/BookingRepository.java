package com.example.victoraso.myapplication.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.victoraso.myapplication.Dao.BookingDao;
import com.example.victoraso.myapplication.Database.BookingDatabase;
import com.example.victoraso.myapplication.Model.Booking;

import java.util.List;

public class BookingRepository {
    private final LiveData<List<Booking>> mBookingLists;
    private final BookingDao mBookingListDao;

    public BookingRepository(Context context) {
        BookingDatabase db = BookingDatabase.getInstance(context);
        mBookingListDao = db.bookingListDao();
        mBookingLists = mBookingListDao.getAll();
    }

    public LiveData<List<Booking>> getAllBookingLists(){return mBookingLists;}

    public void insert(Booking booking) {
        BookingDatabase.dbExecutor.execute(() -> mBookingListDao.insert(booking));
    }

    public LiveData<List<Booking>> search(long date, int hour) {
        return mBookingListDao.getSpecifiedBooking(date, hour);
    }

    public void deleteBooking(Booking booking) {
        BookingDatabase.dbExecutor.execute(
                () -> mBookingListDao.deleteBooking(booking)
        );
    }

    public void update(Booking booking) {
        BookingDatabase.dbExecutor.execute(() -> mBookingListDao.updateBooking(booking));
    }

    public LiveData<Booking> getBooking(int id) {
        return mBookingListDao.booking(id);
    }
}
