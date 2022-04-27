package com.example.chunglvps19319_mob103_assignment1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.dao.UserDAO;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.example.chunglvps19319_mob103_assignment1.model.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextView tvBanKhongCoTaiKhoan, tvQuenMatKhau;
    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap;
    CheckBox cbLuuMatKhau;
    UserDAO userDAO;
    public static String USER = null;
    public static String PASS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng nhập hệ thống quản lý sinh viên");
        AnhXa();
        readRememberUser();

        userDAO = new UserDAO(LoginActivity.this);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtTaiKhoan.getText().toString();
                String password = edtMatKhau.getText().toString();
                // làm kiểu của cô checkLogin()
                User user = new User(username, password);
                if (userDAO.checkLogin(user)) {
                    //gán user là biến toàn cục
                    USER = username;
                    PASS = password;
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    rememberUser();
                    Intent intent = new Intent(LoginActivity.this, HomeQLSVActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username hoặc Password sai", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvBanKhongCoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        cbLuuMatKhau = findViewById(R.id.cbLuuMatKhau);
        tvBanKhongCoTaiKhoan = findViewById(R.id.tvBanKhongCoTaiKhoan);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
    }

    private void rememberUser(){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        String _user = edtTaiKhoan.getText().toString();
        String _password = edtMatKhau.getText().toString();
        boolean _cbLuu = cbLuuMatKhau.isChecked();
        if(!_cbLuu){
            edit.clear();
        }else{
            edit.putString("USERNAME",_user);
            edit.putString("PASSWORD",_password);
            edit.putBoolean("REMEMBER",_cbLuu);
        }
        edit.commit();
    }

    private void readRememberUser(){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user = pref.getString("USERNAME","");
        String pass = pref.getString("PASSWORD","");
        boolean cbLuu = pref.getBoolean("REMEMBER",false);
        edtTaiKhoan.setText(user);
        edtMatKhau.setText(pass);
        cbLuuMatKhau.setChecked(cbLuu);
    }


}