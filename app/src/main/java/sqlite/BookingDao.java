package sqlite;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import model.Booking;

public class BookingDao {
    private SQLiteDatabase db;
    public BookingDao(Context context) {
        HotelDBHelper helper = new HotelDBHelper(context);
        db = helper.getWritableDatabase();
    }
    public List<Booking> getAllBookings(Context context) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = new HotelDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bookings", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                int roomId = cursor.getInt(cursor.getColumnIndexOrThrow("room_id"));
                String checkIn = cursor.getString(cursor.getColumnIndexOrThrow("check_in"));
                String checkOut = cursor.getString(cursor.getColumnIndexOrThrow("check_out"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookingList;
    }

    public long insert(Booking booking) {
        ContentValues values = new ContentValues();
        values.put("user_id", booking.getUserId());
        values.put("room_id", booking.getRoomId());
        values.put("check_in", booking.getCheckIn());
        values.put("check_out", booking.getCheckOut());

        return db.insert("bookings", null, values);
    }

    public long update(Booking booking) {
        ContentValues values = new ContentValues();
        values.put("user_id", booking.getUserId());
        values.put("room_id", booking.getRoomId());
        values.put("check_in", booking.getCheckIn());
        values.put("check_out", booking.getCheckOut());

        return db.update("bookings", values, "_id = ?", new String[]{String.valueOf(booking.getId())});
    }

    public int delete(int id) {
        return db.delete("bookings", "_id = ?", new String[]{String.valueOf(id)});
    }
}
