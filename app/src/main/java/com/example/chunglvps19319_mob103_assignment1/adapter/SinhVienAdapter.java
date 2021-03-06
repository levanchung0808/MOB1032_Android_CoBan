package com.example.chunglvps19319_mob103_assignment1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.dao.LopHocDAO;
import com.example.chunglvps19319_mob103_assignment1.dao.SinhVienDAO;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.example.chunglvps19319_mob103_assignment1.model.SinhVien;
import com.example.chunglvps19319_mob103_assignment1.view.SinhVienActivity;

import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SinhVienAdapter extends BaseAdapter {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Context context;
    ArrayList<SinhVien> arrSinhVien;
    SinhVienDAO svDAO;

    ArrayList<LopHoc> arrLopHoc;
    LopHocDAO lhDAO;

    public SinhVienAdapter(Context context, ArrayList<SinhVien> arrSinhVien) {
        this.context = context;
        this.arrSinhVien = arrSinhVien;
        svDAO = new SinhVienDAO(context);
    }

    @Override
    public int getCount() {
        return arrSinhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return arrSinhVien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder1 holder;
        if (view == null) {
            holder = new ViewHolder1();
            //Add layout one_cell v??o view d??ng LayoutInflater, v?? ??p ki???u context v??? Activity.
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.sinhvien_one_cell, null);

            //??nh x??? t???i class ViewHolder
            holder.tvTenSV = view.findViewById(R.id.tvTenSV);
            holder.tvMaSV = view.findViewById(R.id.tvMaSV);
            holder.tvMaLopSV = view.findViewById(R.id.tvMaLopSinhVien);
            holder.ivAvatarSinhVien = view.findViewById(R.id.ivAvatar_SV);
            holder.ivEditSinhVien = view.findViewById(R.id.ivEditSinhVien);
            holder.ivDeleteSinhVien = view.findViewById(R.id.ivDeleteSinhVien);
            view.setTag(holder);
        } else {
            holder = (ViewHolder1) view.getTag();
        }
        //????? d??? li???u l??n ViewHolder
        SinhVien sv = arrSinhVien.get(i); //l???y t???ng ph???n t??? theo position (i)
        holder.tvTenSV.setText(sv.getTenSv());
        holder.tvMaSV.setText(sv.getMaSv());
        holder.tvMaLopSV.setText(sv.getMaLopHoc());
        holder.ivAvatarSinhVien.setImageResource(sv.getHinh());

        holder.ivEditSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog_EditSinhVien(sv);
            }
        });

        holder.ivDeleteSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog_XacNhanXoaSinhVien(sv);
            }
        });
        return view;
    }

    private void DiaLog_XacNhanXoaSinhVien(SinhVien sv) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("B???n mu???n xo?? sinh vi??n n??y?");

        alertDialog.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (svDAO.delete(sv.getMaSv())) {
                    Toast.makeText(context, "Xo?? th??nh c??ng", Toast.LENGTH_LONG).show();
                    arrSinhVien.clear();
                    arrSinhVien.addAll(svDAO.getAll());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Xo?? th???t b???i.....", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }

    private void DiaLog_EditSinhVien(SinhVien sv) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_editsinhvien);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //x??? l?? trong Dialog
        Button btnEditHuySinhVien = dialog.findViewById(R.id.btnEditHuySinhVien);
        Button btnEditSinhVien = dialog.findViewById(R.id.btnEditSinhVien);
        EditText edtMaSinhVienDialog = dialog.findViewById(R.id.edtMaSinhVienDialog);
        EditText edtTenSinhVienDialog = dialog.findViewById(R.id.edtTenSinhVienDialog);
        EditText edtNgaySinhDialog = dialog.findViewById(R.id.edtNgaySinhDialog);
        Spinner spMaLopEditSinhVienDialog = dialog.findViewById(R.id.spMaLopEditSinhVienDialog);
        ImageView ivImage_SV = dialog.findViewById(R.id.ivImage_SV);

        edtMaSinhVienDialog.setText(sv.getMaSv());
        edtMaSinhVienDialog.setEnabled(false);
        edtTenSinhVienDialog.setText(sv.getTenSv());
        edtNgaySinhDialog.setText(sdf.format(sv.getNgaySinh()));

        lhDAO = new LopHocDAO(context);
        arrLopHoc = lhDAO.getAll();
        ArrayAdapter adapter2 = new ArrayAdapter(context, android.R.layout.simple_list_item_1,arrLopHoc);
        spMaLopEditSinhVienDialog.setAdapter(adapter2);
        //Spinner load
        int id = 0;
        for(int i=0;i<arrLopHoc.size();i++){
            if(sv.getMaLopHoc().equalsIgnoreCase(arrLopHoc.get(i).getMaLop())){
                id = i;
            }
        }
        spMaLopEditSinhVienDialog.setSelection(id);
        ivImage_SV.setImageResource(sv.getHinh());

        btnEditHuySinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnEditSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsEmpty(edtTenSinhVienDialog) | !checkIsEmpty(edtNgaySinhDialog)) {
                    Toast.makeText(context, "Vui l??ng nh???p ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    sv.setTenSv(edtTenSinhVienDialog.getText().toString());
                    sv.setNgaySinh(sdf.parse(edtNgaySinhDialog.getText().toString()));
                    sv.setMaLopHoc(spMaLopEditSinhVienDialog.getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (svDAO.update(sv)) {
                    Toast.makeText(context, "C???p nh???t th??nh c??ng", Toast.LENGTH_LONG).show();
                    arrSinhVien.clear();
                    arrSinhVien.addAll(svDAO.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "C???p nh???t th???t b???i.....", Toast.LENGTH_LONG).show();
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

    //h??m refresh filter custom listview
    public void update(ArrayList<SinhVien> arrSinhVienMenu) {
        arrSinhVien = new ArrayList<>();
        arrSinhVien.addAll(arrSinhVienMenu);
        notifyDataSetChanged();
    }
}

class ViewHolder1 {
    TextView tvTenSV, tvMaSV, tvMaLopSV;
    ImageView ivDeleteSinhVien, ivEditSinhVien, ivAvatarSinhVien;
}
