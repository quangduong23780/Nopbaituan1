package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.SachDAO;
import com.example.myapplication.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String,Object>> listHM;
    private SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_sach,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.txtMaSach.setText("Mã sách: " +list.get(position).getMasach());
       holder.txtTenSach.setText("Tên sách: " +list.get(position).getTensach());
       holder.txtGiaThue.setText("Gía thuê: " +list.get(position).getGiathue());
       holder.txtMaLoai.setText("Mã loại: " +list.get(position).getMaloai());
       holder.txtTenLoai.setText("Tên loại: " +list.get(position).getTenloai());
       holder.txtNamXB.setText("Năm xuất bản: "+ list.get(position).getNamxb());

       holder.ivEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                showDialog(list.get(holder.getAdapterPosition()));
           }
       });

       holder.ivDel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
               switch (check){
                   case 1:
                       Toast.makeText(context,"Xoá thành công",Toast.LENGTH_LONG).show();
                       loadData();
                       break;
                   case 0:
                       Toast.makeText(context,"Xoá không thành công",Toast.LENGTH_LONG).show();
                       break;
                   case -1:
                       Toast.makeText(context,"Không xoá được sách này vì có trong phiếu mượn",Toast.LENGTH_LONG).show();
                       break;
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


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach,txtTenSach,txtGiaThue,txtMaLoai,txtTenLoai,txtNamXB;
        ImageView ivDel, ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtNamXB = itemView.findViewById(R.id.txtNamXB);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }

    private void showDialog(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suasach,null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        TextView txtMaSach = view.findViewById(R.id.txtMaSach);
        EditText edtNamXB = view.findViewById(R.id.edtNamXB);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        txtMaSach.setText("Mã sách: " + sach.getMasach());
        edtTenSach.setText(sach.getTensach());
        edtTien.setText(String.valueOf(sach.getGiathue()));
        edtNamXB.setText(String.valueOf(sach.getNamxb()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(simpleAdapter);
        int index = 0;
        int postion = -1;
        for (HashMap<String,Object>item : listHM){
            if ((int)item.get("maloai") ==sach.getMaloai()){
                postion = index;
            }
            index++;
        }
        spnLoaiSach.setSelection(postion);
        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tensach = edtTenSach.getText().toString();
                int tien =Integer.parseInt( edtTien.getText().toString());
                HashMap<String,Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");
                int namxb = Integer.parseInt(edtNamXB.getText().toString());
                boolean check = sachDAO.capNhatThongTinSach(sach.getMasach(),tensach,tien,maloai,namxb);
                if (check){
                    Toast.makeText(context,"Cập nhật sách thành công",Toast.LENGTH_LONG).show();
                    loadData();
                }else {
                    Toast.makeText(context,"Cập nhật sách không thành công",Toast.LENGTH_LONG).show();
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
        list = sachDAO.getDSDauSach();
        notifyDataSetChanged();
    }

}
