package com.example.bt_tieuluan02;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sqlite.HotelDBHelper;

public class BookingInfo extends AppCompatActivity {
    EditText edtName, edtEmail, edtPhone, edtCheckIn, edtCheckOut;
    Button btnGoToConfirm;
    RadioGroup rgPaymentMethod;
    HotelDBHelper dbHelper;
    int userId = -1;
    int roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_and_payment);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtCheckIn = findViewById(R.id.edtCheckIn);
        edtCheckOut = findViewById(R.id.edtCheckOut);
        btnGoToConfirm = findViewById(R.id.btnGoToConfirm);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        dbHelper = new HotelDBHelper(this);
        roomId = getIntent().getIntExtra("roomId", -1);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String email = prefs.getString("email", null);

        if (email != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id, full_name FROM users WHERE email = ?", new String[]{email});
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(0);
                edtName.setText(cursor.getString(1));
                edtEmail.setText(email);
                edtEmail.setEnabled(false);
            }
            cursor.close();
            db.close();
        }

        btnGoToConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String emailInput = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String checkIn = edtCheckIn.getText().toString().trim();
            String checkOut = edtCheckOut.getText().toString().trim();

            int selectedPaymentId = rgPaymentMethod.getCheckedRadioButtonId();
            String paymentMethod = "";

            if (selectedPaymentId == R.id.rbCash) {
                paymentMethod = "Tiền mặt";
            } else if (selectedPaymentId == R.id.rbBankTransfer) {
                paymentMethod = "Chuyển khoản";
            } else {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }


            if (name.isEmpty() || emailInput.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

                String hotelName = "";
                String hotelAddress = "";
                double priceAtBooking = 0;

                SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
                Cursor cursor = dbRead.rawQuery(
                        "SELECT hotels.name, hotels.address, rooms.price " +
                                "FROM hotels INNER JOIN rooms ON hotels._id = rooms.hotel_id " +
                                "WHERE rooms._id = ?", new String[]{String.valueOf(roomId)}
                );

                if (cursor.moveToFirst()) {
                    hotelName = cursor.getString(0);
                    hotelAddress = cursor.getString(1);
                    priceAtBooking = cursor.getDouble(2);
                }
                cursor.close();
                dbRead.close();

                // Nếu chưa login, userId giữ nguyên -1 nhưng vẫn cho đặt
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("user_id", userId); // Nếu không login thì lưu -1
                values.put("room_id", roomId);
                values.put("check_in", checkIn);
                values.put("check_out", checkOut);
                values.put("payment_method", paymentMethod);
                values.put("price_at_booking", priceAtBooking);
                long bookingId = db.insert("bookings", null, values);
                db.close();

                if (bookingId != -1) {
                Intent intent = new Intent(this, BookingDetail.class);
                intent.putExtra("name", name);
                intent.putExtra("email", emailInput);
                intent.putExtra("phone", phone);
                intent.putExtra("checkIn", checkIn);
                intent.putExtra("checkOut", checkOut);
                intent.putExtra("roomId", roomId);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("hotelName", name);

                intent.putExtra("hotelName", hotelName);
                intent.putExtra("hotelAddress", hotelAddress);
                intent.putExtra("totalPrice", priceAtBooking);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Đặt phòng thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
