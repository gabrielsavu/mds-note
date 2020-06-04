package ro.mds.note.helper;

public interface ResponseListener<T> {
     void onResponse(T responseItems);
}