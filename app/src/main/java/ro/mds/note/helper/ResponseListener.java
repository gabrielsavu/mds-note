package ro.mds.note.helper;

/**
 * The interface through which a response listener is defined.
 * @param <T>
 */
public interface ResponseListener<T> {
     void onResponse(T responseItems);
}