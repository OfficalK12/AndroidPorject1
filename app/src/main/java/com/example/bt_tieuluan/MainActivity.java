package com.example.bt_tieuluan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.database.sqlite.SQLiteDatabase;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Hotel;
import model.Room;
import sqlite.HotelDBHelper;
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private HotelDBHelper dbHelper;
    private EditText edtLocation;
    private Button btnSearch;
    private List<Room> roomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HotelDBHelper helper = new HotelDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        recyclerView = findViewById(R.id.recyclerViewHotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelList = new ArrayList<>();
        dbHelper = new HotelDBHelper(this);

        loadDataFromDatabase();

        hotelAdapter = new HotelAdapter(this, hotelList);
        recyclerView.setAdapter(hotelAdapter);

        edtLocation = findViewById(R.id.edtLocation);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            String location = edtLocation.getText().toString().trim();
            searchHotelsByLocation(location);
        });

        EditText editDateRange = findViewById(R.id.edtDateRange);
        editDateRange.setOnClickListener(v -> showDateRangePicker());

        EditText editGuestRoom = findViewById(R.id.edtRoomInfo);
        editGuestRoom.setOnClickListener(v -> showGuestRoomDialog());


    }

    private void loadDataFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT _id, name, address, city FROM hotels", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String city = cursor.getString(3);

                hotelList.add(new Hotel(id, name, address, city));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
    private void searchHotelsByLocation(String location) {
        hotelList.clear(); // Xóa dữ liệu cũ

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, name, address, city FROM hotels WHERE city LIKE ?",
                new String[]{"%" + location + "%"});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String city = cursor.getString(3);

                hotelList.add(new Hotel(id, name, address, city));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        hotelAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    private void showDateRangePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog startPicker = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String startDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);

                    DatePickerDialog endPicker = new DatePickerDialog(this,
                            (view1, year1, month1, dayOfMonth1) -> {
                                String endDate = String.format("%02d/%02d/%d", dayOfMonth1, month1 + 1, year1);
                                EditText editDateRange = findViewById(R.id.edtDateRange);
                                editDateRange.setText(startDate + " - " + endDate);
                            },
                            year, month, dayOfMonth + 1); // default: hôm sau

                    endPicker.setTitle("Chọn ngày kết thúc");
                    endPicker.show();

                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        startPicker.setTitle("Chọn ngày bắt đầu");
        startPicker.show();
    }

    private void showGuestRoomDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_guest_room, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        TextView tvRoom = dialogView.findViewById(R.id.tvRoomCount);
        TextView tvAdult = dialogView.findViewById(R.id.tvAdultCount);
        TextView tvChild = dialogView.findViewById(R.id.tvChildCount);

        Button btnRoomMinus = dialogView.findViewById(R.id.btnRoomMinus);
        Button btnRoomPlus = dialogView.findViewById(R.id.btnRoomPlus);
        Button btnAdultMinus = dialogView.findViewById(R.id.btnAdultMinus);
        Button btnAdultPlus = dialogView.findViewById(R.id.btnAdultPlus);
        Button btnChildMinus = dialogView.findViewById(R.id.btnChildMinus);
        Button btnChildPlus = dialogView.findViewById(R.id.btnChildPlus);

        btnRoomMinus.setOnClickListener(v -> {
            int val = Math.max(1, Integer.parseInt(tvRoom.getText().toString()) - 1);
            tvRoom.setText(String.valueOf(val));
        });
        btnRoomPlus.setOnClickListener(v -> {
            int val = Integer.parseInt(tvRoom.getText().toString()) + 1;
            tvRoom.setText(String.valueOf(val));
        });

        btnAdultMinus.setOnClickListener(v -> {
            int val = Math.max(1, Integer.parseInt(tvAdult.getText().toString()) - 1);
            tvAdult.setText(String.valueOf(val));
        });
        btnAdultPlus.setOnClickListener(v -> {
            int val = Integer.parseInt(tvAdult.getText().toString()) + 1;
            tvAdult.setText(String.valueOf(val));
        });

        btnChildMinus.setOnClickListener(v -> {
            int val = Math.max(0, Integer.parseInt(tvChild.getText().toString()) - 1);
            tvChild.setText(String.valueOf(val));
        });
        btnChildPlus.setOnClickListener(v -> {
            int val = Integer.parseInt(tvChild.getText().toString()) + 1;
            tvChild.setText(String.valueOf(val));
        });

        builder.setPositiveButton("Áp dụng", (dialog, which) -> {
            String result = tvRoom.getText() + " phòng · " +
                    tvAdult.getText() + " người lớn · " +
                    tvChild.getText() + " trẻ em";
            EditText editGuestRoom = findViewById(R.id.edtRoomInfo);
            editGuestRoom.setText(result);
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

}