package com.example.bt_tieuluan02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import sqlite.HotelDBHelper;

public class ConfirmedBookingDetailActivity extends AppCompatActivity {

    TextView tvHotelName, tvHotelAddress, tvRoomType, tvRoomCapacity, tvCheckIn, tvCheckOut, tvPaymentMethod, tvTotalPrice, tvUserName;
    Button btnCancelBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_booking_detail);

        // Ánh xạ view
        tvHotelName = findViewById(R.id.tvConfirmDetailHotelName);
        tvHotelAddress = findViewById(R.id.tvConfirmDetailHotelAddress);
        tvRoomType = findViewById(R.id.tvConfirmDetailRoomType);
        tvRoomCapacity = findViewById(R.id.tvConfirmDetailRoomCapacity);
        tvUserName = findViewById(R.id.tvConfirmDetailUserName);
        tvCheckIn = findViewById(R.id.tvConfirmDetailCheckIn);
        tvCheckOut = findViewById(R.id.tvConfirmDetailCheckOut);
        tvPaymentMethod = findViewById(R.id.tvConfirmDetailPaymentMethod);
        tvTotalPrice = findViewById(R.id.tvConfirmDetailTotalPrice);
        btnCancelBooking = findViewById(R.id.btnCancelBooking);


        // Lấy dữ liệu từ intent
        int roomId = getIntent().getIntExtra("roomId", -1);
        int userId = getIntent().getIntExtra("userId", -1);
        int bookingId = getIntent().getIntExtra("bookingId", -1);
        String hotelName = "Không xác định";
        String hotelAddress = "Không xác định";
        String roomType = "Không xác định";
        String roomCapacity = "Không xác định";
        String fullName = getUserFullNameById(userId);
        String checkIn = getIntent().getStringExtra("checkIn");
        String checkOut = getIntent().getStringExtra("checkOut");
        String paymentMethod = getIntent().getStringExtra("paymentMethod");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0);


        if (roomId != -1) {
            HotelDBHelper dbHelper = new HotelDBHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Lấy type, capacity, hotel_id từ rooms
            Cursor roomCursor = db.rawQuery("SELECT type, capacity, hotel_id FROM rooms WHERE _id = ?",
                    new String[]{String.valueOf(roomId)});

            int hotelId = -1;
            if (roomCursor.moveToFirst()) {
                roomType = roomCursor.getString(0);
                roomCapacity = String.valueOf(roomCursor.getInt(1));
                hotelId = roomCursor.getInt(2);
            }
            roomCursor.close();

            // Lấy name, address từ hotels theo hotel_id
            if (hotelId != -1) {
                Cursor hotelCursor = db.rawQuery("SELECT name, address FROM hotels WHERE _id = ?",
                        new String[]{String.valueOf(hotelId)});

                if (hotelCursor.moveToFirst()) {
                    hotelName = hotelCursor.getString(0);
                    hotelAddress = hotelCursor.getString(1);
                }
                hotelCursor.close();
            }

            db.close();
        }

        // Gán dữ liệu lên UI
        tvHotelName.setText(hotelName != null ? hotelName : "Không xác định");
        tvHotelAddress.setText(hotelAddress != null ? hotelAddress : "Không xác định");
        tvRoomType.setText(roomType != null ? "Loại phòng: " + roomType : "Loại phòng: Không xác định");
        tvRoomCapacity.setText(roomCapacity != null ? "Sức chứa: " + roomCapacity : "Sức chứa: Không xác định");
        tvUserName.setText("Người đặt: " + fullName);
        tvCheckIn.setText("Ngày nhận phòng: " + (checkIn != null ? checkIn : "Không có"));
        tvCheckOut.setText("Ngày trả phòng: " + (checkOut != null ? checkOut : "Không có"));
        tvPaymentMethod.setText("Phương thức thanh toán: " + (paymentMethod != null ? paymentMethod : "Không có"));
        tvTotalPrice.setText("Tổng tiền: " + totalPrice + " VND");

        btnCancelBooking.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận hủy")
                    .setMessage("Bạn có chắc muốn hủy đặt chỗ này không?")
                    .setPositiveButton("Hủy", (d, w) -> {
                        int deleted = new HotelDBHelper(this)
                                .getWritableDatabase()
                                .delete("bookings", "_id = ?", new String[]{String.valueOf(bookingId)});
                        if (deleted > 0) {
                            Toast.makeText(this, "Đã hủy đặt chỗ", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Hủy không thành công", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

    }

        private String getUserFullNameById(int userId) {
            HotelDBHelper dbHelper = new HotelDBHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String fullName = "Không xác định";

            Cursor cursor = db.rawQuery("SELECT full_name FROM users WHERE _id = ?", new String[]{String.valueOf(userId)});
            if (cursor.moveToFirst()) {
                fullName = cursor.getString(0);
            }
            cursor.close();
            db.close();

            return fullName;
        }
}

