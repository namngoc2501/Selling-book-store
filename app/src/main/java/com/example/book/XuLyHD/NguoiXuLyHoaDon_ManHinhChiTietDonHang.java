package com.example.book.XuLyHD;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.R;
import com.example.book.ThuKho.TKQuanLiSanPham.Product;
import com.example.book.XuLyHD.ChiTietSanPhamTrongDonHang.SanPham;
import com.example.book.XuLyHD.ChiTietSanPhamTrongDonHang.SanPhamAdapter;
import com.example.book.XuLyHD.DonHangChoXuLy.Bill;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class NguoiXuLyHoaDon_ManHinhChiTietDonHang extends AppCompatActivity {
    private ImageView imgUser;
    private ListView lvSanPhamTrongDonHang;
    private Context context;
    private ArrayList<SanPham> listSanPhamTrongDonHang;
    private TextView tvTongGiaTriDonHang, tvSoTienMaDonHangDuocGiam, tvMaDH, tvTenNguoiDat, tvSoDienThoai, tvDiaChiGiaoHang, tvTrangThaiDonHang;
    private Button btnXacNhanDH, btnHuyDonHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_xu_ly_hoa_don_____man_hinh_chi_tiet_don_hang);
        setControl();
        setEvent();


        // toolbarr
        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setEvent() {
        context = this;
        listSanPhamTrongDonHang = new ArrayList<>();
        DatabaseReference databill = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Bill bill = (Bill) intent.getSerializableExtra("TTDH");
        databill.child("users").child(bill.getId_user()).child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    Picasso.get().load(task.getResult().getValue(String.class)).into(imgUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(R.layout.item_adapter_sanphamtrongdonhangchitietdonhang, context, listSanPhamTrongDonHang);
        lvSanPhamTrongDonHang.setAdapter(sanPhamAdapter);

        databill.child("bill_detail").child(bill.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                databill.child("products").child(snapshot.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        String tenSP = task.getResult().getValue(Product.class).getTenSanPham();
                        int soLuong = snapshot.getValue(SanPham.class).getQuality();
                        double giaTien = snapshot.getValue(SanPham.class).getPrice();
                        String anhSP = task.getResult().getValue(Product.class).getHinhAnh();
                        SanPham sp = new SanPham(tenSP, (int) giaTien, soLuong, anhSP);
                        listSanPhamTrongDonHang.add(sp);
                        sanPhamAdapter.notifyDataSetChanged();

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // ?????nh d???ng s???
        NumberFormat currentLocale = NumberFormat.getInstance();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);


        double tienDuocGiam = bill.getDiscount();
        String discount = en.format(tienDuocGiam) + " VN??";

        double tongGiaTriDonHang = bill.getTotalMoney() - bill.getDiscount();
        String giaTien = en.format(tongGiaTriDonHang) + " VN??";
        tvTongGiaTriDonHang.setText("T???ng Gi?? Tr??? ????n H??ng L??: " + giaTien);
        tvSoTienMaDonHangDuocGiam.setText("S??? Ti???n M?? ????n H??ng ???????c Gi???m L??: " + discount);

        // In Th??ng Tin ????n H??ng
        tvMaDH.setText("M?? ????n H??ng: " + bill.getId());
        tvSoDienThoai.setText("S??? ??i???n Tho???i :" + bill.getPhone());
        tvTenNguoiDat.setText("T??n Ng?????i ?????t :" + bill.getName());
        tvDiaChiGiaoHang.setText("?????a Ch??? Giao H??ng" + bill.getAddress());
        tvTrangThaiDonHang.setText("Tr???ng Th??i ????n H??ng: ??ang Ch??? X??? L??");

        // Set S??? Ki???n Button X??c Nh???n ????n H??ng
        btnXacNhanDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("X??c Nh???n ????n H??ng").setMessage("B???n C?? Ch???c Ch???n Mu???n X??c Nh???n ????n H??ng").
                        setPositiveButton("C??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("status", 1);
                                databill.child("bills").child(bill.getId()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
//                                        Toast.makeText(context, "X??c Nh???n ????n H??ng Th??nh C??ng", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(NguoiXuLyHoaDon_ManHinhChiTietDonHang.this, NguoiXuLyHoaDon_ManHinhDanhSachDonHangChoXuLy.class);
//                                        startActivity(i);
                                        onBackPressed();
                                    }
                                });
                            }
                        }).setNegativeButton("Kh??ng", null).show();
            }
        });

        btnHuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("H???y ????n H??ng").setMessage("B???n C?? Ch???c Ch???n Mu???n H???y ????n H??ng").
                        setPositiveButton("C??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("status", -1);
                                databill.child("bills").child(bill.getId()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
//                                        Toast.makeText(context, "H???y ????n H??ng Th??nh C??ng", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(NguoiXuLyHoaDon_ManHinhChiTietDonHang.this, NguoiXuLyHoaDon_ManHinhDanhSachDonHangChoXuLy.class);
//                                        startActivity(i);
                                        onBackPressed();
                                    }
                                });
                            }
                        }).setNegativeButton("Kh??ng", null).show();
            }
        });

    }

    private void setControl() {
        lvSanPhamTrongDonHang = findViewById(R.id.CTDHlvDanhSachSP);
        imgUser = findViewById(R.id.profile_image_NguoiXuLyHoaDon);
        tvTongGiaTriDonHang = findViewById(R.id.CTDHtonggiatridonhang);
        tvSoTienMaDonHangDuocGiam = findViewById(R.id.CTDHsotienduocgiam);
        tvMaDH = findViewById(R.id.CTDHtvMaDH);
        tvTenNguoiDat = findViewById(R.id.CTDHtvTenND);
        tvSoDienThoai = findViewById(R.id.CTDHtvSDT);
        tvDiaChiGiaoHang = findViewById(R.id.CTDHtvDiaChi);
        tvTrangThaiDonHang = findViewById(R.id.CTDHtvTrangThaiDonHang);
        btnHuyDonHang = findViewById(R.id.btnCTDHXacNhanHuyDon);
        btnXacNhanDH = findViewById(R.id.btnCTDHXacNhanDaXuLy);
    }
}