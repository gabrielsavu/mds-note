package ro.mds.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        List<Note> notes = NotesManager.readNotes(getApplicationContext(), page, page + 3);
        if (notes.isEmpty()) {
            historyListView.removeFooterView(footerView);
            return;
        }
        page = page + 3;
        System.out.println(notes);
        responseListener.onResponse(notes);
    }
}
