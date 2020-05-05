package ro.mds.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ro.mds.note.R;
import ro.mds.note.entity.Note;
import ro.mds.note.helper.NotesManager;

public class NoteActivity extends AppCompatActivity {

    private String objTitle;

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("New note");
        }

        objTitle = getIntent().getStringExtra("noteTitle");
        if (objTitle != null) {
            EditText title = findViewById(R.id.noteTitle);
            EditText content = findViewById(R.id.noteDetails);
            note = NotesManager.readNote(getApplicationContext(), objTitle);
            title.setText(note.getTitle());
            content.setText(note.getContent());
        }
        else {
            note = new Note();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveNote) {
            EditText title = findViewById(R.id.noteTitle);
            EditText content = findViewById(R.id.noteDetails);
            note.setTitle(title.getText().toString());
            note.setContent(content.getText().toString());
            NotesManager.saveNote(getApplicationContext(), note);
            Intent intent = new Intent(NoteActivity.this, HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
