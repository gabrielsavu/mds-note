package ro.mds.note.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ro.mds.note.MainActivity;
import ro.mds.note.R;
import ro.mds.note.database.Database;

public class NoteActivity extends AppCompatActivity {
    Database myDb;
    EditText editTitle;
    EditText editDetails;
    EditText editMarks;
    EditText editId;
    Button btnAdd,btnUpdate,btnView,btnDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_note);

        // toolbar
        findViewById(R.id.toolbar);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("New note");
        }
        myDb=new Database(this);
        editTitle=(EditText)findViewById(R.id.noteTitle);
        editDetails=(EditText)findViewById(R.id.noteDetails);
        /*
        editId=(EditText)findViewById(R.id.editTextId);

        btnUpdate=(Button)findViewById(R.id.updateData);
        btnView=(Button)findViewById(R.id.viewAll);
        btnDel=(Button)findViewById(R.id.deleteData);*/
        btnAdd=(Button)findViewById(R.id.save);
        //redirect();
        addData();
        //updateData();
        //viewAll();
        //delete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.saveNote) {
            // salvarea notei
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void viewAll(){
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getAllData();
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+'\n');
                    buffer.append("Name :"+res.getString(1)+'\n');
                    buffer.append("Surname :"+res.getString(2)+'\n');
                    buffer.append("Marks :"+res.getString(3)+'\n');
                }
                showMessage("Data",buffer.toString());
            }
        });

    }
    public void delete(){
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inserted=myDb.deleteData(editId.getText().toString());
                if (inserted==true) {
                    Toast.makeText(NoteActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(NoteActivity.this, "Doesn't exist", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    public void redirect(){
        AlertDialog.Builder alert=new AlertDialog.Builder(NoteActivity.this);
        alert.setTitle("Confirm the save..");

        alert.setMessage("Are you sure want to save?");

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean inserted=myDb.insertData(editTitle.getText().toString(),editDetails.getText().toString());
                if (inserted==true) {
                    Toast.makeText(NoteActivity.this, "File created", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(NoteActivity.this, "File couldn't create", Toast.LENGTH_LONG).show();
                }
                AlertDialog.Builder alert2=new AlertDialog.Builder(NoteActivity.this);
                alert2.setTitle("Create new file");
                alert2.setMessage("Do you want to make another file?");
                alert2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(NoteActivity.this, HistoryActivity.class);
                        startActivity(intent);
                    }
                });
                alert2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editDetails.setText(" ");
                        editTitle.setText(" ");
                    }
                });
                alert2.show();
            }
        });
        alert.setNegativeButton("NO", null);
        alert.show();
    }
    public void addData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               redirect();

            }
        });
    }
    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inserted=myDb.updateData(editTitle.getText().toString(),editDetails.getText().toString(),editId.getText().toString());
                if (inserted==true) {
                    Toast.makeText(NoteActivity.this, "Updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(NoteActivity.this, "Not updated", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
