package com.example.myapplication.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SachAdapter;
import com.example.myapplication.dao.LoaiSachDao;
import com.example.myapplication.dao.SachDAO;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class QLSachFregment extends Fragment {
    RecyclerView recyclerSach;
    SachDAO sachDAO;
    EditText edtSearch;
    ArrayList<Sach> list = new ArrayList<>();
    ArrayList<Sach> list1 = new ArrayList<>();
    SachAdapter adapter;
    Button btnSapXep;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fregment_qlsach,container,false);
        recyclerSach = view.findViewById(R.id.recylerSach);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnSapXep = view.findViewById(R.id.btnSapXep);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                for (Sach sc: list1) {
                    if (sc.getTensach().contains(charSequence.toString())){
                        list.add(sc);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnSapXep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(list, new Comparator<Sach>() {
                    @Override
                    public int compare(Sach sach, Sach t1) {
                        adapter.notifyDataSetChanged();
                        return sach.getTensach().compareTo(t1.getTensach());
                    }
                });
            }
        });

        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);
        sachDAO = new SachDAO(getContext());
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
         list = sachDAO.getDSDauSach();
         list1 = sachDAO.getDSDauSach();

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerSach.setLayoutManager(linearLayoutManager);
        adapter = new SachAdapter(getContext(),list,getDSLoaiSach(), sachDAO);
        recyclerSach.setAdapter(adapter);
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themsach,null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        EditText edtNamXB = view.findViewById(R.id.edtNamXB);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1});

        spnLoaiSach.setAdapter(simpleAdapter);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tensach = edtTenSach.getText().toString();
                int tien =Integer.parseInt( edtTien.getText().toString());
                HashMap<String,Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");
                int namxb = Integer.parseInt(edtNamXB.getText().toString());

                boolean check = sachDAO.themSachMoi(tensach,tien,maloai,namxb);
                if (check){
                    Toast.makeText(getContext(),"Thêm sách thành công",Toast.LENGTH_LONG).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(),"Thêm sách không thành công",Toast.LENGTH_LONG).show();
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

    private ArrayList<HashMap<String, Object>> getDSLoaiSach(){
        LoaiSachDao loaiSachDao = new LoaiSachDao(getContext());
        ArrayList<LoaiSach> list = loaiSachDao.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (LoaiSach loai: list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", loai.getId());
            hs.put("tenloai", loai.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }
}
