package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.LoaiSachAdapter;
import com.example.myapplication.dao.LoaiSachDao;
import com.example.myapplication.model.LoaiSach;

import java.util.ArrayList;

public class QLLoaiSachFragment extends Fragment{
    RecyclerView recyclerLoaiSach;
    LoaiSachDao dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach,container,false);

        recyclerLoaiSach = view.findViewById(R.id.recyclerLoaiSach);
        EditText edtLoaiSach = view.findViewById(R.id.edtLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);

        dao = new LoaiSachDao(getContext());
        LoadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloai = edtLoaiSach.getText().toString();
                if (dao.themLoaiSach(tenloai)){
                    //thong bao + load ds
                     LoadData();
                     edtLoaiSach.setText("");
                }else {
                    Toast.makeText(getContext(),"Thêm loại sách không thành công",Toast.LENGTH_LONG).show();

                }
            }
        });
        return view;
    }
    public void LoadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerLoaiSach.setLayoutManager(linearLayoutManager);
        ArrayList<LoaiSach> list = dao.getDSLoaiSach();
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(),list);
        recyclerLoaiSach.setAdapter(adapter);
    }
}
