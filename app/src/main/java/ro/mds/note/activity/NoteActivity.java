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
    private NotesManager notesManager;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        notesManager=new NotesManager(getApplicationContext());
        objTitle = getIntent().getStringExtra("noteTitle");
        // toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (objTitle != null) {
                getSupportActionBar().setTitle("Update note");
            }
            else
                getSupportActionBar().setTitle("New note");
        }


        if (objTitle != null) {
            EditText title = findViewById(R.id.noteTitle);
            EditText content = findViewById(R.id.noteDetails);
            title.setEnabled(false);
            note = notesManager.readNote(objTitle);
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
            if(objTitle!=null)
                notesManager.updateNote(note);

            else
                notesManager.saveNote(note);
            Intent intent = new Intent(NoteActivity.this, HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
