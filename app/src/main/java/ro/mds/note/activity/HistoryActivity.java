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

    /**
     * An integer to keep track of the last page displayed.
     * A page is equivalent to an item.
     * So, if there are 3 notes displayed then there are 3 pages.
     * This might create confusion...
     */
    static private int page;

    /**
     * A response listener that is called when new notes need to be added to the adapter.
     */
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

        // Check if the android device supports androidx.appcompat.widget.Toolbar.
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
        // Load more elements in the list if the users scroll down.
        // It is efficient in terms of space occupied on the user interface, there is no point in bringing all the elements in one go.
        historyListView.setOnScrollListener(new LazyLoader() {
            @Override
            public void loadMore(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                loadItems();
            }
        });
        // If the user clicks on an element then we open the editing activity.
        historyListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Note note = (Note) adapterView.getItemAtPosition(i);
            if (note != null) {

                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("noteTitle", note.getTitle()); // We need this in the NoteActivity for recognizing the note.
                startActivity(intent);
            }

        });

        // When the activity first starts the on scroll listener is not called.
        loadItems();
    }

    /**
     * When the user preses on the back button from the toolbar it should invoke the back pressed action.
     *
     * @return always true
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * This method must be used only in scroll listener.
     * Load the notes into the list and invoke the response listener to update the adapter.
     */
    private void loadItems() {
        List<Note> notes = NotesManager.readNotes(HistoryActivity.this, page, page + 3);
        // When there are no more notes to load, the loading bar must disappear.
        if (notes.isEmpty()) {
            historyListView.removeFooterView(footerView);
            return;
        }
        page = page + 3;
        System.out.println(notes);
        responseListener.onResponse(notes);
    }

    /**
     * When deleting an item you will be asked the name of the note that you wish to delete.
     * It was thought wrong, the deletion had to be done in the activity of each note without asking you the name of the note.
     *
     * @param item the item pressed
     * @return super implementation.
     */
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
                            if (NotesManager.deleteNote(getApplicationContext(), input.getText().toString()) == true) {

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
