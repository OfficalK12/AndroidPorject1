package com.example.bt_tieuluan02;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import model.Booking;
import sqlite.HotelDBHelper;

public class BookingFragment extends Fragment {

    private RecyclerView recyclerViewBookings;
    private BookingAdapter bookingAdapter;
    private HotelDBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_booking, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        int loggedInUserId = prefs.getInt("userId", -1);


        recyclerViewBookings = rootView.findViewById(R.id.recyclerViewBookings);
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new HotelDBHelper(requireContext());

        loadBookingList();

        return rootView;
    }

    private void loadBookingList() {
        List<Booking> bookingList = getBookingsFromDB();
        bookingAdapter = new BookingAdapter(getContext(), bookingList);
        recyclerViewBookings.setAdapter(bookingAdapter);
    }

    private List<Booking> getBookingsFromDB() {
        SharedPreferences prefs = getContext().getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        int loggedInUserId = prefs.getInt("userId", -1);

        if (isLoggedIn && loggedInUserId != -1) {
            // Nếu đã đăng nhập → Lấy booking theo user
            return dbHelper.getBookingsByUserId(loggedInUserId);
        } else {
            // Nếu chưa đăng nhập → Lấy booking chưa có userId (tức userId == null hoặc -1)
            return dbHelper.getOfflineBookings();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bookingAdapter != null) {
            bookingAdapter.closeDB(); // Đóng DB khi fragment bị hủy
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
