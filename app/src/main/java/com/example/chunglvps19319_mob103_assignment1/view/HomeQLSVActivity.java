package com.example.chunglvps19319_mob103_assignment1.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.dao.UserDAO;
import com.example.chunglvps19319_mob103_assignment1.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class HomeQLSVActivity extends AppCompatActivity {

    TextView tvUser;
    TextInputLayout edtMatKhauCu, edtMatKhauMoi, edtNhapLaiMatKhauMoi;
    ImageView ivQLLopHoc, ivQLSinhVien;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_qlsv);
        setTitle("Trang chủ quản lý sinh viên");

        AnhXa();

        ivQLLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeQLSVActivity.this, LopHocActivity.class);
                startActivity(intent);
            }
        });
        ivQLSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeQLSVActivity.this, SinhVienActivity.class);
                startActivity(intent);
            }
        });
        tvUser.setText("Xin chào: " + LoginActivity.USER);
        userDAO = new UserDAO(HomeQLSVActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qlsv_change_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_DoiMatKhau: {
                DiaLog_DoiMatKhau();
                break;
            }
            case R.id.action_DangXuat: {
                DiaLog_XacNhanDangXuat();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void AnhXa() {
        ivQLLopHoc = findViewById(R.id.ivHome_QLLopHoc);
        ivQLSinhVien = findViewById(R.id.ivHome_QLSinhVien);
        tvUser = findViewById(R.id.tvUser);
    }

    private void DiaLog_DoiMatKhau() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Đổi mật khẩu");
        dialog.setContentView(R.layout.dialog_custom_doimatkhau);
        dialog.show();

        //ánh xạ trong dialog
        edtMatKhauCu = dialog.findViewById(R.id.edtMatKhauCu);
        edtMatKhauMoi = dialog.findViewById(R.id.edtMatKhauMoi);
        edtNhapLaiMatKhauMoi = dialog.findViewById(R.id.edtNhapLaiMatKhauMoi);
        Button btnLuuMatKhau = dialog.findViewById(R.id.btnLuuMatKhau);
        Button btnCancel = dialog.findViewById(R.id.btnCancelMatKhau);

        String user = LoginActivity.USER;
        String pass = LoginActivity.PASS;
//        Toast.makeText(this, "user: " + user + " pass: " + pass, Toast.LENGTH_SHORT).show();

        btnLuuMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ktra input password k rỗng
                String mkCu = edtMatKhauCu.getEditText().getText().toString();
                String mkMoi = edtMatKhauMoi.getEditText().getText().toString();
                if (confirmInput()) {
                    if (mkCu.equals(pass)) {
                        if (mkMoi.equals(mkCu)) {
                            Toast.makeText(HomeQLSVActivity.this, "Mật khẩu mới vui lòng không trùng mật khẩu cũ", Toast.LENGTH_SHORT).show();
                        } else if (userDAO.changePassword(user, mkMoi)) {
                            Toast.makeText(HomeQLSVActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            DiaLog_XacNhanDangXuat();
                        } else {
                            Toast.makeText(HomeQLSVActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeQLSVActivity.this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeQLSVActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    private void DiaLog_XacNhanDangXuat() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Bạn có muốn đăng xuất?");

        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(HomeQLSVActivity.this, "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }

    private boolean confimMatKhau(String matKhau, TextInputLayout text) {
        if (matKhau.isEmpty()) {
            text.setError("Không được rỗng");
            return false;
        } else {
            text.setError(null);
            return true;
        }
    }

    private boolean confimPasswordSame() {
        String matKhauMoi = edtMatKhauMoi.getEditText().getText().toString().trim();
        String nhapLaiMatKhauMoi = edtNhapLaiMatKhauMoi.getEditText().getText().toString().trim();

        if (!matKhauMoi.matches(nhapLaiMatKhauMoi)) {
            Toast.makeText(this, "Mật khẩu và nhập lại phải giống nhau", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean confirmInput() {
        String matKhauCu = edtMatKhauCu.getEditText().getText().toString().trim();
        String matKhauMoi = edtMatKhauMoi.getEditText().getText().toString().trim();
        String nhapLaiMatKhauMoi = edtNhapLaiMatKhauMoi.getEditText().getText().toString().trim();
        if (!confimMatKhau(matKhauCu, edtMatKhauCu) | !confimMatKhau(matKhauMoi, edtMatKhauMoi) | !confimMatKhau(nhapLaiMatKhauMoi, edtNhapLaiMatKhauMoi) | !confimPasswordSame()) {
            return false;
        } else {
            return true;
        }
    }
}