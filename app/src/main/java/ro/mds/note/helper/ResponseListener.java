package ro.mds.note.helper;

public interface ResponseListener<T> {
    public void onResponse(T responseItems);
}