package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.SachAdapter;
import com.example.myapplication.dao.ThuThuDAO;
import com.example.myapplication.fragment.DoanhThuFragment;
import com.example.myapplication.fragment.DoiMatKhauFragment;
import com.example.myapplication.fragment.QLLoaiSachFragment;
import com.example.myapplication.fragment.QLPhieuMuonFragment;
import com.example.myapplication.fragment.QLSachFregment;
import com.example.myapplication.fragment.QLThanhVienFragment;
import com.example.myapplication.fragment.ThemUserFragment;
import com.example.myapplication.fragment.TopFrigment;
import com.example.myapplication.model.ThanhVien;
import com.example.myapplication.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolBar;
    NavigationView navigationView;
    FrameLayout frameLayout;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar=findViewById(R.id.toolBar);
        navigationView=findViewById(R.id.navigaionView);
        frameLayout=findViewById(R.id.frameLayout);
        drawerLayout=findViewById(R.id.drawerLayout);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtname = headerLayout.findViewById(R.id.txtname);

        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId()== R.id.mQLPhieuMuon){
                    fragment = new QLPhieuMuonFragment();
                }else if (item.getItemId()== R.id.mQLLoaiSach) {
                    fragment = new QLLoaiSachFragment();
                }else if (item.getItemId()== R.id.mQLSach){
                    fragment = new QLSachFregment();
                } else if (item.getItemId()== R.id.mTop) {
                    fragment = new TopFrigment();
                } else if (item.getItemId()== R.id.mQLThanhVien) {
                    fragment = new QLThanhVienFragment();
                } else if (item.getItemId()== R.id.mDoanhThu) {
                    fragment = new DoanhThuFragment();
                } else if (item.getItemId()== R.id.mDoiMatKhau) {
                    fragment = new DoiMatKhauFragment();
                    showDialogDoiMatKhau();
                } else if (item.getItemId()== R.id.mThemUser) {
                    fragment = new ThemUserFragment();
                }
                else if (item.getItemId()== R.id.mThoat){
                    Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit();
                    toolBar.setTitle(item.getTitle());
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment)
                        .commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                toolBar.setTitle(item.getTitle());
                return false;
            }
        });

        //hien thi chức năng của admin
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaiTK = sharedPreferences.getString("loaitaikhoan","");
        if (loaiTK.equals("admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mThemUser).setVisible(true);
        }

        String hoten = sharedPreferences.getString("hoten","");
        txtname.setText("Xin chào: " + hoten);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
           drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    private void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);
        EditText edtoldpass = view.findViewById(R.id.edtoldpass);
        EditText edtnewpass = view.findViewById(R.id.edtnewpass);
        EditText edtrenewpass = view.findViewById(R.id.edtrenewpass);
        builder.setView(view);

        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String oldpass = edtoldpass.getText().toString();
                String newpass = edtnewpass.getText().toString();
                String renewpass = edtrenewpass.getText().toString();
                if (newpass.equals(renewpass)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("THONG TIN", MODE_PRIVATE);
                    String matt = sharedPreferences.getString("matt", "");
                    ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                    boolean check = thuThuDAO.CapNhatMatKhau(matt, oldpass, newpass);
                    if (check) {
                        Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    } else {
                        Toast.makeText(MainActivity.this, "Cập nhật mật khẩu không thành công", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Password không khớp với nhau", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}