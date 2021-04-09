package com.example.victoraso.myapplication.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

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

    @Transaction
    @Query("UPDATE booking_list " +
            "SET horse_rider =:horseRider, phone =:phone, horse_name =:horseName, date =:date, hour =:hour, comentary =:comentary " +
            "WHERE id = :id")
    public abstract int update(String id,
                               String horseRider,
                               String phone,
                               String horseName,
                               String date,
                               int hour,
                               String comentary
                               );

}
