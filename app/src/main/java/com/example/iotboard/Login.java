package com.example.iotboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edtusername, edtpassword;
    Button b1;
    SharedPreferences.Editor editor;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private static final String HI ="http://iotboard.atwebpages.com/get_user.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.circle_cropped);
        edtusername = findViewById(R.id.username);
        edtpassword = findViewById(R.id.password);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String valusername=edtusername.getText().toString();
        String valpassword=edtpassword.getText().toString();
        editor = sharedpreferences.edit();
        String user = sharedpreferences.getString("username", null);
        if(user!=null){
            startActivity(new Intent(Login.this, MainActivity.class));
        }


    }
    void dooit(final String uname, final String pass)
    {

        String url = "http://iotboard.atwebpages.com/get_user.php?uname="+uname+"&pass="+pass;
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("th" +
                        "is is is is : "+response);
                if(response.equals("1")) {
                    editor.putString("username", uname);
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(Login.this, "User name or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("this er er er : "+error);

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public void do_login(View view) {
        dooit(edtusername.getText().toString(),edtpassword.getText().toString());
    }
}

