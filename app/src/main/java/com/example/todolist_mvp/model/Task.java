package com.example.todolist_mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_tasks")
public class Task implements Parcelable {

    public enum ImportanceLevel{
        LOW,
        NORMAL,
        HIGH
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private boolean isDone;
    private ImportanceLevel importance;

    public Task(){

    }

    public Task(String title, ImportanceLevel importance) {
        this.title = title;
        this.importance = importance;
        this.isDone=false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public ImportanceLevel getImportance() {
        return importance;
    }

    public void setImportance(ImportanceLevel importance) {
        this.importance = importance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
        dest.writeInt(this.importance == null ? -1 : this.importance.ordinal());
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.isDone = source.readByte() != 0;
        int tmpImportance = source.readInt();
        this.importance = tmpImportance == -1 ? null : ImportanceLevel.values()[tmpImportance];
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.isDone = in.readByte() != 0;
        int tmpImportance = in.readInt();
        this.importance = tmpImportance == -1 ? null : ImportanceLevel.values()[tmpImportance];
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
