package ro.mds.note.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

/**
 * The object that gets saved on the phone.
 * It extends Serializable because it must be serializable in order to be saved in the internal memory of the phone.
 */
public class Note implements Serializable {
    /**
     * The title of the note.
     * This field is the identifier for every note.
     */
    private String title;

    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return title.equals(note.title) &&
                content.equals(note.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
