package com.example.bt_tieuluan02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Hotel;
import model.Room;
import sqlite.HotelDBHelper;
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Đảm bảo đây là layout chính

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        topAppBar = findViewById(R.id.topAppBar);

        // Đặt tiêu đề mặc định cho Toolbar
        topAppBar.setTitle("Booking.com");

        // Load Fragment mặc định khi ứng dụng khởi chạy (SearchFragment)
        if (savedInstanceState == null) {
            loadFragment(new SearchFragment());
        }

        // Lắng nghe sự kiện chọn item trên Bottom Navigation Bar
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                    topAppBar.setTitle("Tìm kiếm");
                } else if (itemId == R.id.nav_booking) {
                    selectedFragment = new BookingFragment();
                    topAppBar.setTitle("Đặt chỗ của tôi");
                } else if (itemId == R.id.nav_account) {
                    selectedFragment = new AccountFragment();
                    topAppBar.setTitle("Tài khoản");
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Hàm dùng để tải và hiển thị Fragment vào fragment_container
     * @param fragment Fragment muốn hiển thị
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        // transaction.addToBackStack(null); // Có thể thêm vào back stack nếu bạn muốn người dùng quay lại
        transaction.commit();
    }
}