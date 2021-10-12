package ro.mds.note.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import ro.mds.note.actions.Auth;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ro.mds.note.R;

public class LoginActivity extends AppCompatActivity {
    Auth authActions;
    EditText email;
    EditText password;
    Button loginButton;
    Button registerButton;
    TextView forgotPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LoginButton fbLogin;
    Boolean saveLogin;
    boolean isValidLogin;
    boolean isValidForgot;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        authActions=new Auth();
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.logEmail);
        password=findViewById(R.id.logPassword);
        loginButton=findViewById(R.id.loginButton);
        registerButton=findViewById(R.id.registerButton);
        fbLogin=findViewById(R.id.fb_button);
        forgotPassword=findViewById(R.id.forgotPassword);
        fbLogin.setPermissions(Arrays.asList("email"));
        sharedPreferences=getSharedPreferences("loginRef",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        saveLogin=sharedPreferences.getBoolean("saveLogin",false);
        if(saveLogin){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
         callbackManager = CallbackManager.Factory.create();

         fbLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                     @Override
                     public void onSuccess(LoginResult loginResult) {
                         final AccessToken accessToken = loginResult.getAccessToken();

                         GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                             @Override
                             public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                                 System.out.println(user.optString("email"));
                                 editor.putBoolean("saveLogin",true);
                                 editor.putString("email",user.optString("email"));
                                 editor.commit();
                                 Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                 startActivity(intent);
                             }
                         }).executeAsync();

                     }

                     @Override
                     public void onCancel() {

                     }

                     @Override
                     public void onError(FacebookException error) {
                         Toast.makeText(LoginActivity.this,"Cannot connect with facebook , please create an account",Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName=email.getText().toString();
                String inputPassword=password.getText().toString();
                if(inputName.isEmpty()||inputPassword.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(inputName).matches()){
                    Toast.makeText(LoginActivity.this,"Please enter all the details correctly",Toast.LENGTH_SHORT).show();
                }
                else{

                    authActions.validateLogin(inputName,inputPassword,LoginActivity.this,MainActivity.class,editor);


                }
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(LoginActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Forgot password?")
                        .setMessage("Please enter your email:")
                        .setView(input)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                authActions.validateForgot(input.getText().toString(),LoginActivity.this);


                            }
                        })

                        .setIcon(android.R.drawable.ic_menu_search)
                        .show();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
