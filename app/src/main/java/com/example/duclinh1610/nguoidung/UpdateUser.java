package com.example.duclinh1610.nguoidung;

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
import com.example.duclinh1610.quanlithuvien.R;
import com.example.duclinh1610.ultis.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateUser extends AppCompatActivity {

    EditText edtmaSV,edttenND,edtngaySinh,edtngayDK;
    Button btnUpdate;
    ProgressDialog pDialog;
    String maSV;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    private static String url_update_user       = Config.HOST + "/QuanLyThuVien/update_user.php";

    private static final String TAG_maSV        = "maSV";
    private static final String TAG_tenND       = "tenND";
    private static final String TAG_ngaySinh    = "ngaySinh";
    private static final String TAG_ngayDK      = "ngayDK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        init();
        edtmaSV.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_maSV));
        edttenND.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_tenND));
        edtngaySinh.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_ngaySinh));
        edtngayDK.setText(getIntent().getBundleExtra("DuLieu").getString(TAG_ngayDK));

        edtmaSV.setEnabled(false);

        maSV = getIntent().getBundleExtra("DuLieu").getString(TAG_maSV);
        Log.e("maSV:", maSV);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveUserDetails().execute();
                finish();
                Intent i = new Intent(UpdateUser.this, NguoiDungActivity.class);
                startActivity(i);
            }
        });
    }

    public void init() {
        edtmaSV = (EditText) findViewById(R.id.maSV);
        edttenND = (EditText) findViewById(R.id.tenND);
        edtngaySinh = (EditText) findViewById(R.id.ngaySinh);
        edtngayDK = (EditText) findViewById(R.id.ngayDK);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    class SaveUserDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateUser.this);
            pDialog.setMessage("Saving user ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        // getting updated data from EditTexts
        String str_tenND         = edttenND.getText().toString();
        String str_ngaySinh      = edtngaySinh.getText().toString();
        String str_ngayDK        = edtngayDK.getText().toString();

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSV, maSV));
            params.add(new BasicNameValuePair(TAG_tenND, str_tenND));
            params.add(new BasicNameValuePair(TAG_ngaySinh, str_ngaySinh));
            params.add(new BasicNameValuePair(TAG_ngayDK, str_ngayDK));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_user,
                    "POST", params);
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }
}
