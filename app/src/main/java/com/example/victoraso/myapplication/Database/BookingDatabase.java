package com.example.victoraso.myapplication.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.victoraso.myapplication.Dao.BookingDao;
import com.example.victoraso.myapplication.Model.Booking;
import com.example.victoraso.myapplication.Utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Booking.class}, version = 1, exportSchema = false)
public abstract class BookingDatabase extends RoomDatabase {

    public abstract BookingDao bookingListDao();

    private static final String DATABSE_NAME = "booking_db";

    private static BookingDatabase INSTANCE;

    private static final int THREADS = 4;

    /**Declaramos un ExecutorService para ejecutar las operaciones de bases de datos en otros hilos de trabajo y por ende no entorpecer la UI.**/
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

                Booking bookingOne = new Booking("Victor", "634895012", "Perdigón", Utils.getDateTimestamp("19/04/2021"), 20, "Hola");
                Booking bookingTwo = new Booking("Ismael", "634234012", "ColaCao", Utils.getDateTimestamp("15/04/2021"), 18, "Hola");
                Booking bookingThree = new Booking("Bea", "634878912", "Galleta", Utils.getDateTimestamp("17/04/2021"), 17, "Hola");
                Booking bookingFour = new Booking("Sonia", "636495012", "Bola", Utils.getDateTimestamp("12/04/2021"), 20, "Hola");
                Booking bookingFive = new Booking("Juan", "634895187", "Rápido", Utils.getDateTimestamp("14/04/2021"), 17, "Hola");


                dao.insert(bookingOne);
                dao.insert(bookingTwo);
                dao.insert(bookingThree);
                dao.insert(bookingFour);
                dao.insert(bookingFive);
            });
        }
    };
}
