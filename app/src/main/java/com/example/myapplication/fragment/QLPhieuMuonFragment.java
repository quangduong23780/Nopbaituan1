package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PhieuMuonAdapter;
import com.example.myapplication.dao.PhieuMuonDAO;
import com.example.myapplication.dao.SachDAO;
import com.example.myapplication.dao.ThanhVienDao;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class QLPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    RecyclerView recyclerQLPhieuMuon;
    ArrayList<PhieuMuon> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon,container,false);
        recyclerQLPhieuMuon=view.findViewById(R.id.recylerQLPhieuMuon);
        FloatingActionButton floatAdd = view.findViewById(R.id.foatAdd);

        LoadData();
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }
    private void LoadData(){
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getDSPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerQLPhieuMuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(list, getContext());
        recyclerQLPhieuMuon.setAdapter(adapter);
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_phieumuon,null);
        Spinner spnThanhVien, spnSach;
        EditText edtTien;
        spnThanhVien=view.findViewById(R.id.spmThanhVien);
        spnSach=view.findViewById(R.id.spmSach);
        edtTien=view.findViewById(R.id.edtTien);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
               int matv =(int) hsTV.get("matv");

               HashMap<String, Object>hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
               int masach =(int) hsSach.get("masach");

               int tien = Integer.parseInt(edtTien.getText().toString());
               themPhieuMuon(matv,masach,tien);
            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    private void getDataThanhVien(Spinner spnThanhVien){
            ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
            ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();

            ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
            for (ThanhVien tv: list){
                HashMap<String, Object> hs = new HashMap<>();
                hs.put("matv", tv.getMatv());
                hs.put("hoten",tv.getHoten());
                listHM.add(hs);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    getContext(),
                    listHM,
                    android.R.layout.simple_list_item_1,
                    new String[]{"hoten"},
                    new int[]{android.R.id.text1});
            spnThanhVien.setAdapter(simpleAdapter);
    }
    private void getDataSach(Spinner spnSach){
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getDSDauSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sc: list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sc.getMasach());
            hs.put("tensach",sc.getTensach());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void themPhieuMuon(int matv, int masach, int tien){
        //lay ma thuthu
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt","");
        //lay ngay hien tai
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(matv, matt,masach,ngay,0,tien);
        boolean kiemtra = phieuMuonDAO.themPhieuMuon(phieuMuon);
        if (kiemtra){
            Toast.makeText(getContext(),"Thêm thiếu mượn thành công",Toast.LENGTH_LONG).show();
            LoadData();
        }else {
            Toast.makeText(getContext(),"Thêm phiếu mượn thất bại",Toast.LENGTH_LONG).show();
        }
    }
}
