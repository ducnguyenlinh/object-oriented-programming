package com.example.duclinh1610.quanlithuvien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.duclinh1610.JSONParser;
import com.example.duclinh1610.ultis.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateSach extends AppCompatActivity {

    EditText edtmaSach,edttenSach,edttenTG,edtnamXB;
    Button btnUpdate;
    ProgressDialog pDialog;
    String maSach;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    private static String url_update_sach       = Config.HOST + "/QuanLyThuVien/update_sach.php";

    private static final String TAG_maSach  = "maSach";
    private static final String TAG_tenSach = "tenSach";
    private static final String TAG_tenTG   = "tenTG";
    private static final String TAG_namXB   = "namXB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_sach);

        init();
        edtmaSach.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_maSach));
        edttenSach.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_tenSach));
        edttenTG.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_tenTG));
        edtnamXB.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_namXB));

        edtmaSach.setEnabled(false);

        maSach = getIntent().getBundleExtra("DuLieu").getString(TAG_maSach);
        Log.e("abc", maSach);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveUserDetails().execute();
                finish();
                Intent i = new Intent(UpdateSach.this,SachActivity.class);
                startActivity(i);
            }
        });
    }

    public void init() {
        edtmaSach = (EditText) findViewById(R.id.maSach);
        edttenSach = (EditText) findViewById(R.id.tenSach);
        edttenTG = (EditText) findViewById(R.id.tenTG);
        edtnamXB = (EditText) findViewById(R.id.namXB);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }
///////////////////////////////////////////////////////////////////////////////////////////////
    class SaveUserDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateSach.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        // getting updated data from EditTexts
        String str_tenSach  = edttenSach.getText().toString();
        String str_tenTG    = edttenTG.getText().toString();
        String str_namXB    = edtnamXB.getText().toString();

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSach, maSach));
            params.add(new BasicNameValuePair(TAG_tenSach, str_tenSach));
            params.add(new BasicNameValuePair(TAG_tenTG, str_tenTG));
            params.add(new BasicNameValuePair(TAG_namXB, str_namXB));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_sach,
                    "POST", params);
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

}
