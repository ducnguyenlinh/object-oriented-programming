package com.example.duclinh1610.muon_tra;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TraSach extends AppCompatActivity {

    EditText edtmaSach, edtmaSV;
    Button btnOk;

    private int kiemtra = 1;


    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;

    private static String url_tra_sach = Config.HOST + "/QuanLyThuVien/tra_sach.php";

    private static final String TAG_maSach     = "maSach";
    private static final String TAG_maSV       = "maSV";
    private static final String TAG_ngayTra    = "ngayTra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_sach);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtmaSach = (EditText) findViewById(R.id.maSach);
        edtmaSV = (EditText) findViewById(R.id.maSV);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemtra = 1;
                Error_Empty();
                if (kiemtra == 1) {
                    new Tra().execute();
                    finish();
                } else {
                    Toast.makeText(TraSach.this, "Ban phai dien day du thong tin",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Error_Empty(){
        if (TextUtils.isEmpty(edtmaSach.getText().toString())){
            edtmaSach.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(edtmaSV.getText().toString())){
            edtmaSV.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    class Tra extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TraSach.this);
            pDialog.setMessage("Creating Book..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String str_maSV = edtmaSV.getText().toString();
        String str_maSach = edtmaSach.getText().toString();
        String str_ngayTra = sdf.format(c.getTime());


        protected String doInBackground(String... args) {

            Log.e("abc", str_ngayTra);
            // Building Parameters
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSach, str_maSach));
            params.add(new BasicNameValuePair(TAG_maSV, str_maSV));
            params.add(new BasicNameValuePair(TAG_ngayTra, str_ngayTra));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_tra_sach,
                    "POST", params);
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            //pDialog.dismiss();
        }
    }

}
