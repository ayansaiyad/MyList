package com.example.mylist;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.util.Date;
// Model for the task entity
@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true) public long id;
    @NonNull public String title;
    public String description;
    @NonNull public Date dueDate;
    public boolean done;
    public Task(@NonNull String title, String description, @NonNull Date dueDate, boolean done) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.done = done;
    }

}
