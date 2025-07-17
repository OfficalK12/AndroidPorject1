package sqlite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import model.Booking;

public class HotelDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "hotel_booking";
    public static final int DATABASE_VERSION = 1;

    public HotelDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "full_name TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT)");

        db.execSQL("CREATE TABLE hotels (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "address TEXT, " +
                "city TEXT)");

        db.execSQL("CREATE TABLE rooms (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hotel_id INTEGER, " +
                "type TEXT, " +
                "price REAL, " +
                "capacity INTEGER)");

        db.execSQL("CREATE TABLE bookings (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "room_id INTEGER, " +
                "check_in TEXT, " +
                "check_out TEXT," +
                "payment_method TEXT," +
                "price_at_booking REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS hotels");
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS bookings");
        onCreate(db);
    }

    public List<Booking> getAllBookings() {
            List<Booking> bookings = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT _id, user_id, room_id, check_in, check_out, payment_method, price_at_booking FROM bookings ORDER BY _id DESC";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    int userId = cursor.getInt(1);
                    int roomId = cursor.getInt(2);
                    String checkIn = cursor.getString(3);
                    String checkOut = cursor.getString(4);
                    String paymentMethod = cursor.getString(5);
                    double priceAtBooking = cursor.getDouble(6);

                    Booking booking = new Booking(id, userId, roomId, checkIn, checkOut, paymentMethod, priceAtBooking);
                    bookings.add(booking);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            return bookings;
        }

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bookings WHERE user_id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                list.add(createBookingFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // 2. Lấy các booking không gắn user (người chưa đăng nhập)
    public List<Booking> getOfflineBookings() {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bookings WHERE user_id IS NULL OR user_id = -1", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(createBookingFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // 3. Tạo Booking từ Cursor
    private Booking createBookingFromCursor(Cursor cursor) {
        Booking booking = new Booking();
        booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        booking.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow("room_id")));
        booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))); // nên dùng int
        booking.setCheckIn(cursor.getString(cursor.getColumnIndexOrThrow("check_in")));
        booking.setCheckOut(cursor.getString(cursor.getColumnIndexOrThrow("check_out")));
        booking.setPaymentMethod(cursor.getString(cursor.getColumnIndexOrThrow("payment_method")));
        booking.setPriceAtBooking(cursor.getDouble(cursor.getColumnIndexOrThrow("price_at_booking")));
        return booking;
    }
    }