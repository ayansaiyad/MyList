package com.example.mylist;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.Entity;
import androidx.room.Dao;
import android.content.Context;
import androidx.room.Room;
// The Room Database for all tasks in the app
@Database(entities = {Task.class}, version = 1)
@TypeConverters({Converters.class}) // You’ll need a Converters.java for Date ↔ Long
public abstract class AppDb extends RoomDatabase {
    //taskDao Required by RoomDB to get taskDao implementation
    public abstract TaskDao taskDao();
    private static volatile AppDb INSTANCE;

    public static AppDb get(Context c){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(c, AppDb.class, "tasks_db").build();
        }
        return INSTANCE;
    }
    public TaskDao dao() {
        return taskDao();
    }
}
