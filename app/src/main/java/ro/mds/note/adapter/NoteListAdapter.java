package ro.mds.note.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.mds.note.R;
import ro.mds.note.activity.NoteActivity;
import ro.mds.note.entity.Note;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        TextView textView;
        LinearLayout mainLayout;
        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println(itemView.findViewById(R.layout.adapter_note_list_layout)+"REEEEEEEEEEEEE");
            mainLayout=itemView.findViewById(R.id.mainLayout);
            System.out.println("mainlayout= "+mainLayout);
            titleView = itemView.findViewById(R.id.title);
            textView = itemView.findViewById(R.id.content);
            System.out.println(titleView+" "+mainLayout);
        }
    }
    private Context mContext;
    private int mResource;
    private List<Note> lists;
    public NoteListAdapter(Context context, int resource) {
        this.mContext = context;
        this.mResource = resource;

    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    public Note getNote(int position){
        return lists.get(position);
    }
    public void remove(int position){
        lists.remove(position);
    }

    @NonNull
    @Override
    public NoteListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("BRRRRRRRRRRRRR");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        System.out.println(mResource);
        View view=inflater.inflate(mResource, parent, false);
        return new MyViewHolder(view);
    }
    public void addAll(List<Note> var){
        lists=var;
    }
    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.MyViewHolder holder, int position) {
        String title = lists.get(position).getTitle();
        String text = lists.get(position).getContent();
        holder.titleView.setText(title);
        holder.textView.setText(text);
        System.out.println(holder.mainLayout+"KKKKKKKKKKKKKKKKK");
        String finalTitle = title;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, NoteActivity.class);
                intent.putExtra("noteTitle", finalTitle);
                mContext.startActivity(intent);
            }
        });

        if (title.length() > 20) {
            title = title.substring(0, 20) + "...";
        }


    }

    @Override
    public int getItemCount() {

        return lists.size();
    }
}
