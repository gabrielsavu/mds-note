package ro.mds.note.helper;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ro.mds.note.entity.Note;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class NotesManagerIt {

    private Context context;

    private Note note;

    @Before
    public void init() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("ro.mds.note", context.getPackageName());
        note = new Note("Test Save", "Save this note");
    }

    @Test
    public void readNote() {
        assertEquals(note, NotesManager.readNote(context, note.getTitle()));
    }

    @Test
    public void saveNote() {
        assertEquals(note, NotesManager.saveNote(context, note));
    }

    @Test
    public void readNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("First part", "First part of text"));
        notes.add(new Note("Second part", "Second part of text"));
        for (Note localNote : notes) {
            NotesManager.saveNote(context, localNote);
        }
        for (Note localNote : notes) {
            assertEquals(localNote, NotesManager.readNote(context, localNote.getTitle()));
        }
    }

    @Test
    public void deleteNote() {
        assertTrue(NotesManager.deleteNote(context, note.getTitle()));
    }
}
