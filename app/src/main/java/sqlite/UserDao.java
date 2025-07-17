package sqlite;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDao {
    private SQLiteDatabase db;

    public UserDao(Context context) {
        HotelDBHelper helper = new HotelDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<User> getAllUsers(Context context) {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = new HotelDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("full_name", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        return db.insert("users", null, values);
    }

    public long update(User user) {
        ContentValues values = new ContentValues();
        values.put("full_name", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        return db.update("users", values, "_id = ?", new String[]{String.valueOf(user.getId())});
    }

    public int delete(int id) {
        return db.delete("users", "_id = ?", new String[]{String.valueOf(id)});
    }
}
