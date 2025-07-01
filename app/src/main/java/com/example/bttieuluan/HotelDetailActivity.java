package com.example.bttieuluan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HotelDetailActivity extends AppCompatActivity {



    private TextView txtDetailName, txtDetailAddress, txtDetailCity;
    private Button btnBack; // Thêm dòng này

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        txtDetailName = findViewById(R.id.txtDetailName);
        txtDetailAddress = findViewById(R.id.txtDetailAddress);
        txtDetailCity = findViewById(R.id.txtDetailCity);
        btnBack = findViewById(R.id.btnBack); // Khởi tạo nút "Quay lại"

        // Lấy dữ liệu từ Intent
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String city = getIntent().getStringExtra("city");

        // Hiển thị dữ liệu
        txtDetailName.setText(name);
        txtDetailAddress.setText(address);
        txtDetailCity.setText(city);

        // Xử lý sự kiện "Quay lại"
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity hiện tại → quay về Activity trước đó
            }
        });
    }
}