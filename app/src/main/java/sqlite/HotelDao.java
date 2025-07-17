package sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Hotel;

public class HotelDao {
    private SQLiteDatabase db;

    public HotelDao(Context context) {
        HotelDBHelper helper = new HotelDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<Hotel> getAllHotels(Context context) {
        List<Hotel> hotelList = new ArrayList<>();
        SQLiteDatabase db = new HotelDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hotels", null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));

                hotelList.add(new Hotel(id, name, address, city));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return hotelList;
    }

    public long insert(Hotel hotel) {
        ContentValues values = new ContentValues();
        values.put("id", hotel.getId());
        values.put("name", hotel.getName());
        values.put("address", hotel.getAddress());
        values.put("city", hotel.getCity());

        return db.insert("hotels", null, values);
    }

    public long update(Hotel hotel) {
        ContentValues values = new ContentValues();
        values.put("name", hotel.getName());
        values.put("address", hotel.getAddress());
        values.put("city", hotel.getCity());

        return db.update("hotels", values, "_id = ?", new String[]{String.valueOf(hotel.getId())});

    }

    public int delete(int id){
        return db.delete("hotels", "_id = ?", new String[]{String.valueOf(id)});
    }
}
