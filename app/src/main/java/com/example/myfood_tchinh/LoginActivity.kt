package com.example.myfood_tchinh

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood_tchinh.databinding.ActivityLoginBinding
import com.example.myfood_tchinh.DatabaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BƯỚC 2: KHỞI TẠO BIẾN dbHelper Ở ĐÂY
        dbHelper = DatabaseHelper(this)




        // Xử lý sự kiện khi nhấn nút Đăng nhập
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // ----- PHẦN KIỂM TRA ĐĂNG NHẬP -----
            // Trong thực tế, bạn sẽ kiểm tra username và password với database hoặc API.
            // Ở đây, ta làm một ví dụ đơn giản: chỉ cần nhập đủ thông tin.
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener // Dừng xử lý nếu thông tin trống
            }
            if (dbHelper.checkUser(username, password)) {
                // Nếu thông tin hợp lệ, chuyển đến HomeActivity
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish() // Đóng màn hình Login để người dùng không quay lại được bằng nút Back
            } else {
                // Nếu thông tin không hợp lệ, thông báo lỗi
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT)
                    .show()
            }


            // Xử lý sự kiện khi nhấn vào chữ "Đăng kí"
            binding.tvRegister.setOnClickListener {
                // Chuyển đến màn hình RegisterActivity
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}