package com.example.duclinh1610.muon_tra;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MuonSach extends AppCompatActivity {

    EditText edtmaSach, edtmaSV, edtngayMuon, edtngayTra;
    Button btnOk;

    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;

    private static String url_muon_sach = Config.HOST + "/QuanLyThuVien/muon_sach.php";

    private int kiemtra = 1;
    private static final String TAG_maSach     = "maSach";
    private static final String TAG_maSV       = "maSV";
    private static final String TAG_ngayMuon   = "ngayMuon";
    private static final String TAG_ngayTra    = "ngayTra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muon_sach);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemtra = 1;
                Error_Empty();
                if (kiemtra == 1) {
                    new Muon().execute();
                    finish();
                }
                else {
                    Toast.makeText(MuonSach.this, "Ban phai dien day du thong tin",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void init(){
        edtmaSach = (EditText) findViewById(R.id.maSach);
        edtmaSV = (EditText) findViewById(R.id.maSV);
        edtngayMuon = (EditText) findViewById(R.id.ngayMuon);
        edtngayTra = (EditText) findViewById(R.id.ngayTra);
        btnOk = (Button) findViewById(R.id.btnOk);
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
        if (TextUtils.isEmpty(edtngayMuon.getText().toString())){
            edtngayMuon.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
        if (TextUtils.isEmpty(edtngayTra.getText().toString())){
            edtngayTra.setError(getString(R.string.error_empty));
            kiemtra = 0;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
class Muon extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(MuonSach.this);
        pDialog.setMessage("Creating Book..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    String strMaSach = edtmaSach.getText().toString();
    String strMaSV = edtmaSV.getText().toString();
    String strNgayMuon = edtngayMuon.getText().toString();
    String strNgayTra = edtngayTra.getText().toString();

    protected String doInBackground(String... args) {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(TAG_maSach, strMaSach));
        params.add(new BasicNameValuePair(TAG_maSV, strMaSV));
        params.add(new BasicNameValuePair(TAG_ngayMuon, strNgayMuon));
        params.add(new BasicNameValuePair(TAG_ngayTra, strNgayTra));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_muon_sach,
                "POST", params);
        return null;
    }

    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
        pDialog.dismiss();
    }
}
}
