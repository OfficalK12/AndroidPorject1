package sqlite;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import model.Room;

public class RoomDao {
    private SQLiteDatabase db;
    public RoomDao(Context context) {
        HotelDBHelper helper = new HotelDBHelper(context);
        db = helper.getWritableDatabase();
    }
    public List<Room> getAllRooms(Context context) {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = new HotelDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                int hotelId = cursor.getInt(cursor.getColumnIndexOrThrow("hotel_id"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int capacity = cursor.getInt(cursor.getColumnIndexOrThrow("capacity"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return roomList;
    }

    public long insert(Room room) {
        ContentValues values = new ContentValues();
        values.put("hotel_id", room.getHotelId());
        values.put("type", room.getType());
        values.put("price", room.getPrice());
        values.put("capacity", room.getCapacity());

        return db.insert("rooms", null, values);
    }

    public long update(Room room) {
        ContentValues values = new ContentValues();
        values.put("hotel_id", room.getHotelId());
        values.put("type", room.getType());
        values.put("price", room.getPrice());
        values.put("capacity", room.getCapacity());

        return db.update("rooms", values, "_id = ?", new String[]{String.valueOf(room.getId())});
    }

    public int delete(int id) {
        return db.delete("rooms", "_id = ?", new String[]{String.valueOf(id)});
    }
}
