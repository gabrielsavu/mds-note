package ro.mds.note.helper;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.jupiter.api.*;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ro.mds.note.entity.Note;

import static org.junit.jupiter.api.Assertions.assertAll;

import  static org.junit.jupiter.api.Assertions.assertEquals;
import  static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import  static org.junit.jupiter.api.Assertions.assertTrue;


public class NotesManagerTest2 {

    Context context;
    Note note;
    @BeforeEach
    void init(){

        context = InstrumentationRegistry.getInstrumentation().getContext();
        note=new Note("Test Save","Save this note");
        System.out.println(note.toString());
    }
    @Test
    @DisplayName("Read a single note")
    public void readNote() {


        System.out.println(note.toString());
        assertAll(()-> Assertions.assertDoesNotThrow(()-> NotesManager.readNote(context,"Test Save")),
                ()-> Assertions.assertEquals(note, NotesManager.readNote(context,"Test Save"),note.toString()));
    }

   @Nested
    @DisplayName("Notes Manipulation")
    class NotesManipulation {
        @Tag("Files")
        @Test
        @DisplayName("Save a single note")
        public void saveNote() {


                    assertEquals(note,NotesManager.saveNote(context,note), "text: " + context.toString());


        }
        @Tag("Files")
        @Test
        @DisplayName("Read a single note")

        public  void readNote() {
            System.out.println("YEEEEEE");
            NotesManager.saveNote(context,note);
            assertAll(()->assertDoesNotThrow(()->NotesManager.readNote(context,note.getTitle())),
                    ()->assertEquals(note,NotesManager.readNote(context,note.getTitle())));
        }
        @Tag("Files")
        @Test
        @DisplayName("Read multiple notes")
        public void readNotes() {
            List<Note> notes=new ArrayList<>();
            notes.add(new Note("First part","First part of text"));
            notes.add(new Note("Second part","Second part of text"));
            assertAll(()->assertDoesNotThrow(()->NotesManager.readNotes(context,1,2)),
                    ()->assertEquals(note,NotesManager.readNotes(context,1,2)));
        }
        @Tag("Files")
        @Test
        @DisplayName("Delete a note")
        public void deleteNote() {
            assertTrue(NotesManager.deleteNote(context,"My Test"));
        }
    }
}