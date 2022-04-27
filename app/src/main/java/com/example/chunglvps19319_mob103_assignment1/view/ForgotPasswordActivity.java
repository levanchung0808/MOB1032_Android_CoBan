package com.example.chunglvps19319_mob103_assignment1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.dao.UserDAO;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtUsername_Forgot;
    Button btnForgotPass;
    UserDAO userDAO;

    public static String USERNAME_FORGOT = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle("Quên mật khẩu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtUsername_Forgot = findViewById(R.id.edtUsername_ForgotPass);
        btnForgotPass = findViewById(R.id.btnForgotPass);

        userDAO = new UserDAO(ForgotPasswordActivity.this);

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername_Forgot.getText().toString();
                if(userDAO.checkUsernameExists(username)){
                    Intent intent = new Intent(ForgotPasswordActivity.this,ResetPasswordActivity.class);
                    USERNAME_FORGOT = username;
                    startActivity(intent);
                }else{
                    Toast.makeText(ForgotPasswordActivity.this, "Username không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}