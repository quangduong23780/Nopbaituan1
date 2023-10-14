package com.example.myapplication.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ThanhVienAdapter;
import com.example.myapplication.dao.ThanhVienDao;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.PropertyResourceBundle;

public class QLThanhVienFragment extends Fragment {
    RecyclerView recyclerThanhVien;
    ThanhVienDao thanhVienDao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien,container,false);

        recyclerThanhVien = view.findViewById(R.id.recylerThanhVien);
        FloatingActionButton floatAdd = view.findViewById(R.id.foatAdd);

        thanhVienDao = new ThanhVienDao(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }
    private void loadData(){
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerThanhVien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(),list,thanhVienDao);
        recyclerThanhVien.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_thanhvien,null);
        builder.setView(view);

        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String hoten = edtHoTen.getText().toString();
                String namsinh = edtNamSinh.getText().toString();

                boolean check = thanhVienDao.themThanhVien(hoten,namsinh);
                if (check){
                    Toast.makeText(getContext(),"Thêm thành viên thành công",Toast.LENGTH_LONG).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(),"Thêm thành viên thất bại",Toast.LENGTH_LONG).show();
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
}
