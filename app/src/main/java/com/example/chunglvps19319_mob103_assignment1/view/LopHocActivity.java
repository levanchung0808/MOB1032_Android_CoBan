package com.example.chunglvps19319_mob103_assignment1.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.adapter.LopHocAdapter;
import com.example.chunglvps19319_mob103_assignment1.dao.LopHocDAO;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LopHocActivity extends AppCompatActivity {

    ListView lvDanhSachLopHoc;
    FloatingActionButton btnThemMoiLopHoc;

    ArrayList<LopHoc> arrLopHoc = new ArrayList<>();
    LopHocAdapter adapter = null;
    LopHocDAO lhDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop_hoc);
        setTitle("Quản lý lớp học");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvDanhSachLopHoc = findViewById(R.id.lvDanhSachLopHoc);
        btnThemMoiLopHoc = findViewById(R.id.btnThemMoiLopHoc);

        lhDAO = new LopHocDAO(LopHocActivity.this);
        //hàm gọi all data
        arrLopHoc = lhDAO.getAll();

        adapter = new LopHocAdapter(LopHocActivity.this, arrLopHoc);
        lvDanhSachLopHoc.setAdapter(adapter);

        btnThemMoiLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog_AddNewLopHoc();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_lophoc, menu);
        MenuItem menuItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<LopHoc> arrLopHocMenu = new ArrayList<>();
                arrLopHoc = lhDAO.getAll();
                //duyệt nếu chuỗi nhập vào có trong maLopHoc thì add list
                for (LopHoc item : arrLopHoc) {
                    if (item.getMaLop().contains(s)) {
                        arrLopHocMenu.add(item);
                    } else if (item.getTenLop().contains(s)) {
                        arrLopHocMenu.add(item);
                    }
                }
                //refresh listview sau khi nhập
                ((LopHocAdapter) lvDanhSachLopHoc.getAdapter()).update(arrLopHocMenu);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void DiaLog_AddNewLopHoc() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_addlophoc);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //xử lý trong Dialog | ánh xạ
        Button btnCancel = dialog.findViewById(R.id.btnAddHuyLop);
        Button btnTaoLop = dialog.findViewById(R.id.btnAddTaoLop);
        EditText edtAddMaLop = dialog.findViewById(R.id.edtAddMaLop);
        EditText edtAddTenLop = dialog.findViewById(R.id.edtAddTenLop);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnTaoLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Insert data
                String maLop = edtAddMaLop.getText().toString();
                String tenLop = edtAddTenLop.getText().toString();

                if (!checkIsEmpty(edtAddMaLop) | !checkIsEmpty(edtAddTenLop)) {
                    Toast.makeText(LopHocActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tenHinh = "avartar_c";
                int resId = getResources().getIdentifier(tenHinh, "drawable", getPackageName());
                LopHoc lh = new LopHoc(maLop, tenLop, resId);
                //ktra trùng PRIMARY KEY
                for (int i = 0; i < arrLopHoc.size(); i++) {
                    if (maLop.equalsIgnoreCase(arrLopHoc.get(i).getMaLop())) {
                        Toast.makeText(LopHocActivity.this, "Mã lớp đã tồn tại vui lòng chọn mã lớp khác", Toast.LENGTH_LONG).show();
                        edtAddMaLop.requestFocus();
                        edtAddMaLop.setBackgroundColor(Color.parseColor("#FFFF00"));
                        return;
                    }
                }
                //Nếu chưa có thì thành công, ngược lại có r thì fail
                if (lhDAO.insert(lh)) {
                    Toast.makeText(LopHocActivity.this, "Thêm mới thành công", Toast.LENGTH_LONG).show();
                    arrLopHoc.clear();
                    arrLopHoc.addAll(lhDAO.getAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
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