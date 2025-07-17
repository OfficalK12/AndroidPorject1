package com.example.bt_tieuluan02;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;

public class RoomDetail extends AppCompatActivity {
    private TextView txtRoomName, txtRoomArea, txtRoomPrice, txtRoomFacilities;
    private ImageView imgRoom;
    private Button btnBookNow;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Button btnBack = findViewById(R.id.btnBackRoom);
        Button btnBookNow = findViewById(R.id.btnBookNow);

        txtRoomName = findViewById(R.id.txtRoomName);
        txtRoomArea = findViewById(R.id.txtRoomArea);
        txtRoomPrice = findViewById(R.id.txtRoomPrice);
        txtRoomFacilities = findViewById(R.id.txtRoomFacilities);
        imgRoom = findViewById(R.id.imgRoom);
        btnBookNow = findViewById(R.id.btnBookNow);


        // Lấy dữ liệu phòng từ intent
        String name = getIntent().getStringExtra("roomType");
        double price = getIntent().getDoubleExtra("roomPrice", 0);
        int capacity = getIntent().getIntExtra("roomCapacity", 2);

        // Hiển thị dữ liệu mẫu
        txtRoomName.setText(name);
        txtRoomPrice.setText("Giá: " + price);
        txtRoomArea.setText("Diện tích: 43m²");
        txtRoomFacilities.setText("WiFi • TV • Điều hòa • Ban công");

        btnBack.setOnClickListener(v -> finish());
        btnBookNow.setOnClickListener(v -> {
            Intent intent = new Intent(RoomDetail.this, BookingInfo.class);
            intent.putExtra("roomId", getIntent().getIntExtra("roomId", -1));
            intent.putExtra("roomType", getIntent().getStringExtra("roomType"));
            intent.putExtra("roomPrice", getIntent().getDoubleExtra("roomPrice", 0));
            intent.putExtra("roomCapacity", getIntent().getIntExtra("roomCapacity", 2));
            startActivity(intent);
            startActivity(intent);

        });
    }
}
