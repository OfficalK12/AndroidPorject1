package sqlite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                "check_out TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS hotels");
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS bookings");
        onCreate(db);
    }
}