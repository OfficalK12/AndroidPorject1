package com.example.myfood_tchinh;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tên và phiên bản của Database
    private static final String DATABASE_NAME = "Food_DB.db";
    private static final int DATABASE_VERSION = 1;

    // Tên các bảng
    public static final String TABLE_USER = "user";
    public static final String TABLE_RESTAURANT = "restaurant";
    public static final String TABLE_FOOD = "food";

    // Các cột của bảng User
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Các cột của bảng Restaurant
    public static final String COLUMN_RESTAURANT_ID = "id";
    public static final String COLUMN_RESTAURANT_NAME = "name";
    public static final String COLUMN_RESTAURANT_ADDRESS = "address";
    public static final String COLUMN_RESTAURANT_PHONE = "phone";
    public static final String COLUMN_RESTAURANT_IMAGE = "image"; // Tên file ảnh trong drawable

    // Các cột của bảng Food
    public static final String COLUMN_FOOD_ID = "id";
    public static final String COLUMN_FOOD_NAME = "name";
    public static final String COLUMN_FOOD_PRICE = "price";
    public static final String COLUMN_FOOD_DESCRIPTION = "description";
    public static final String COLUMN_FOOD_IMAGE = "image"; // Tên file ảnh trong drawable
    public static final String COLUMN_FOOD_RESTAURANT_ID = "restaurant_id"; // Khóa ngoại

    // Câu lệnh tạo bảng User
    private static final String TABLE_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_USER_PASSWORD + " TEXT" +
                    ");";

    // Câu lệnh tạo bảng Restaurant
    private static final String TABLE_CREATE_RESTAURANT =
            "CREATE TABLE " + TABLE_RESTAURANT + " (" +
                    COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RESTAURANT_NAME + " TEXT, " +
                    COLUMN_RESTAURANT_ADDRESS + " TEXT, " +
                    COLUMN_RESTAURANT_PHONE + " TEXT, " +
                    COLUMN_RESTAURANT_IMAGE + " TEXT" +
                    ");";

    // Câu lệnh tạo bảng Food
    private static final String TABLE_CREATE_FOOD =
            "CREATE TABLE " + TABLE_FOOD + " (" +
                    COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FOOD_NAME + " TEXT, " +
                    COLUMN_FOOD_PRICE + " INTEGER, " +
                    COLUMN_FOOD_DESCRIPTION + " TEXT, " +
                    COLUMN_FOOD_IMAGE + " TEXT, " +
                    COLUMN_FOOD_RESTAURANT_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_FOOD_RESTAURANT_ID + ") REFERENCES " +
                    TABLE_RESTAURANT + "(" + COLUMN_RESTAURANT_ID + ")" +
                    ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thực thi các câu lệnh tạo bảng
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(TABLE_CREATE_RESTAURANT);
        db.execSQL(TABLE_CREATE_FOOD);

        // Chèn dữ liệu mẫu
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Thêm 5 user
        addUser(db, "user1", "123");
        addUser(db, "user2", "123");
        addUser(db, "user3", "123");
        addUser(db, "user4", "123");
        addUser(db, "user5", "123");

        // Thêm 5 nhà hàng
        addRestaurant(db, "Quán bánh mì cô Ba", "hẻm 68 Bùi Thị Xuân, quận Tân Bình", "0631335935", "banhmi");
        addRestaurant(db, "Phở Thìn Lò Đúc", "13 Lò Đúc, Hai Bà Trưng, Hà Nội", "0945148282", "pho");
        addRestaurant(db, "Bún Chả Hương Liên", "24 Lê Văn Hưu, Hai Bà Trưng, Hà Nội", "02439434106", "buncha");
        addRestaurant(db, "Cơm tấm Cali", "48 Nguyễn Huệ, Quận 1, TPHCM", "02838228229", "comtam");


        // Thêm 10 món ăn (gán vào các nhà hàng tương ứng bằng restaurant_id)
        // Món của quán cô Ba (id=1)
        addFood(db, "Bánh mì bò kho", 10000, "Size S", "img_food_bokho", 1);
        addFood(db, "Bánh mì bơ tỏi", 15000, "Size S", "img_food_botoi", 1);
        addFood(db, "Bánh mì chảo", 35000, "1 người ăn", "img_food_chao", 1);

        // Món của Phở Thìn (id=2)
        addFood(db, "Phở tái lăn", 60000, "Bát đầy đủ", "pho", 2);
        addFood(db, "Quẩy", 5000, "1 đĩa", "img_food_quay", 2);

        // Món của Bún chả Hương Liên (id=3)
        addFood(db, "Suất bún chả", 40000, "Obama combo", "buncha", 3);
        addFood(db, "Nem cua bể", 7000, "1 cái", "img_food_nemcua", 3);

        // Món của Cơm tấm Cali (id=4)
        addFood(db, "Cơm sườn bì chả", 55000, "Đĩa đặc biệt", "comtam", 4);

        // Món của Xôi Yến (id=5)
        addFood(db, "Xôi xéo", 15000, "Gói nhỏ", "xoi", 5);
        addFood(db, "Xôi gà nấm", 35000, "Bát đầy đủ", "xoi", 5);
    }

    // Các hàm helper để thêm dữ liệu cho gọn
    private void addUser(SQLiteDatabase db, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        values.put(COLUMN_USER_PASSWORD, password);
        db.insert(TABLE_USER, null, values);
    }

    private void addRestaurant(SQLiteDatabase db, String name, String address, String phone, String image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_NAME, name);
        values.put(COLUMN_RESTAURANT_ADDRESS, address);
        values.put(COLUMN_RESTAURANT_PHONE, phone);
        values.put(COLUMN_RESTAURANT_IMAGE, image);
        db.insert(TABLE_RESTAURANT, null, values);
    }

    private void addFood(SQLiteDatabase db, String name, int price, String desc, String image, int restaurantId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, name);
        values.put(COLUMN_FOOD_PRICE, price);
        values.put(COLUMN_FOOD_DESCRIPTION, desc);
        values.put(COLUMN_FOOD_IMAGE, image);
        values.put(COLUMN_FOOD_RESTAURANT_ID, restaurantId);
        db.insert(TABLE_FOOD, null, values);
    }
}