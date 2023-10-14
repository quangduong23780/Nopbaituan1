package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ThanhVienDao;
import com.example.myapplication.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context ;
    private ArrayList<ThanhVien> list;
    private ThanhVienDao thanhVienDao;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list,ThanhVienDao thanhVienDao) {
        this.context = context;
        this.list = list;
        this.thanhVienDao = thanhVienDao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanhvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTV.setText("Mã TV: " + list.get(position).getMatv());
        holder.txtHoTen.setText("Họ tên: " + list.get(position).getHoten());
        holder.txtNamSinh.setText("Năm sinh: " + list.get(position).getNamsinh());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdateTT(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = thanhVienDao.xoaThongTin(list.get(holder.getAdapterPosition()).getMatv());
                switch (check){
                    case 1:
                        Toast.makeText(context,"Xoá thành viên thành công",Toast.LENGTH_LONG).show();
                        loadData();
                    case 0:
                        Toast.makeText(context,"Xoá thành viên thất bại",Toast.LENGTH_LONG).show();
                    case -1:
                        Toast.makeText(context,"Thành viên tồn tại phiếu mượn, không thể xoá ",Toast.LENGTH_LONG).show();
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaTV,txtHoTen,txtNamSinh;
        ImageView ivEdit,ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            ivDel = itemView.findViewById(R.id.ivDel);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
    private void showDialogUpdateTT(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_thanhvien,null);
        builder.setView(view);

        TextView txtMaTV = view.findViewById(R.id.txtMaTV);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        txtMaTV.setText("Mã TV: " + thanhVien.getMatv());
        edtHoTen.setText(thanhVien.getHoten());
        edtNamSinh.setText(thanhVien.getNamsinh());

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               String hoten = edtHoTen.getText().toString();
               String namsinh = edtNamSinh.getText().toString();
               int id = thanhVien.getMatv();

               boolean check = thanhVienDao.UpdateThongTin(id,hoten,namsinh);
               if(check){
                   Toast.makeText(context,"Update thông tin thành công",Toast.LENGTH_LONG).show();
                   loadData();
               }else {
                   Toast.makeText(context,"Update thông tin không thành công",Toast.LENGTH_LONG).show();
               }
            }
        });
         builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         });
         AlertDialog alertDialog = builder.create();
         alertDialog.show();
    }
    private void loadData(){
        list.clear();
        list = thanhVienDao.getDSThanhVien();
        notifyDataSetChanged();
    }
}
