package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.myapplication.fragment.DoanhThuFragment;
import com.example.myapplication.fragment.DoiMatKhauFragment;
import com.example.myapplication.fragment.QLLoaiSachFragment;
import com.example.myapplication.fragment.QLPhieuMuonFragment;
import com.example.myapplication.fragment.QLSachFregment;
import com.example.myapplication.fragment.QLThanhVienFragment;
import com.example.myapplication.fragment.ThemUserFragment;
import com.example.myapplication.fragment.TopFrigment;
import com.google.android.material.navigation.NavigationView;

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
                } else if (item.getItemId()== R.id.mThemUser) {
                    fragment = new ThemUserFragment();
                }
                else if (item.getItemId()== R.id.mThoat){
                    startActivity(new Intent(MainActivity.this,DangNhapActivity.class));
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
           drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}