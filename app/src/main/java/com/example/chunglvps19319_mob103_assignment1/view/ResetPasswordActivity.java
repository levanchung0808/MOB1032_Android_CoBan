package com.example.chunglvps19319_mob103_assignment1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.dao.UserDAO;
import com.example.chunglvps19319_mob103_assignment1.model.User;

public class ResetPasswordActivity extends AppCompatActivity {

    TextView tvUsername;
    EditText edtPassword, edtConfirmPassword;
    Button btnXacNhan;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Đặt lại mật khẩu");
        tvUsername = findViewById(R.id.tvUsername_ResetPass);
        edtPassword = findViewById(R.id.edtPassword_ResetPass);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword_ResetPass);
        btnXacNhan = findViewById(R.id.btnXacNhan_ResetPass);

        String username = ForgotPasswordActivity.USERNAME_FORGOT;
        tvUsername.setText(username);

        userDAO = new UserDAO(ResetPasswordActivity.this);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confimPasswordSame() && passwordIsEmpty()){
                    if(userDAO.forgotPassword(username,edtPassword.getText().toString())){
                        Toast.makeText(ResetPasswordActivity.this, "Khôi phục mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    private boolean confimPasswordSame(){
        String matKhauMoi = edtPassword.getText().toString().trim();
        String nhapLaiMatKhauMoi = edtConfirmPassword.getText().toString().trim();

        if(!matKhauMoi.matches(nhapLaiMatKhauMoi)){
            Toast.makeText(this, "Mật khẩu và nhập lại phải giống nhau!", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean passwordIsEmpty(){
        String matKhauMoi = edtPassword.getText().toString().trim();
        String nhapLaiMatKhauMoi = edtConfirmPassword.getText().toString().trim();

        if(matKhauMoi.isEmpty() | nhapLaiMatKhauMoi.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}