package com.example.iotboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText msg;
    String uname;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msg = findViewById(R.id.et1);
        preferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String user = preferences.getString("username", null);
        uname = user;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mylogout:
                logout(uname);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(String prefKey) {
        preferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("username");
        editor.apply();
        editor.commit();
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    public void send_Data(View view) {
        dooit(uname,msg.getText().toString());
        
    }

    void dooit(final String uname, final String msg)
    {

        String url = "http://iotboard.atwebpages.com/set_data.php";
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                System.out.println("this is is is : "+response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("this er er er : "+error);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("uname", uname); //Add the data you'd like to send to the server.
                MyData.put("msg", msg);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
