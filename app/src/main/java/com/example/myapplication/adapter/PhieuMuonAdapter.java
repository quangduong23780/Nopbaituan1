package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.PhieuMuonDAO;
import com.example.myapplication.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHoder>{

    private ArrayList<PhieuMuon> list;
    private Context context;
    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycle_phieumuon,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.txtMaPM.setText("Mã PM: "+list.get(position).getMapm());
        holder.txtMaTV.setText("Mã TV: "+list.get(position).getMatv());
        holder.txtTenTV.setText("Tên TV: "+list.get(position).getTentv());
        holder.txtMaTT.setText("Mã TT: "+list.get(position).getMatt());
        holder.txtTenTT.setText("Tên TT: "+list.get(position).getTentt());
        holder.txtMaSach.setText("Mã Sách: "+list.get(position).getMasach());
        holder.txtTenSach.setText("Tên Sách: "+list.get(position).getTensach());
        holder.txtNgay.setText("Ngày: "+list.get(position).getNgay());
        String trangthai = "";
        if (list.get(position).getTrasach()==1){
            trangthai = "Đã trả sách";
        }else {
            trangthai = "Chưa trả sách";
        }
        holder.txtTrangThai.setText("Trạng thái: "+trangthai);
        holder.txtTien.setText("Tiền Thuê: "+list.get(position).getTienthue());

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean check = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
                if (check){
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context,"Thay đổi trạng thái thất bại",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txtMaPM,txtMaTV,txtTenTV,txtMaTT,txtTenTT,txtMaSach,txtTenSach,txtNgay,txtTrangThai,txtTien;
        Button btnTraSach;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtMaPM=itemView.findViewById(R.id.txtMaPM);
            txtMaTV=itemView.findViewById(R.id.txtMaTV);
            txtTenTV=itemView.findViewById(R.id.txtTenTV);
            txtMaTT=itemView.findViewById(R.id.txtMaTT);
            txtTenTT=itemView.findViewById(R.id.txtTenTT);
            txtMaSach=itemView.findViewById(R.id.txtMaSach);
            txtTenSach=itemView.findViewById(R.id.txtTenSach);
            txtNgay=itemView.findViewById(R.id.txtNgay);
            txtTrangThai=itemView.findViewById(R.id.txtTrangThai);
            txtTien=itemView.findViewById(R.id.txtTien);
            btnTraSach=itemView.findViewById(R.id.btnTraSach);
        }
    }
}
