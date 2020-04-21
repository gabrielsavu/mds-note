package ro.mds.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ro.mds.note.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takeNoteBtn = findViewById(R.id.takeNoteBtn);
        Button historyBtn = findViewById(R.id.historyBtn);

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

    }
}
