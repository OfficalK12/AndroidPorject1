package com.example.bttieuluan;

import android.database.Cursor;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.sqlite.SQLiteDatabase;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import model.Hotel;
import sqlite.HotelDBHelper;
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private HotelDBHelper dbHelper;

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
}