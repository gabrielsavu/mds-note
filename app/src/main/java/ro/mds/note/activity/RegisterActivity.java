package ro.mds.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import ro.mds.note.actions.Auth;
import ro.mds.note.R;
public class RegisterActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button loginButton;
    Button registerButton;
    String errorMessage;
    Auth authActions;
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    boolean isValid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authActions=new Auth();
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.regEmail);
        password=findViewById(R.id.regPassword);
        loginButton=findViewById(R.id.signinButton);
        registerButton=findViewById(R.id.signupButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName=email.getText().toString();
                String inputPassword=password.getText().toString();
                if(inputName.isEmpty()||inputPassword.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(inputName).matches()){
                    Toast.makeText(RegisterActivity.this,"Please enter all the details correctly",Toast.LENGTH_SHORT).show();
                }
                else{
                    authActions.validateRegister(inputName,inputPassword,RegisterActivity.this);
                }
            }
        });
    }


}
