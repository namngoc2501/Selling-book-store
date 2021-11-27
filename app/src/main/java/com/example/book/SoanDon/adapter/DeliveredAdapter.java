package com.example.book.SoanDon.adapter;import android.app.Activity;import android.content.Context;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ArrayAdapter;import android.widget.CheckBox;import android.widget.TextView;import android.widget.Toast;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import com.example.book.R;import com.example.book.SoanDon.Object.Product_Cart;import com.example.book.SoanDon.models.Delivered;import com.example.book.SoanDon.models.User;import com.example.book.SoanDon.models.Voucher;import com.example.book.ThuKho.TKQuanLiSanPham.Product;import com.example.book.XuLyHD.DonHangChoXuLy.Bill;import com.google.firebase.database.ChildEventListener;import com.google.firebase.database.DataSnapshot;import com.google.firebase.database.DatabaseError;import com.google.firebase.database.DatabaseReference;import com.google.firebase.database.FirebaseDatabase;import java.util.ArrayList;import java.util.UUID;public class DeliveredAdapter extends ArrayAdapter {    Context context;    int layoutItemID;    ArrayList<Bill> delivereds;    ClickNhanTien clickNhanTien;    public DeliveredAdapter(Context context, int resource, ArrayList<Bill> objects, ClickNhanTien clickNhanTien) {        super(context, resource, objects);        this.context = context;        this.layoutItemID = resource;        this.delivereds = objects;        this.clickNhanTien = clickNhanTien;    }    @Override    public int getCount() {        return delivereds.size();    }    @Override    public View getView(int position, View convertView, ViewGroup parent) {        ViewHolder viewHolder = null;        //ConverView: luu lại thong tin những phần từ mỗi lần quấn lên hoặc xuống(tái sử dụng,ko cần find view id lại)        if (convertView == null) {            convertView = LayoutInflater.from(context).inflate(layoutItemID, parent, false);            viewHolder = new ViewHolder();            //get view from itemlayout            viewHolder.tenSach = convertView.findViewById(R.id.txtDelivered);            viewHolder.gia = convertView.findViewById(R.id.txtgiaDelivered);            viewHolder.txttennguoinhan = convertView.findViewById(R.id.txttennguoinhan);            viewHolder.tenshipper = convertView.findViewById(R.id.tenshipper);            viewHolder.chkDaNhanTien = convertView.findViewById(R.id.chkDaNhanTienItem);            //luu lại khi quấn để tái sử dụng            convertView.setTag(viewHolder);        } else {            //Tái sử dụng            viewHolder = (ViewHolder) convertView.getTag();        }        Bill delivered = delivereds.get(position);        viewHolder.tenSach.setText(delivered.getId());        viewHolder.gia.setText(delivered.getTotalMoney() + "");        viewHolder.txttennguoinhan.setText(delivered.getName() + "");        viewHolder.tenshipper.setText(delivered.getShipper() + "");        viewHolder.chkDaNhanTien.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if (clickNhanTien.ClickNhanTien(delivered)) {                    CheckBox chk = (CheckBox) v;                    chk.setChecked(false);                }            }        });        return convertView;    }    public static class ViewHolder {        TextView tenSach;        TextView gia;        TextView txttennguoinhan;        TextView tenshipper;        CheckBox chkDaNhanTien;    }}