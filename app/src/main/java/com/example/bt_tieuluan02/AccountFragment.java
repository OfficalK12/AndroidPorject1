package com.example.bt_tieuluan02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AccountFragment extends Fragment {
    TextView tvAccountMessage; // TextView để hiển thị thông báo/tên người dùng
    Button btnLogout;
    Button btnLoginPrompt;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginSession"; // Tên file SharedPreferences
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";

    public AccountFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvAccountMessage = view.findViewById(R.id.tvAccountMessage);
        btnLoginPrompt = view.findViewById(R.id.btnLoginPrompt);
        btnLogout = view.findViewById(R.id.btnLogout);

        sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        btnLoginPrompt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Xóa tất cả dữ liệu session
                editor.apply(); // Cập nhật lại UI sau khi đăng xuất
                updateUIBasedOnLoginStatus(); // Cập nhật lại UI
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUIBasedOnLoginStatus();
    }
    private void updateUIBasedOnLoginStatus() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginSession", Context.MODE_PRIVATE);

        // Kiểm tra trạng thái đăng nhập trực tiếp từ SharedPreferences
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        String username = sharedPreferences.getString("username", "Người dùng");// Lấy tên người dùng

        if (isLoggedIn) {
            btnLoginPrompt.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            tvAccountMessage.setText("Xin chào, " + username + "!");
            tvAccountMessage.setTextSize(20);
            tvAccountMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            btnLoginPrompt.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            tvAccountMessage.setText("Đăng nhập để quản lý chuyến đi của bạn");
            tvAccountMessage.setTextSize(16);
            tvAccountMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateUIBasedOnLoginStatus();
        }
    }
}