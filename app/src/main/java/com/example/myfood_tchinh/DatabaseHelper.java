package com.example.myfood_tchinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tên và phiên bản của Database
    private static final String DATABASE_NAME = "HotelBooking.db";
    private static final int DATABASE_VERSION = 1;

    // Tên các bảng
    public static final String TABLE_USERS = "users";
    public static final String TABLE_HOTELS = "hotels";
    public static final String TABLE_ROOMS = "rooms";
    public static final String TABLE_BOOKINGS = "bookings";

    // Các cột của bảng users
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_FULL_NAME = "full_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Các cột của bảng hotels
    public static final String COLUMN_HOTEL_ID = "_id";
    public static final String COLUMN_HOTEL_NAME = "name";
    public static final String COLUMN_HOTEL_ADDRESS = "address";
    public static final String COLUMN_HOTEL_CITY = "city";

    // Các cột của bảng rooms
    public static final String COLUMN_ROOM_ID = "_id";
    public static final String COLUMN_ROOM_HOTEL_ID = "hotel_id"; // Khóa ngoại
    public static final String COLUMN_ROOM_TYPE = "type";
    public static final String COLUMN_ROOM_PRICE = "price";
    public static final String COLUMN_ROOM_CAPACITY = "capacity";

    // Các cột của bảng bookings
    public static final String COLUMN_BOOKING_ID = "_id";
    public static final String COLUMN_BOOKING_USER_ID = "user_id"; // Khóa ngoại
    public static final String COLUMN_BOOKING_ROOM_ID = "room_id"; // Khóa ngoại
    public static final String COLUMN_BOOKING_CHECK_IN = "check_in";
    public static final String COLUMN_BOOKING_CHECK_OUT = "check_out";


    // Câu lệnh tạo bảng users
    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_FULL_NAME + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT UNIQUE, " +
                    COLUMN_USER_PASSWORD + " TEXT" +
                    ");";

    // Câu lệnh tạo bảng hotels
    private static final String TABLE_CREATE_HOTELS =
            "CREATE TABLE " + TABLE_HOTELS + " (" +
                    COLUMN_HOTEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_HOTEL_NAME + " TEXT, " +
                    COLUMN_HOTEL_ADDRESS + " TEXT, " +
                    COLUMN_HOTEL_CITY + " TEXT" +
                    ");";

    // Câu lệnh tạo bảng rooms
    private static final String TABLE_CREATE_ROOMS =
            "CREATE TABLE " + TABLE_ROOMS + " (" +
                    COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ROOM_HOTEL_ID + " INTEGER, " +
                    COLUMN_ROOM_TYPE + " TEXT, " +
                    COLUMN_ROOM_PRICE + " REAL, " +
                    COLUMN_ROOM_CAPACITY + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_ROOM_HOTEL_ID + ") REFERENCES " +
                    TABLE_HOTELS + "(" + COLUMN_HOTEL_ID + ")" +
                    ");";

    // Câu lệnh tạo bảng bookings
    private static final String TABLE_CREATE_BOOKINGS =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BOOKING_USER_ID + " INTEGER, " +
                    COLUMN_BOOKING_ROOM_ID + " INTEGER, " +
                    COLUMN_BOOKING_CHECK_IN + " TEXT, " +
                    COLUMN_BOOKING_CHECK_OUT + " TEXT, " +
                    "FOREIGN KEY(" + COLUMN_BOOKING_USER_ID + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_BOOKING_ROOM_ID + ") REFERENCES " +
                    TABLE_ROOMS + "(" + COLUMN_ROOM_ID + ")" +
                    ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thực thi các câu lệnh tạo bảng
        db.execSQL(TABLE_CREATE_USERS);
        db.execSQL(TABLE_CREATE_HOTELS);
        db.execSQL(TABLE_CREATE_ROOMS);
        db.execSQL(TABLE_CREATE_BOOKINGS);

        // Chèn dữ liệu mẫu
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Thêm người dùng
        addUser(db, "Nguyễn Văn A", "nguyenvana@email.com", "password123");
        addUser(db, "Trần Thị B", "tranthib@email.com", "password456");

        // Thêm khách sạn
        addHotel(db, "Khách sạn Grand Saigon", "8 Đồng Khởi, Bến Nghé, Quận 1", "Hồ Chí Minh");
        addHotel(db, "Khách sạn Metropole Hà Nội", "15 Ngô Quyền, Tràng Tiền, Hoàn Kiếm", "Hà Nội");
        addHotel(db, "Vinpearl Resort & Spa", "Hòn Tre, Vĩnh Nguyên", "Nha Trang");

        // Thêm phòng
        // Phòng cho Grand Saigon (hotel_id = 1)
        addRoom(db, 1, "Standard", 1500000.0, 2);
        addRoom(db, 1, "Deluxe", 2500000.0, 2);
        addRoom(db, 1, "Suite", 4000000.0, 4);

        // Phòng cho Metropole Hà Nội (hotel_id = 2)
        addRoom(db, 2, "Premium", 3000000.0, 2);
        addRoom(db, 2, "Grand Premium", 4500000.0, 3);

        // Phòng cho Vinpearl Nha Trang (hotel_id = 3)
        addRoom(db, 3, "Villa 2-Bedroom", 10000000.0, 4);
        addRoom(db, 3, "Bungalow", 7500000.0, 2);

        // Thêm đặt phòng
        // Nguyễn Văn A đặt phòng Deluxe ở Grand Saigon
        addBooking(db, 1, 2, "2024-08-10", "2024-08-12");
        // Trần Thị B đặt phòng Villa ở Vinpearl
        addBooking(db, 2, 6, "2024-09-01", "2024-09-05");
    }

    // Các hàm helper để thêm dữ liệu
    private void addUser(SQLiteDatabase db, String fullName, String email, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FULL_NAME, fullName);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
    }

    private void addHotel(SQLiteDatabase db, String name, String address, String city) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEL_NAME, name);
        values.put(COLUMN_HOTEL_ADDRESS, address);
        values.put(COLUMN_HOTEL_CITY, city);
        db.insert(TABLE_HOTELS, null, values);
    }

    private void addRoom(SQLiteDatabase db, int hotelId, String type, double price, int capacity) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_HOTEL_ID, hotelId);
        values.put(COLUMN_ROOM_TYPE, type);
        values.put(COLUMN_ROOM_PRICE, price);
        values.put(COLUMN_ROOM_CAPACITY, capacity);
        db.insert(TABLE_ROOMS, null, values);
    }

    private void addBooking(SQLiteDatabase db, int userId, int roomId, String checkIn, String checkOut) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKING_USER_ID, userId);
        values.put(COLUMN_BOOKING_ROOM_ID, roomId);
        values.put(COLUMN_BOOKING_CHECK_IN, checkIn);
        values.put(COLUMN_BOOKING_CHECK_OUT, checkOut);
        db.insert(TABLE_BOOKINGS, null, values);
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
}