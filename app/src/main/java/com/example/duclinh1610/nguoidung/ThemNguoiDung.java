package com.example.duclinh1610.nguoidung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duclinh1610.JSONParser;
import com.example.duclinh1610.quanlithuvien.R;
import com.example.duclinh1610.ultis.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ThemNguoiDung extends AppCompatActivity {

    EditText edtmaSV, edttenND, edtngaySinh, edtngayDK;
    Button btnAdd;

    JSONParser jsonNhapSach = new JSONParser();
    private ProgressDialog pDialog;

    private static String url_them_nguoi_dung = Config.HOST + "/QuanLyThuVien/them_nguoi_dung.php";

    private int kiemtra = 1;
    private static final String TAG_maSV        = "maSV";
    private static final String TAG_tenND       = "tenND";
    private static final String TAG_ngaySinh    = "ngaySinh";
    private static final String TAG_ngayDK      = "ngayDK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_user);

        init();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemtra = 1;
                Error_Empty();
                if (kiemtra == 1) {
                    new ThemNguoiSuDung().execute();
                    finish();
                    Intent i = new Intent(ThemNguoiDung.this,NguoiDungActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(ThemNguoiDung.this, "Ban phai dien day du thong tin",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init(){
        edtmaSV     = (EditText) findViewById(R.id.maSV);
        edttenND    = (EditText) findViewById(R.id.tenND);
        edtngaySinh = (EditText) findViewById(R.id.ngaySinh);
        edtngayDK   = (EditText) findViewById(R.id.ngayDK);
        btnAdd = (Button) findViewById(R.id.btnAdd);
    }


    private void Error_Empty(){
        if (TextUtils.isEmpty(edtmaSV.getText().toString())){
            edtmaSV.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(edttenND.getText().toString())){
            edttenND.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(edtngaySinh.getText().toString())){
            edtngaySinh.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(edtngayDK.getText().toString())){
            edtngayDK.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
    }

    class ThemNguoiSuDung extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ThemNguoiDung.this);
            pDialog.setMessage("Creating Book..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        String str_maSV     = edtmaSV.getText().toString();
        String str_tenND    = edttenND.getText().toString();
        String str_ngaySinh = edtngaySinh.getText().toString();
        String str_ngayDK   = edtngayDK.getText().toString();

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSV, str_maSV));
            params.add(new BasicNameValuePair(TAG_tenND, str_tenND));
            params.add(new BasicNameValuePair(TAG_ngaySinh, str_ngaySinh));
            params.add(new BasicNameValuePair(TAG_ngayDK, str_ngayDK));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonNhapSach.makeHttpRequest(url_them_nguoi_dung,
                    "POST", params);
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }
}
