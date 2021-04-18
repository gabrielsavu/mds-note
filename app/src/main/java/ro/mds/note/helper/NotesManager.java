package ro.mds.note.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro.mds.note.entity.Note;

public class NotesManager extends SQLiteOpenHelper {
    public NotesManager(Context context){
        super(context,"documents2.db",null,1 );
    }

    public Boolean saveNote(Note note){
        System.out.println("SAVEEEEEEEEEEEEEEEEEEEEEE");
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",note.getTitle());
        contentValues.put("content",note.getContent());
        long result=db.insert("documents",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updateNote(Note note){
        System.out.println("UPDATEEEEEEEEEEEEEEEEEE");
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",note.getTitle());
        System.out.println(note.getTitle()+" EEEEEEEEEEEE");
        contentValues.put("content",note.getContent());
        long result=db.update("documents",contentValues,"title=?",new String[]{note.getTitle()});
        System.out.println("RESULT+++++++++ "+result);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    @Nullable
    public Boolean deleteNote(@NotNull String title){
        System.out.println("DELETEEEEEEEEEEEEEEEEEEE");
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from documents where title=?",new String[]{title});
        if(cursor.getCount()==0){
            return false;
        }
        long result=db.delete("documents","title=?",new String[]{title});
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Note readNote(String title){
        System.out.println("READDDDDDDDDDDDDDDDDDDDD");
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        Cursor cursor=db.rawQuery("Select * from documents where title=?",new String[]{title});
        cursor.moveToNext();
        Note note=new Note(cursor.getString(0),cursor.getString(1));
        return note;
    }
    public List<Note> readNotes(){
        System.out.println("READSSSSSSSSSSSSSSSSSSSSS");
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        Cursor cursor=db.rawQuery("Select * from documents",new String[]{});
        List<Note> notes=new ArrayList<>();
        while(cursor.moveToNext())
        {
            notes.add(new Note(cursor.getString(0),cursor.getString(1)));
        }
        System.out.println(notes.size()+" SIZEEEEEEEEEEEEEE");
        return notes;
    }
//    static public Note readNote(@NotNull Context context, String title) {
//
//        Note note = null;
//        try {
//            FileInputStream fis = context.openFileInput(title);
//            ObjectMapper objectMapper = new ObjectMapper();
//            note = objectMapper.readValue(fis, Note.class);
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return note;
//    }
//    @NotNull
//    static public List<Note> readNotes(@NotNull Context context, int from, int to) {
//        List<Note> notes = new ArrayList<>();
//        String[] allFilesNames = context.fileList();
//        if (from < 0) {
//            from = 0;
//        }
//        if (to >= allFilesNames.length) {
//            to = allFilesNames.length;
//        }
//        for (int i = from; i < to; i++) {
//            notes.add(readNote(context, allFilesNames[i]));
//        }
//        return notes;
//    }
//    @Nullable
//    public Note saveNote(@NotNull Context context, @NotNull Note note) {
//        SQLiteDatabase db=this.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
//        try {
//            FileOutputStream fos = context.openFileOutput(note.getTitle(), Context.MODE_PRIVATE);
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.writeValue(fos, note);
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return note;
//    }
//    static public boolean deleteNote(@NotNull Context context, String title) {
//        return context.deleteFile(title);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("create table documents(title TEXT primary key,content TEXT)");
     System.out.println("AJUNGGGGGGGGGGG");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists documents");
    }
}
