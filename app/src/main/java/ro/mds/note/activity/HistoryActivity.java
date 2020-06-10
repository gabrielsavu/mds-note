package ro.mds.note.activity;

import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import ro.mds.note.R;
import ro.mds.note.adapter.NoteListAdapter;
import ro.mds.note.entity.Note;
import ro.mds.note.helper.LazyLoader;
import ro.mds.note.helper.NotesManager;
import ro.mds.note.helper.ResponseListener;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "HistoryActivity";

    private NoteListAdapter adapter;

    private ProgressBar footerView;

    private ListView historyListView;

    static private int page;

    private ResponseListener<List<Note>> responseListener = new ResponseListener<List<Note>>() {
        @Override
        public void onResponse(List<Note> responseItems) {
            adapter.addAll(responseItems);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Note History");
        }
        footerView = new ProgressBar(this);
        historyListView = findViewById(R.id.historyListView);
        historyListView.addFooterView(footerView);
        adapter = new NoteListAdapter(this, R.layout.adapter_note_list_layout);
        historyListView.setAdapter(adapter);
        page = 0;
        historyListView.setOnScrollListener(new LazyLoader() {

            @Override
            public void loadMore(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                loadItems();
            }
        });
        historyListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Note note = (Note) adapterView.getItemAtPosition(i);
            if (note != null) {

                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("noteTitle", note.getTitle());
                startActivity(intent);
            }

        });

        loadItems();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadItems() {
        List<Note> notes = NotesManager.readNotes(HistoryActivity.this, page, page + 3);
        if (notes.isEmpty()) {
            historyListView.removeFooterView(footerView);
            return;
        }
        page = page + 3;
        System.out.println(notes);
        responseListener.onResponse(notes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteNote) {
            final EditText input = new EditText(HistoryActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            new AlertDialog.Builder(this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setView(input)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NotesManager.deleteNote(getApplicationContext(), input.getText().toString())) {

                                for (int i = 0; i <= adapter.getCount(); ++i) {
                                    if (adapter.getItem(i).getTitle().equals(input.getText().toString())) {
                                        adapter.remove(adapter.getItem(i));
                                        historyListView.setAdapter(adapter);
                                        break;
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "The file has been deleted", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getApplicationContext(), "The file couldn't be deleted", Toast.LENGTH_LONG).show();
                        }
                    })

                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
