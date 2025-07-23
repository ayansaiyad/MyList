package com.example.mylist;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface TaskDao {
    // Database access methods for tasks
    @Query("SELECT * FROM tasks ORDER BY dueDate DESC")
    LiveData<List<Task>> getAll();
    @Query("SELECT * FROM tasks WHERE done = 0 ORDER BY dueDate DESC")
    LiveData<List<Task>> getPendingTasks();
    @Query("SELECT * FROM tasks WHERE done = 1 ORDER BY dueDate DESC")
    LiveData<List<Task>> getCompletedTasks();
    @Insert void insert(Task t);
    @Update void update(Task t);
    @Delete void delete(Task t);

    @Query("DELETE FROM tasks")
    void deleteAll();
}
