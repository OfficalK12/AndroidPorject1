package com.example.bt_tieuluan02;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        String hotelName = getIntent().getStringExtra("hotelName");
        String hotelAddress = getIntent().getStringExtra("hotelAddress");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0);
        int roomId = getIntent().getIntExtra("roomId", -1);
        long bookingId = getIntent().getLongExtra("bookingId", -1);

        if (checkIn == null || checkOut == null) {
            // Gán giá trị mặc định hoặc show lỗi
            checkIn = "01/01/2025";
            checkOut = "02/01/2025";
        }

        // Gán dữ liệu mẫu (có thể dùng roomId để truy vấn DB nếu cần)
        txtHotelName.setText(hotelName);
        txtHotelAddress.setText(hotelAddress);
        txtCheckIn.setText("Nhận phòng: " + checkIn);
        txtCheckOut.setText("Trả phòng: " + checkOut);
        int nights = calculateNights(checkIn, checkOut);
        txtNights.setText("Tổng thời gian lưu trú: " + nights + "đêm");
        txtTotalPrice.setText("Giá: " + totalPrice + " VND");
        txtUserInfo.setText("Khách: " + name + "\nEmail: " + email + "\nSĐT: " + phone);

        btnConfirmBooking.setOnClickListener(v -> {
            Toast.makeText(this, "Đặt chỗ thành công! Mã đơn: #" + bookingId, Toast.LENGTH_LONG).show();
            // Tạo intent quay về màn chính
            Intent intent = new Intent(BookingDetail.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Xóa back stack để không quay lại BookingInfo
            startActivity(intent); // kết thúc BookingDetail
        });
    }

    private int calculateNights(String checkIn, String checkOut) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date dateIn = sdf.parse(checkIn);
            Date dateOut = sdf.parse(checkOut);
            long diff = dateOut.getTime() - dateIn.getTime();
            return (int) (diff / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
            return 1; // lỗi → giả định 1 đêm
        }
    }
}
