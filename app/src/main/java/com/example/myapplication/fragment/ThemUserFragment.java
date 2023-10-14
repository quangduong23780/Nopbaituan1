package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ThuThuDAO;
import com.example.myapplication.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThemUserFragment extends Fragment {
    ThuThuDAO thuThuDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_themuser, container, false);

        FloatingActionButton floatAddUser = view.findViewById(R.id.foatAddUser);
        thuThuDAO = new ThuThuDAO(getContext());
        floatAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }
    private void loadData(){

    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_user, null);
        builder.setView(view);

        EditText edtMaTT = view.findViewById(R.id.edtMaTT);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtPassWord = view.findViewById(R.id.edtPassWord);
        EditText edtLoaiTaiKhoan = view.findViewById(R.id.edtLoaiTaiKhoan);
        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String matt = edtMaTT.getText().toString();
                String hoten = edtHoTen.getText().toString();
                String password = edtPassWord.getText().toString();
                String loaitaikhoan = edtLoaiTaiKhoan.getText().toString();
                if (matt.equals("") && hoten.equals("") && password.equals("") && loaitaikhoan.equals("")){
                    Toast.makeText(getContext(),"Không được để trống bất kì ô nào",Toast.LENGTH_LONG).show();
                }
                if (loaitaikhoan.equals("admin")) {
                    Toast.makeText(getContext(), "Không thể tạo tài khoản Admin", Toast.LENGTH_SHORT).show();
                }
                boolean check = thuThuDAO.ThemThuThu(matt,hoten,password,loaitaikhoan);
                 if (check) {
                    Toast.makeText(getContext(), "Thêm thủ thư thành công", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Thêm thủ thư thất bại", Toast.LENGTH_LONG).show();
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
