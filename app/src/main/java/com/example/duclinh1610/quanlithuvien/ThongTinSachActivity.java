package com.example.duclinh1610.quanlithuvien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.duclinh1610.JSONParser;
import com.example.duclinh1610.adapters.Adapter_History;
import com.example.duclinh1610.model.Info_History;
import com.example.duclinh1610.ultis.Config;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ThongTinSachActivity extends AppCompatActivity {

    TextView txtmaSach,txttenSach,txttenTG,txtnamXB;
    ListView lvHistory;
    Button btnExit;

    private static String url_history   = Config.HOST + "/QuanLyThuVien/history.php";
    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // JSON Node names
    private static final String TAG_BOOKS   = "books";
    private static final String TAG_maSach  = "maSach";
    private static final String TAG_tenSach = "tenSach";
    private static final String TAG_tenTG   = "tenTG";
    private static final String TAG_namXB   = "namXB";
    private static final String TAG_maSV    = "maSV";
    private static final String TAG_tenND   = "tenND";
    private static final String TAG_ngayMuon   = "ngayMuon";

    String maSach;
    // Array
    JSONArray users = null;
    ArrayList<Info_History> arrHistory;

    Adapter_History adapter_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_sach);

        init();

        Intent intent_nhan_thong_tin_sach = getIntent();
        Bundle bundle_nhan_thong_tin_sach = intent_nhan_thong_tin_sach.getBundleExtra("DuLieu");

        maSach = bundle_nhan_thong_tin_sach.getString(TAG_maSach);

        txtmaSach.setText(bundle_nhan_thong_tin_sach.getString(TAG_maSach));
        txttenSach.setText(bundle_nhan_thong_tin_sach.getString(TAG_tenSach));
        txttenTG.setText(bundle_nhan_thong_tin_sach.getString(TAG_tenTG));
        txtnamXB.setText(bundle_nhan_thong_tin_sach.getString(TAG_namXB));

        arrHistory = new ArrayList<Info_History>();
        new LoadHistory().execute();

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(ThongTinSachActivity.this, SachActivity.class);
                startActivity(i);
            }
        });
        registerForContextMenu(lvHistory);

    }

    public void init() {
        txtmaSach = (TextView) findViewById(R.id.maSach);
        txttenSach = (TextView) findViewById(R.id.tenSach);
        txttenTG = (TextView) findViewById(R.id.tenTG);
        txtnamXB = (TextView) findViewById(R.id.namXB);
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        btnExit = (Button) findViewById(R.id.btnExit);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    class LoadHistory extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ThongTinSachActivity.this);
            pDialog.setMessage("Loading Book. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_history, "GET", params);
            // Check your log cat for JSON reponse
            Log.d("History: ", json.toString());
            Log.e("abc", maSach);

            try {
                users = json.getJSONArray(TAG_BOOKS);
                // looping through All Products
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);
                    // String each json item in variable
                    String str_maSach = c.getString(TAG_maSach);
                    if (str_maSach.equalsIgnoreCase(maSach)) {
                        String str_maSV = c.getString(TAG_maSV);
                        String str_tenND = c.getString(TAG_tenND);
                        String str_ngayMuon = c.getString(TAG_ngayMuon);
                        arrHistory.add(new Info_History(str_maSV, str_tenND, str_ngayMuon));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            adapter_history = new Adapter_History(ThongTinSachActivity.this,arrHistory);
            adapter_history.notifyDataSetChanged();
            lvHistory.setAdapter(adapter_history);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

}
