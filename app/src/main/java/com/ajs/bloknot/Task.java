package com.ajs.bloknot;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * A Task is a representation of something the User must finish. The application user can track a
 * Task's Date and Time, as well as provide some informative details.
 */
@Entity(tableName = "tasks")
public class Task implements Parcelable {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "first_name")
    public String name;

    @ColumnInfo(name = "details")
    String details;

    @ColumnInfo(name = "date")
    public Date date;

    public Task(String name, int id, String details, Date date) {
        this.name = name;
        this.id = id;
        this.details = details;
        this.date = date;
    }

    public Task(Parcel in) {
        name = in.readString();
        id = in.readInt();
        details = in.readString();
        date = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(details);
        dest.writeLong(date.getTime());
    }

    @Ignore
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Task)) {
            return false;
        } else {
            Task c = (Task) obj;
            return this.id == c.id;
        }


    }

    @NonNull
    @Override
    public String toString() {
        return "Task: " + id+ " - " + name + " - " + details + " - " + date;
    }

}
