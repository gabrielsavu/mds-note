package ro.mds.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import ro.mds.note.R;

public class MainActivity extends AppCompatActivity {
    Button logout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean saveData;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("loginRef",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Button takeNoteBtn = findViewById(R.id.takeNoteBtn);
        Button historyBtn = findViewById(R.id.historyBtn);
        logout=findViewById(R.id.logout);
        System.out.println("reeeeeeeeeeeeeeeeeeeee");
        takeNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent note = new Intent(MainActivity.this, NoteActivity.class);
                MainActivity.this.startActivity(note);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(MainActivity.this, HistoryActivity.class);
                MainActivity.this.startActivity(history);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                editor.clear();
                editor.commit();
                Intent note = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(note);
            }
        });
    }

}
