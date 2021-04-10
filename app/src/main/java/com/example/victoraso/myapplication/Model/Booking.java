package com.example.victoraso.myapplication.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "booking_list")
public class Booking {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "horse_rider")
    private String horseRider;
    @NonNull
    @ColumnInfo(name = "phone")
    private String phone;
    @NonNull
    @ColumnInfo(name = "horse_name")
    private String horseName;
    @NonNull
    @ColumnInfo(name = "date")
    private String date;
    @NonNull
    @ColumnInfo(name = "hour")
    private int hour;
    @NonNull
    @ColumnInfo(name = "comentary")
    private String comentary;

    public Booking(@NonNull String horseRider, @NonNull String phone, @NonNull String horseName, @NonNull String date, int hour, @NonNull String comentary) {
        this.horseRider = horseRider;
        this.phone = phone;
        this.horseName = horseName;
        this.date = date;
        this.hour = hour;
        this.comentary = comentary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHorseRider() {
        return horseRider;
    }

    public void setHorseRider(String horseRider) {
        this.horseRider = horseRider;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getComentary() {
        return comentary;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }


}
