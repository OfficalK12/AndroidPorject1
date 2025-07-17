package com.example.bt_tieuluan02;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import model.Room;
import sqlite.HotelDBHelper;

import java.util.ArrayList;
import java.util.List;

public class HotelDetail extends AppCompatActivity {
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
        int hotelId = getIntent().getIntExtra("hotelId", -1);

        // Hiển thị dữ liệu
        txtDetailName.setText(name);
        txtDetailAddress.setText(address);
        txtDetailCity.setText(city);

        // Tải danh sách phòng cho khách sạn này
        loadRooms(hotelId);

        // Xử lý sự kiện "Quay lại"
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity hiện tại → quay về Activity trước đó
            }
        });
    }

    private void loadRooms(int hotelId) {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = new HotelDBHelper(this).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT _id, type, price, capacity FROM rooms WHERE hotel_id = ?", new String[]{String.valueOf(hotelId)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String type = cursor.getString(1);
                double price = cursor.getDouble(2);
                int capacity = cursor.getInt(3);
                roomList.add(new Room(id, hotelId, type, price, capacity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        RecyclerView recyclerViewRooms = findViewById(R.id.recyclerRooms);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        RoomAdapter adapter = new RoomAdapter(this, roomList);
        recyclerViewRooms.setAdapter(adapter);
    }
}
