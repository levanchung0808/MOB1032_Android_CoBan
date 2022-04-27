package com.example.chunglvps19319_mob103_assignment1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.dao.UserDAO;
import com.example.chunglvps19319_mob103_assignment1.model.User;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnDangKy;
    UserDAO userDAO;
    ArrayList<User> arrUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Đăng ký tài khoản");

        edtUsername = findViewById(R.id.edtTaiKhoan_DK);
        edtPassword = findViewById(R.id.edtMatKhau_DK);
        btnDangKy = findViewById(R.id.btnDangKy_DK);

        userDAO = new UserDAO(RegisterActivity.this);
        arrUser = userDAO.getAll();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                User user = new User(username,password);

                for(int i=0;i<arrUser.size();i++){
                    if(username.equalsIgnoreCase(arrUser.get(i).getUsername())){
                        Toast.makeText(RegisterActivity.this, "Username đã tồn tại...", Toast.LENGTH_SHORT).show();
                        edtUsername.requestFocus();
                        return;
                    }
                }

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập username, password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userDAO.insert(user)){
                    Toast.makeText(RegisterActivity.this, "Thêm tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}