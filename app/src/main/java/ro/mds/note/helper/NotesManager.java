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

import ro.mds.note.activity.LoginActivity;
import ro.mds.note.entity.Note;

public class NotesManager extends SQLiteOpenHelper {
    public NotesManager(Context context){
        super(context,"documents2.db",null,1 );
    }



    public Boolean saveNote(Note note){
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

        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",note.getTitle());

        contentValues.put("content",note.getContent());
        long result=db.update("documents",contentValues,"title=?",new String[]{note.getTitle()});

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    @Nullable
    public Boolean deleteNote(@NotNull String title){

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

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from documents where title=?",new String[]{title});
        cursor.moveToNext();
        Note note=new Note(cursor.getString(0),cursor.getString(1));
        return note;
    }
    public List<Note> readNotes(){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from documents",new String[]{});
        List<Note> notes=new ArrayList<>();
        while(cursor.moveToNext())
        {
            notes.add(new Note(cursor.getString(0),cursor.getString(1)));
        }

        return notes;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("create table documents(title TEXT primary key,content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists documents");
    }
}
