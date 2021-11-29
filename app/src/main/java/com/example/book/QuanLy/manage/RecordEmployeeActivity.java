package com.example.book.QuanLy.manage;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;import androidx.annotation.NonNull;import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.widget.Toolbar;import com.example.book.Dialog.NotificationDialog;import com.example.book.MainActivity;import com.example.book.QuanLy.models.Employee;import com.example.book.R;import com.google.android.gms.tasks.OnCompleteListener;import com.google.android.gms.tasks.Task;import com.google.firebase.auth.AuthResult;import com.google.firebase.auth.FirebaseAuth;import com.google.firebase.auth.FirebaseAuthEmailException;import com.google.firebase.auth.FirebaseAuthException;import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;import com.google.firebase.database.DatabaseReference;import com.google.firebase.database.FirebaseDatabase;public class RecordEmployeeActivity extends AppCompatActivity {    String listspinner[] = {"Nhân viên giao hàng", "Nhân viên xử lý đơn", "Nhân viên đóng gói", "Nhân viên thủ kho"};    String permission;    boolean check = false;    Employee employee;    private NotificationDialog notificationDialog;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.manage_record_employee_layout);        setTitle("Hồ Sơ Nhân Viên");        Spinner spinner = findViewById(R.id.spinnerAddEmployee);        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listspinner);        spinner.setAdapter(spinnerAdapter);        permission = spinner.getSelectedItem().toString();        notificationDialog = new NotificationDialog(this);        Button btnAddEmploeeScreen = findViewById(R.id.btnAddEmploeeScreen);        TextView txtemail = findViewById(R.id.txtemail);        TextView txtname = findViewById(R.id.txtname);        TextView txtbirth = findViewById(R.id.txtbirth);        TextView txtsdt = findViewById(R.id.txtsdt);        TextView txtluongCB = findViewById(R.id.txtluongCB);        // toolbarr        Toolbar toolbar = findViewById(R.id.toobar);        setSupportActionBar(toolbar);        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);        toolbar.setNavigationOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                onBackPressed();            }        });        btnAddEmploeeScreen.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if (txtemail.getText().toString().isEmpty()) {                    txtemail.setError(getResources().getString(R.string.empty_field));                } else if (txtname.getText().toString().isEmpty()) {                    txtname.setError(getResources().getString(R.string.empty_field));                } else if (txtbirth.getText().toString().isEmpty()) {                    txtbirth.setError(getResources().getString(R.string.empty_field));                } else if (txtsdt.getText().toString().isEmpty()) {                    txtsdt.setError(getResources().getString(R.string.empty_field));                } else if (txtluongCB.getText().toString().isEmpty()) {                    txtluongCB.setError(getResources().getString(R.string.empty_field));                } else {                    String email = txtemail.getText().toString();                    String pass = "123456";                    String name = txtname.getText().toString();                    String birth = txtbirth.getText().toString();                    String sdt = txtsdt.getText().toString();                    int luong = Integer.parseInt(txtluongCB.getText().toString());                    // thêm vào auth:                    notificationDialog.startLoadingDialog();                    FirebaseAuth auth = FirebaseAuth.getInstance();                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {                        @Override                        public void onComplete(@NonNull Task<AuthResult> task) {                            if (task.isSuccessful()) {                                notificationDialog.endLoadingDialog();                                check = true;                                employee = new Employee(auth.getUid(), name, birth, sdt, email, luong, permission);                                DatabaseReference database = FirebaseDatabase.getInstance().getReference();                                if (employee.getPermission().equals("shipper")) {                                    database.child("shipper").child(employee.getId()).setValue(employee);                                } else {                                    database.child("admin").child(employee.getPermission()).child(employee.getId()).setValue(employee);                                }                                onBackPressed();                            } else {                                notificationDialog.startSuccessfulDialog(getResources().getString(R.string.signup_success));                            }                        }                    });                }            }        });        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {            @Override            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                permission = listspinner[position];                if (permission.equals(listspinner[0])) {                    permission = "shipper";                } else if (permission.equals(listspinner[1])) {                    permission = "xuLyHD";                } else if (permission.equals(listspinner[2])) {                    permission = "soanDon";                } else if (permission.equals(listspinner[3])) {                    permission = "thuKho";                }            }            @Override            public void onNothingSelected(AdapterView<?> parent) {            }        });    }}