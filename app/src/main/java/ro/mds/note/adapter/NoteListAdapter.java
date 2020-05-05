package ro.mds.note.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Objects;

import ro.mds.note.R;
import ro.mds.note.entity.Note;

public class NoteListAdapter extends ArrayAdapter<Note> {
    private Context mContext;
    private int mResource;

    public NoteListAdapter(Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = Objects.requireNonNull(getItem(position)).getTitle();
        String text = Objects.requireNonNull(getItem(position)).getContent();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView titleView = convertView.findViewById(R.id.title);
        TextView textView = convertView.findViewById(R.id.content);

        if (title.length() > 20) {
            title = title.substring(0, 20) + "...";
        }
        if (title.length() > 70) {
            title = title.substring(0, 70) + "...";
        }
        titleView.setText(title);
        textView.setText(text);
        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
