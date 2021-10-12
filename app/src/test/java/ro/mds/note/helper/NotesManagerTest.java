package ro.mds.note.helper;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.jupiter.api.*;

import ro.mds.note.activity.LoginActivity;
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

        context = InstrumentationRegistry.getInstrumentation().getContext();
        notesManager=new NotesManager(context);
        note=new Note("Test Save","Save this note");
    }


    @Nested
    @DisplayName("Notes Manipulation")
    class NotesManipulation {
        @Tag("Files")
        @Test
        @DisplayName("Save a single note")
        void saveNote() {
            assertAll(()->Assertions.assertDoesNotThrow(()->notesManager.saveNote(note)),
                    ()->assertEquals(note,notesManager.saveNote(note)));


        }
        @Tag("Files")
        @Test
        @DisplayName("Read a single note")
        void readNote() {
            assertAll(()->assertDoesNotThrow(()->notesManager.readNote("Test Save")),
                    ()->assertEquals(note,notesManager.readNote("Test save")));
        }
        @Tag("Files")
        @Test
        @DisplayName("Read multiple notes")
        void readNotes() {
            List<Note> notes=new ArrayList<>();
            notes.add(new Note("First part","First part of text"));
            notes.add(new Note("Second part","Second part of text"));
            assertAll(()->assertDoesNotThrow(()->notesManager.readNotes()),
                    ()->assertEquals(notes,notesManager.readNotes()));
        }
        @Tag("Files")
        @Test
        @DisplayName("Delete a note")
        void deleteNote() {
            assertTrue(notesManager.deleteNote("My Test"));
        }
    }
}