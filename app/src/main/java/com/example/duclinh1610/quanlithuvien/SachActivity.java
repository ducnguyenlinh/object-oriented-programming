package com.example.duclinh1610.quanlithuvien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.duclinh1610.JSONParser;
import com.example.duclinh1610.adapters.Adapter_Sach;
import com.example.duclinh1610.model.Info_Book;
import com.example.duclinh1610.ultis.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SachActivity extends AppCompatActivity {
    // url
    private static String url_xoa_sach          = Config.HOST + "/QuanLyThuVien/xoa_sach.php";
    private static String url_list_sach         = Config.HOST + "/QuanLyThuVien/list_sach.php";
    private static String url_thong_tin_sach    = Config.HOST + "/QuanLyThuVien/thong_tin_sach.php";

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();

    // JSON Node names
    private static final String TAG_BOOKS   = "books";
    private static final String TAG_maSach  = "maSach";
    private static final String TAG_tenSach = "tenSach";
    private static final String TAG_tenTG   = "tenTG";
    private static final String TAG_namXB   = "namXB";

    // Array
    JSONArray books = null;
    ArrayList<Info_Book> arrBook;


    Adapter_Sach adapter_sach;
    ListView lvSach;
    String maSach;
    Intent intent_thong_tin_sach,intent_update;
/////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SachActivity.this,NhapSach.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvSach = (ListView) findViewById(R.id.lvSach);
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                maSach = ((TextView) view.findViewById(R.id.txtmaSach)).getText().toString();
                return false;
            }
        });
        arrBook = new ArrayList<Info_Book>();

        new LoadAllBook().execute();
        registerForContextMenu(lvSach);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_sach, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sua:
                new UpdateBook().execute();
                break;
            case R.id.item_xoa:
                new DeleteBook().execute();
                finish();
                Intent i = new Intent(this,SachActivity.class);
                startActivity(i);
                break;
            case R.id.item_xem:
                new GetBookDetails().execute();
                break;
        }
        return super.onContextItemSelected(item);
    }
////////////////////////////////////////////////////////////////////////////////////////////////
    class LoadAllBook extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SachActivity.this);
            pDialog.setMessage("Loading Book. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_list_sach, "GET", params);
            // Check your log cat for JSON reponse

            try {
                books = json.getJSONArray(TAG_BOOKS);
                // looping through All Products
                for (int i = 0; i < books.length(); i++) {
                    JSONObject c = books.getJSONObject(i);
                    // String each json item in variable
                    String str_maSach = c.getString(TAG_maSach);
                    String str_tenSach = c.getString(TAG_tenSach);
                    String str_tenTG = c.getString(TAG_tenTG);
                    String str_namXB = c.getString(TAG_namXB);
                    arrBook.add(new Info_Book(str_maSach,str_tenSach,str_tenTG,str_namXB));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            adapter_sach = new Adapter_Sach(SachActivity.this,arrBook);
            lvSach.setAdapter(adapter_sach);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    class DeleteBook extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SachActivity.this);
            pDialog.setMessage("Deleting Book...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSach, maSach));
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(url_xoa_sach, "POST", params);
        return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////
    class GetBookDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SachActivity.this);
            pDialog.setMessage("Loading book details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("maSach", maSach));

            final JSONObject json = jsonParser.makeHttpRequest(url_thong_tin_sach, "GET", params);

            Log.d("Single book details", json.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray products = json.getJSONArray(TAG_BOOKS);
                        JSONObject product = products.getJSONObject(0);

                        intent_thong_tin_sach = new Intent(SachActivity.this,ThongTinSachActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString(TAG_maSach, product.getString(TAG_maSach));
                        bundle.putString(TAG_tenSach,product.getString(TAG_tenSach));
                        bundle.putString(TAG_tenTG, product.getString(TAG_tenTG));
                        bundle.putString(TAG_namXB,product.getString(TAG_namXB));

                        intent_thong_tin_sach.putExtra("DuLieu", bundle);
                        startActivity(intent_thong_tin_sach);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////
class UpdateBook extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(SachActivity.this);
        pDialog.setMessage("Loading update book. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    protected String doInBackground(String... strings) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("maSach", maSach));

        final JSONObject json = jsonParser.makeHttpRequest(url_thong_tin_sach, "GET", params);

        Log.d("Update book", json.toString());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray products = json.getJSONArray(TAG_BOOKS);
                    JSONObject product = products.getJSONObject(0);

                    intent_update = new Intent(SachActivity.this,UpdateSach.class);

                    Bundle bundle = new Bundle();
                    bundle.putString(TAG_maSach, product.getString(TAG_maSach));
                    bundle.putString(TAG_tenSach,product.getString(TAG_tenSach));
                    bundle.putString(TAG_tenTG, product.getString(TAG_tenTG));
                    bundle.putString(TAG_namXB,product.getString(TAG_namXB));

                    intent_update.putExtra("DuLieu", bundle);
                    startActivity(intent_update);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }

    protected void onPostExecute(String file_url) {
        // dismiss the dialog once got all details
        pDialog.dismiss();
    }
}
}
