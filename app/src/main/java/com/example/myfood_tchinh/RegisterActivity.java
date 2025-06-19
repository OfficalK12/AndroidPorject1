package com.example.myfood_tchinh;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfood_tchinh.databinding.ActivityRegisterBinding;
public class RegisterActivity extends AppCompatActivity{
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        // Thiết lập layout cho Activity
        setContentView(binding.getRoot());

        // Xử lý sự kiện khi nhấn nút Đăng kí
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });

        // Xử lý sự kiện khi nhấn vào chữ "Đăng nhập ngay"
        binding.tvLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay trở lại màn hình trước đó (LoginActivity)
                finish();
            }
        });
    }

    private void handleRegistration() {
        // Lấy dữ liệu người dùng nhập và xóa khoảng trắng thừa
        String username = binding.etUsernameRegister.getText().toString().trim();
        String password = binding.etPasswordRegister.getText().toString().trim();
        String rePassword = binding.etRepasswordRegister.getText().toString().trim();

        // 1. Kiểm tra các trường có bị bỏ trống không
        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return; // Dừng hàm tại đây
        }

        // 2. Kiểm tra mật khẩu và mật khẩu nhập lại có khớp không
        // Chú ý: Trong Java, so sánh chuỗi dùng phương thức .equals()
        if (!password.equals(rePassword)) {
            Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp. Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
            return; // Dừng hàm tại đây
        }

        // ---- XỬ LÝ ĐĂNG KÍ THÀNH CÔNG ----
        // Tại đây, bạn sẽ thực hiện lưu thông tin người dùng vào database.
        // Sau khi lưu thành công, ta sẽ thông báo và chuyển hướng.

        // Ví dụ: Thông báo đăng kí thành công
        Toast.makeText(RegisterActivity.this, "Đăng kí tài khoản thành công!", Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình đăng nhập
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        // Các cờ này giúp xóa các Activity trung gian (như RegisterActivity)
        // và tạo một task mới cho LoginActivity, người dùng không thể nhấn Back để quay lại.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

