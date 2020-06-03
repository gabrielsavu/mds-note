package ro.mds.note.helper;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.jupiter.api.*;

import ro.mds.note.entity.Note;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotesManagerTest {
    NotesManager notesManager;
    Context context;
    Note note;
    @BeforeEach
    void init(){
        notesManager=new NotesManager();
        context = InstrumentationRegistry.getInstrumentation().getContext();
        note=new Note("Test Save","Save this note");
    }


    @Nested
    @DisplayName("Notes Manipulation")
    class NotesManipulation {
        @Tag("Files")
        @Test
        @DisplayName("Save a single note")
        void saveNote() {
            assertAll(()->Assertions.assertDoesNotThrow(()->notesManager.saveNote(context,note)),
                    ()->assertEquals(note,notesManager.saveNote(context,note)));


        }
        @Tag("Files")
        @Test
        @DisplayName("Read a single note")
        void readNote() {
            assertAll(()->assertDoesNotThrow(()->notesManager.readNote(context,"Test Save")),
                    ()->assertEquals(note,notesManager.readNote(context,"Test save")));
        }
        @Tag("Files")
        @Test
        @DisplayName("Read multiple notes")
        void readNotes() {
            List<Note> notes=new ArrayList<>();
            notes.add(new Note("First part","First part of text"));
            notes.add(new Note("Second part","Second part of text"));
            assertAll(()->assertDoesNotThrow(()->notesManager.readNotes(context,1,2)),
                    ()->assertEquals(note,notesManager.readNotes(context,1,2)));
        }
        @Tag("Files")
        @Test
        @DisplayName("Delete a note")
        void deleteNote() {
            assertTrue(notesManager.deleteNote(context,"My Test"));
        }
    }
}