package com.example.duclinh1610.nguoidung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.duclinh1610.JSONParser;
import com.example.duclinh1610.quanlithuvien.R;

public class ThongTinUserActivity extends AppCompatActivity {

    TextView txtmaSV, txttenND, txtngaySinh, txtngayDK;
    Button btnExit;


    // JSON Node names
    private static final String TAG_USERS       = "users";
    private static final String TAG_maSV        = "maSV";
    private static final String TAG_tenND       = "tenND";
    private static final String TAG_ngaySinh    = "ngaySinh";
    private static final String TAG_ngayDK      = "ngayDK";

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_user);

        init();

        Intent intent_output = getIntent();
        Bundle bd_output = intent_output.getBundleExtra("DuLieu");

        txtmaSV.setText(bd_output.getString(TAG_maSV));
        txttenND.setText(bd_output.getString(TAG_tenND));
        txtngaySinh.setText(bd_output.getString(TAG_ngaySinh));
        txtngayDK.setText(bd_output.getString(TAG_ngayDK));

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent i = new Intent(ThongTinUserActivity.this, NguoiDungActivity.class);
                startActivity(i);
            }
        });
    }

    public void init(){
        txtmaSV = (TextView) findViewById(R.id.maSV);
        txttenND = (TextView) findViewById(R.id.tenND);
        txtngaySinh = (TextView) findViewById(R.id.ngaySinh);
        txtngayDK = (TextView) findViewById(R.id.ngayDK);

        btnExit = (Button) findViewById(R.id.btnExit);
    }
}
