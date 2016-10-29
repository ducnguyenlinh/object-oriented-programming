package com.example.duclinh1610.nguoidung;

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
import com.example.duclinh1610.adapters.Adapter_User;
import com.example.duclinh1610.model.Info_User;
import com.example.duclinh1610.quanlithuvien.R;
import com.example.duclinh1610.ultis.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungActivity extends AppCompatActivity {

    // url
    private static String url_xoa_user          = Config.HOST + "/QuanLyThuVien/xoa_user.php";
    private static String url_list_user         = Config.HOST + "/QuanLyThuVien/list_user.php";
    private static String url_thong_tin_user    = Config.HOST + "/QuanLyThuVien/thong_tin_user.php";

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();

    // JSON Node names
    private static final String TAG_USERS       = "users";
    private static final String TAG_maSV        = "maSV";
    private static final String TAG_tenND       = "tenND";
    private static final String TAG_ngaySinh    = "ngaySinh";
    private static final String TAG_ngayDK      = "ngayDK";

    // Array
    JSONArray users = null;
    ArrayList<Info_User> arrUser;


    Adapter_User adapter_user;
    String maSV;
    Intent intent_thongtinUser,intent_updateUser,i;
    /////////////////////////////////////////////////////////////////////////////////////////
    ListView lvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NguoiDungActivity.this, ThemNguoiDung.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvUser = (ListView) findViewById(R.id.lvUser);

        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                maSV = ((TextView) view.findViewById(R.id.txtmaSV)).getText().toString();
                return false;
            }
        });

        arrUser = new ArrayList<Info_User>();
        new LoadAllUser().execute();
        registerForContextMenu(lvUser);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_nguoidung,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sua:
                intent_updateUser = new Intent(NguoiDungActivity.this,UpdateUser.class);
                new UpdateUsers().execute();
                finish();
                i = new Intent(this,NguoiDungActivity.class);
                startActivity(i);
                break;
            case R.id.item_xoa:
                new DeleteUser().execute();
                finish();
                i = new Intent(this,NguoiDungActivity.class);
                startActivity(i);
                break;
            case R.id.item_xem:
                intent_thongtinUser = new Intent(NguoiDungActivity.this,ThongTinUserActivity.class);
                new GetUserDetails().execute();
                break;
        }
        return super.onContextItemSelected(item);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    class LoadAllUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NguoiDungActivity.this);
            pDialog.setMessage("Loading Book. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_list_user, "GET", params);
            // Check your log cat for JSON reponse
            Log.d("All Users: ", json.toString());

            try {
                users = json.getJSONArray(TAG_USERS);
                // looping through All Products
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);
                    // String each json item in variable
                    String str_maSV     = c.getString(TAG_maSV);
                    String str_tenND    = c.getString(TAG_tenND);
                    String str_ngaySinh = c.getString(TAG_ngaySinh);
                    String str_ngayDK   = c.getString(TAG_ngayDK);
                    arrUser.add(new Info_User(str_maSV,str_tenND,str_ngaySinh,str_ngayDK));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            adapter_user = new Adapter_User(NguoiDungActivity.this,arrUser);
            lvUser.setAdapter(adapter_user);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    class DeleteUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NguoiDungActivity.this);
            pDialog.setMessage("Deleting User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSV, maSV));
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(url_xoa_user, "POST", params);
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    class GetUserDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NguoiDungActivity.this);
            pDialog.setMessage("Loading book details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSV, maSV));

            final JSONObject json = jsonParser.makeHttpRequest(url_thong_tin_user, "GET", params);

            Log.d("Single user details", json.toString());

                    try {
                        JSONArray users = json.getJSONArray(TAG_USERS);
                        JSONObject user = users.getJSONObject(0);

                        Bundle bundle = new Bundle();
                        bundle.putString(TAG_maSV, user.getString(TAG_maSV));
                        bundle.putString(TAG_tenND,user.getString(TAG_tenND));
                        bundle.putString(TAG_ngaySinh, user.getString(TAG_ngaySinh));
                        bundle.putString(TAG_ngayDK,user.getString(TAG_ngayDK));

                        intent_thongtinUser.putExtra("DuLieu", bundle);
                        startActivity(intent_thongtinUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    class UpdateUsers extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NguoiDungActivity.this);
            pDialog.setMessage("Loading update user. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_maSV, maSV));

            final JSONObject json = jsonParser.makeHttpRequest(url_thong_tin_user, "GET", params);

            Log.d("Update user:", json.toString());

                    try {
                        JSONArray users = json.getJSONArray(TAG_USERS);
                        JSONObject user = users.getJSONObject(0);

                        Bundle bundle = new Bundle();
                        bundle.putString(TAG_maSV, user.getString(TAG_maSV));
                        bundle.putString(TAG_tenND,user.getString(TAG_tenND));
                        bundle.putString(TAG_ngaySinh, user.getString(TAG_ngaySinh));
                        bundle.putString(TAG_ngayDK,user.getString(TAG_ngayDK));

                        intent_updateUser.putExtra("DuLieu", bundle);
                        startActivity(intent_updateUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
}
