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
    @Query("SELECT * FROM booking_list")
    LiveData<List<Booking>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Booking booking);

    @Transaction
    @Query("SELECT * FROM booking_list WHERE id = :id")
    public abstract LiveData<Booking> booking(String id);

    @Delete
    void deleteBooking(Booking booking);

    @Update
    public void updateBooking(Booking booking);

}
