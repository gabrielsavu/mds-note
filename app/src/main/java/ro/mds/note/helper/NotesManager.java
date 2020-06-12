package ro.mds.note.helper;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro.mds.note.entity.Note;

/**
 * The NotesManager class deals with notes management: Saving and deleting.
 */
public abstract class NotesManager {

    /**
     * Method that can be used to save a note.
     * The note as well as the context must not be null, otherwise it will throw an exception.
     *
     * @param context the context.
     * @param note the note that should be saved.
     * @return it returns the saved note or null if there was an exception when saving it.
     */
    @Nullable
    static public Note saveNote(@NotNull Context context, @NotNull Note note) {
        try {
            FileOutputStream fos = context.openFileOutput(note.getTitle(), Context.MODE_PRIVATE);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(fos, note);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return note;
    }

    /**
     * Method that can be used to read a note.
     * The context must not be null, otherwise it will throw an exception.
     *
     * @param context the context.
     * @param title the note title.
     * @return it returns the note or null if there was an exception when reading it.
     */
    static public Note readNote(@NotNull Context context, String title) {
        Note note = null;
        try {
            FileInputStream fis = context.openFileInput(title);
            ObjectMapper objectMapper = new ObjectMapper();
            note = objectMapper.readValue(fis, Note.class);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return note;
    }

    /**
     * Method that can be used to read multiple notes.
     * The context must not be null, otherwise it will throw an exception.
     * The reading is done in the order in which they were saved and starts from the "from" parameter to the "to" parameter.
     *
     * @param context the context.
     * @param from where to start.
     * @param to where to end.
     * @return it returns a list of notes.
     */
    @NotNull
    static public List<Note> readNotes(@NotNull Context context, int from, int to) {
        List<Note> notes = new ArrayList<>();
        String[] allFilesNames = context.fileList();
        if (from < 0) {
            from = 0;
        }
        if (to >= allFilesNames.length) {
            to = allFilesNames.length;
        }
        for (int i = from; i < to; i++) {
            notes.add(readNote(context, allFilesNames[i]));
        }
        return notes;
    }

    /**
     * Method that can be used to delete a note.
     * The context must not be null, otherwise it will throw an exception.
     *
     * @param context the context.
     * @param title the title of the note
     * @return {@code true} if the file was successfully deleted; else
     *         {@code false}.
     */
    static public boolean deleteNote(@NotNull Context context, String title) {
        return context.deleteFile(title);
    }
}
