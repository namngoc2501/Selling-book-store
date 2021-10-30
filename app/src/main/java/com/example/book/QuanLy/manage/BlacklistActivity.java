package com.example.book.QuanLy.manage;import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.ListView;import androidx.appcompat.app.AppCompatActivity;import com.example.book.R;public class BlacklistActivity extends AppCompatActivity {    private Context context;    private Button btnBack, btnDanhSachBlackList, btnAddBlackList;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.manage_blacklist_layout);        setTitle("Danh sách đen");        setControll();        setEvent();    }    private void setEvent() {        context = this;        btnBack.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent intent = new Intent(context,ManageActivity.class);                startActivity(intent);            }        });        btnAddBlackList.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent intent = new Intent(context,BlackList_add.class);                startActivity(intent);            }        });        btnDanhSachBlackList.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent intent = new Intent(context,Blacklist_List.class);                startActivity(intent);            }        });    }    private void setControll() {        btnBack = findViewById(R.id.backQuanLiBlackList);        btnDanhSachBlackList = findViewById(R.id.btnTKBLdanhSachBlackList);        btnAddBlackList = findViewById(R.id.btnTKBLthemTaiKhoanVaoBlackList);    }}