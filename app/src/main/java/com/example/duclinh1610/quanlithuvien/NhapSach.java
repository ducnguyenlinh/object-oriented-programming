package com.example.duclinh1610.quanlithuvien;

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
import com.example.duclinh1610.ultis.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NhapSach extends AppCompatActivity {

    EditText maSach,tenSach,tenTG,namXB;
    Button btnNhap;

    JSONParser jsonNhapSach = new JSONParser();
    private ProgressDialog pDialog;

    private static String url_them_sach = Config.HOST + "/QuanLyThuVien/them_sach.php";

    private int kiemtra = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_sach);

        unit();

        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemtra = 1;
                Error_Empty();
                if (kiemtra == 1) {
                    new ThemSach().execute();
                    finish();
                    Intent i = new Intent(NhapSach.this,SachActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(NhapSach.this, "Ban phai dien day du thong tin",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void unit(){
        maSach = (EditText) findViewById(R.id.maSach);
        tenSach = (EditText) findViewById(R.id.tenSach);
        tenTG = (EditText) findViewById(R.id.tenTG);
        namXB = (EditText) findViewById(R.id.namXB);

        btnNhap = (Button) findViewById(R.id.btnNhap);
    }

    private void Error_Empty(){
        if (TextUtils.isEmpty(maSach.getText().toString())){
            maSach.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(tenSach.getText().toString())){
            tenSach.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(tenTG.getText().toString())){
            tenTG.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(namXB.getText().toString())){
            namXB.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
    }

    class ThemSach extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NhapSach.this);
            pDialog.setMessage("Creating Book..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        String strMaSach = maSach.getText().toString();
        String strTenSach = tenSach.getText().toString();
        String strTenTG = tenTG.getText().toString();
        String strNamXB = namXB.getText().toString();

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("maSach", strMaSach));
            params.add(new BasicNameValuePair("tenSach", strTenSach));
            params.add(new BasicNameValuePair("tenTG", strTenTG));
            params.add(new BasicNameValuePair("namXB", strNamXB));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonNhapSach.makeHttpRequest(url_them_sach,
                    "POST", params);
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }
}
