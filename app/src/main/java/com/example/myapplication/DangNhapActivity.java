package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.dao.ThuThuDAO;

public class DangNhapActivity extends AppCompatActivity {
    EditText edtuser,edtpassword;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        edtuser=findViewById(R.id.edtuser);
        edtpassword=findViewById(R.id.edtpassword);
        btnlogin=findViewById(R.id.btnlogin);
        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user  = edtuser.getText().toString();
                String pass = edtpassword.getText().toString();
                if (thuThuDAO.checkDangNhap(user,pass)){
                    startActivity(new Intent(DangNhapActivity.this,MainActivity.class));

                }else {
                    Toast.makeText(DangNhapActivity.this, "Username và password không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}