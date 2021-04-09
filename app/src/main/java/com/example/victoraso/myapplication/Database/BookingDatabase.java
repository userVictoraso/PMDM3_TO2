package com.example.victoraso.myapplication.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.victoraso.myapplication.Dao.BookingDao;
import com.example.victoraso.myapplication.Model.Booking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Booking.class}, version = 1, exportSchema = false)
public abstract class BookingDatabase extends RoomDatabase {

    public abstract BookingDao bookingListDao();

    private static final String DATABSE_NAME = "booking_db";

    private static BookingDatabase INSTANCE;

    private static final int THREADS = 4;

    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(THREADS);

    public static BookingDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookingDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), BookingDatabase.class, DATABSE_NAME)
                            .addCallback(mRoomCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback mRoomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbExecutor.execute(() -> {
                BookingDao dao = INSTANCE.bookingListDao();

                Booking bookingOne = new Booking("1", "Victor", "777", "Perdigón", "12-12-12", 17, "Hola");
                Booking bookingTwo = new Booking("2" , "Ismael", "777", "Perdigón", "12-12-12", 17, "Hola");

                dao.insert(bookingOne);
                dao.insert(bookingTwo);
            });
        }
    };
}
