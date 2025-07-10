package com.example.bt_tieuluan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class BookingDetail extends AppCompatActivity {

    TextView txtHotelName, txtHotelAddress, txtCheckIn, txtCheckOut, txtNights, txtTotalPrice, txtUserInfo;
    Button btnConfirmBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        // Ánh xạ view
        txtHotelName = findViewById(R.id.txtHotelName);
        txtHotelAddress = findViewById(R.id.txtHotelAddress);
        txtCheckIn = findViewById(R.id.txtCheckIn);
        txtCheckOut = findViewById(R.id.txtCheckOut);
        txtNights = findViewById(R.id.txtNights);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtUserInfo = findViewById(R.id.txtUserInfo);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        // Lấy dữ liệu từ intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        String checkIn = getIntent().getStringExtra("checkIn");
        String checkOut = getIntent().getStringExtra("checkOut");
        int roomId = getIntent().getIntExtra("roomId", -1);
        long bookingId = getIntent().getLongExtra("bookingId", -1);

        // Gán dữ liệu mẫu (có thể dùng roomId để truy vấn DB nếu cần)
        txtHotelName.setText("Khách sạn mẫu ABC");
        txtHotelAddress.setText("123 Đường ABC, Quận 1, TP.HCM");
        txtCheckIn.setText("Nhận phòng: " + checkIn);
        txtCheckOut.setText("Trả phòng: " + checkOut);
        txtNights.setText("Tổng thời gian lưu trú: 1 đêm");
        txtTotalPrice.setText("Giá: 1.654.200 VNĐ (đã bao gồm thuế và phí)");
        txtUserInfo.setText("Khách: " + name + "\nEmail: " + email + "\nSĐT: " + phone);

        btnConfirmBooking.setOnClickListener(v -> {
            Toast.makeText(this, "Đặt chỗ thành công! Mã đơn: #" + bookingId, Toast.LENGTH_LONG).show();
            finish(); // hoặc chuyển về màn chính
        });
    }
}
