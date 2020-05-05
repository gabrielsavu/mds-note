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

public class NotesManager {

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

    static public boolean deleteNote(@NotNull Context context, String title) {
        return context.deleteFile(title);
    }
}
