package ro.mds.note.actions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ro.mds.note.activity.LoginActivity;
import ro.mds.note.activity.MainActivity;
import ro.mds.note.activity.RegisterActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Auth {
    public void validateLogin(String email, String password, Context LoginActivity, Class<MainActivity> MainActivity, SharedPreferences.Editor editor){

        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity);
        String loginUrl="https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyD2g8440brWMDA5ssB7m4aJfnLLvKpXGfo";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                editor.putBoolean("saveLogin",true);
                editor.putString("email",email);
                editor.commit();
                Intent intent=new Intent(LoginActivity, MainActivity);
                LoginActivity.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity,"Wrong email or password",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                params.put("returnSecureToken","true");
                return params;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
   public void validateForgot(String email,Context LoginActivity){

        RequestQueue requestQueue=Volley.newRequestQueue(LoginActivity);
        String loginUrl="https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=AIzaSyD2g8440brWMDA5ssB7m4aJfnLLvKpXGfo";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), "Check your email to reset your password", Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "The email couldn't be found", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("email",email);
                params.put("requestType","PASSWORD_RESET");
                return params;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void validateRegister(String email,String password,Context RegisterActivity){

        RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity);
        String signupUrl="https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyD2g8440brWMDA5ssB7m4aJfnLLvKpXGfo";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, signupUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity,"The account has created successfully",Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = null;
                if(error.networkResponse.data!=null) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }


                try {
                    JSONObject jObject = new JSONObject(body);
                    jObject=jObject.getJSONObject("error");
                    Toast.makeText(RegisterActivity,jObject.getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }){
            @Override
            public Map<String,String> getParams(){
                Map<String,String> params= new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                params.put("returnSecureToken","true");
                return params;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
