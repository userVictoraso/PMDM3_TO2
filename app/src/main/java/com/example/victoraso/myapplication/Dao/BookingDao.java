package com.example.victoraso.myapplication.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.victoraso.myapplication.Model.Booking;

import java.util.List;


@Dao
public interface BookingDao {
    @Query("SELECT * FROM booking_list ORDER BY date DESC, hour DESC")
    LiveData<List<Booking>> getAll();

    @Query("SELECT * FROM booking_list WHERE date =:date AND hour =:hour")
    LiveData<List<Booking>> getSpecifiedBooking(long date, int hour);

    @Query("SELECT COUNT(*) FROM booking_list WHERE date =:date AND hour =:hour")
    LiveData<Integer> checkIfReserved(long date, int hour);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Booking booking);

    @Transaction
    @Query("SELECT * FROM booking_list WHERE id = :id")
    public abstract LiveData<Booking> booking(int id);

    @Delete
    void deleteBooking(Booking booking);

    @Update(entity = Booking.class)
    void updateBooking(Booking booking);

}
