package com.example.chunglvps19319_mob103_assignment1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.adapter.SinhVienAdapter;
import com.example.chunglvps19319_mob103_assignment1.dao.LopHocDAO;
import com.example.chunglvps19319_mob103_assignment1.dao.SinhVienDAO;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.example.chunglvps19319_mob103_assignment1.model.SinhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class SinhVienActivity extends AppCompatActivity {

    ListView lvDanhSachSinhVien;
    FloatingActionButton btnThemMoiSinhVien;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    ArrayList<SinhVien> arrSinhVien;
    SinhVienAdapter adapter = null;
    SinhVienDAO svDAO;

    ArrayList<LopHoc> arrLopHoc;
    LopHocDAO lhDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien);
        setTitle("Quản lý sinh viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvDanhSachSinhVien = findViewById(R.id.lvDanhSachSinhVien);
        btnThemMoiSinhVien = findViewById(R.id.btnThemMoiSinhVien);
        svDAO = new SinhVienDAO(SinhVienActivity.this);
        arrSinhVien = svDAO.getAll();

        lhDAO = new LopHocDAO(SinhVienActivity.this);
        arrLopHoc = lhDAO.getAll();

        adapter = new SinhVienAdapter(SinhVienActivity.this, arrSinhVien);
        lvDanhSachSinhVien.setAdapter(adapter);

        btnThemMoiSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog_AddNewSinhVien();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_sinhvien, menu);
        MenuItem menuItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<SinhVien> arrSinhVienMenu = new ArrayList<>();
                arrSinhVien = svDAO.getAll();
                //duyệt nếu chuỗi nhập vào có trong maLopHoc thì add list
                for (SinhVien item : arrSinhVien) {
                    if (item.getMaSv().contains(s)) {
                        arrSinhVienMenu.add(item);
                    } else if (item.getTenSv().contains(s)) {
                        arrSinhVienMenu.add(item);
                    } else if (item.getMaLopHoc().contains(s)) {
                        arrSinhVienMenu.add(item);
                    }
                }

                //refresh listview sau khi nhập
                ((SinhVienAdapter) lvDanhSachSinhVien.getAdapter()).update(arrSinhVienMenu);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {

        super.onResume();
        arrSinhVien.clear();
        arrSinhVien.addAll(svDAO.getAll());
        adapter.notifyDataSetChanged();
    }

    private void DiaLog_AddNewSinhVien() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_addsinhvien);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //xử lý trong Dialog // ánh xạ
        Button btnTaoSinhVien = dialog.findViewById(R.id.btnTaoSinhVien);
        Button btnHuySinhVien = dialog.findViewById(R.id.btnHuySinhVien);
        EditText edtMaSinhVienDialog = dialog.findViewById(R.id.edtMaSinhVienDialog);
        EditText edtTenSinhVienDialog = dialog.findViewById(R.id.edtTenSinhVienDialog);
        EditText edtNgaySinhDialog = dialog.findViewById(R.id.edtNgaySinhDialog);
        Spinner spMaLopSinhVienDialog = dialog.findViewById(R.id.spMaLopSinhVienDialog);

        ArrayAdapter adapter2 = new ArrayAdapter(SinhVienActivity.this, android.R.layout.simple_list_item_1, arrLopHoc);
        spMaLopSinhVienDialog.setAdapter(adapter2);

        btnTaoSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spMaLopSinhVienDialog.getCount() == 0) {
                    Toast.makeText(SinhVienActivity.this, "Không có mã lớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                String maSv = edtMaSinhVienDialog.getText().toString();
                String tenSv = edtTenSinhVienDialog.getText().toString();
                String ngaySinh = edtNgaySinhDialog.getText().toString();
                String maLop = spMaLopSinhVienDialog.getSelectedItem().toString();

                if (!checkIsEmpty(edtMaSinhVienDialog) | !checkIsEmpty(edtTenSinhVienDialog) | !checkIsEmpty(edtNgaySinhDialog)) {
                    Toast.makeText(SinhVienActivity.this, "Vui lòng nhập đủ thông tin để thêm", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!checkDate(ngaySinh)) {
                    Toast.makeText(SinhVienActivity.this, "Nhập sai định dạng ngày (yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
                    edtNgaySinhDialog.requestFocus();
                    edtNgaySinhDialog.setBackgroundColor(Color.parseColor("#FFFF00"));
                    return;
                }

                //random ảnh avatar
                Random rand = new Random();
                int rndInt = rand.nextInt(5) + 1; // bound: số lượng ảnh, bắt đầu là 1
                String tenHinh = "avatar" + rndInt;
                int resId = getResources().getIdentifier(tenHinh, "drawable", getPackageName());

                for (int i = 0; i < arrSinhVien.size(); i++) {
                    if (maSv.equalsIgnoreCase(arrSinhVien.get(i).getMaSv())) {
                        Toast.makeText(SinhVienActivity.this, "Mã sinh viên đã tồn tại vui lòng chọn mã lớp khác", Toast.LENGTH_LONG).show();
                        edtMaSinhVienDialog.requestFocus();
                        edtMaSinhVienDialog.setBackgroundColor(Color.parseColor("#FFFF00"));
                    }
                }

                try {
                    SinhVien sv = new SinhVien(maSv, tenSv, sdf.parse(ngaySinh), resId, maLop);
                    if (svDAO.insert(sv)) {
                        Toast.makeText(SinhVienActivity.this, "Thêm mới thành công", Toast.LENGTH_LONG).show();
                        arrSinhVien.clear();
                        arrSinhVien.addAll(svDAO.getAll());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnHuySinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    private boolean checkDate(String str) {
        String pattern = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";
        if (str.matches(pattern)) {
            return true;
        }
        return false;
    }

    private boolean checkIsEmpty(EditText edt) {
        String _edt = edt.getText().toString();
        edt.setBackgroundColor(Color.parseColor("#FFFFFF"));
        if (_edt.isEmpty()) {
            edt.setBackgroundColor(Color.parseColor("#FFFF00"));
            return false;
        }
        return true;
    }
}