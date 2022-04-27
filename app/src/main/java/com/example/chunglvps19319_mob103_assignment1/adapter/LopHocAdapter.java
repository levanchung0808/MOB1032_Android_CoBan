package com.example.chunglvps19319_mob103_assignment1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chunglvps19319_mob103_assignment1.R;
import com.example.chunglvps19319_mob103_assignment1.dao.LopHocDAO;
import com.example.chunglvps19319_mob103_assignment1.model.LopHoc;
import com.example.chunglvps19319_mob103_assignment1.model.SinhVien;
import com.example.chunglvps19319_mob103_assignment1.view.LopHocActivity;

import java.util.ArrayList;

public class LopHocAdapter extends BaseAdapter {
    Context context;
    ArrayList<LopHoc> arrLopHoc;
    LopHocDAO lhDAO;

    public LopHocAdapter(Context context, ArrayList<LopHoc> arrLopHoc) {
        this.context = context;
        this.arrLopHoc = arrLopHoc;
        lhDAO = new LopHocDAO(context);
    }

    @Override
    public int getCount() {
        return arrLopHoc.size();
    }

    @Override
    public Object getItem(int i) {
        return arrLopHoc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            //Add layout one_cell vào view dùng LayoutInflater, và ép kiểu context về Activity.
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.lophoc_one_cell, null);

            //ánh xạ tới class ViewHolder
            holder.tvMa = view.findViewById(R.id.tvMaLopHoc);
            holder.tvTen = view.findViewById(R.id.tvTenLopHoc);
            holder.ivEditLopHoc = view.findViewById(R.id.ivEditLopHoc);
            holder.ivDeleteLopHoc = view.findViewById(R.id.ivDeleteLopHoc);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //đổ dữ liệu lên ViewHolder(layout custom)
        LopHoc lh = arrLopHoc.get(i); //lấy từng phần tử theo position (i)
        holder.tvMa.setText(lh.getMaLop());
        holder.tvTen.setText(lh.getTenLop());

        holder.ivEditLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog_EditLopHoc(lh);
            }
        });

        holder.ivDeleteLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog_XacNhanXoaLop(lh);
            }
        });

        return view;
    }

    private void DiaLog_EditLopHoc(LopHoc lh) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_editlophoc);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //xử lý trong Dialog || ánh xạ
        Button btnCancel = dialog.findViewById(R.id.btnEditHuyLop);
        Button btnEditCapNhatLop = dialog.findViewById(R.id.btnEditCapNhatLop);
        EditText edtEditMaLop = dialog.findViewById(R.id.edtEditMaLop);
        EditText edtEditTenLop = dialog.findViewById(R.id.edtEditTenLop);
        ImageView ivImage_LH = dialog.findViewById(R.id.ivImage_LH);

        edtEditMaLop.setText(lh.getMaLop());
        edtEditMaLop.setEnabled(false);
        edtEditTenLop.setText(lh.getTenLop());
        ivImage_LH.setImageResource(lh.getHinh());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnEditCapNhatLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set Tên lớp theo cái đã sửa

                String _tenLop = edtEditTenLop.getText().toString();

                if (!checkIsEmpty(edtEditTenLop)) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                lh.setTenLop(_tenLop);
                if (lhDAO.update(lh)) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    arrLopHoc.clear();
                    arrLopHoc.addAll(lhDAO.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại.....", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void DiaLog_XacNhanXoaLop(LopHoc lh) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Bạn muốn xoá lớp này?");

        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (lhDAO.delete(lh.getMaLop())) {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_LONG).show();
                    arrLopHoc.clear();
                    arrLopHoc.addAll(lhDAO.getAll());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Xoá thất bại.....", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
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

    public void update(ArrayList<LopHoc> arrLopHocMenu){
        arrLopHoc = new ArrayList<>();
        arrLopHoc.addAll(arrLopHocMenu);
        notifyDataSetChanged();
    }

}

//nơi lưu trữ view
class ViewHolder {
    TextView tvMa, tvTen;
    ImageView ivEditLopHoc, ivDeleteLopHoc, ivImage_LH;
}
