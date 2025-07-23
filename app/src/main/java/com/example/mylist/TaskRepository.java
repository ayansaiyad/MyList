package com.example.mylist;
// For Room's database access
import androidx.lifecycle.LiveData;

// For Executors (background threads)
import java.util.concurrent.Executors;

// For using List (collection of tasks)
import java.util.List;
import android.content.Context;



// manages all data operation for tasks
public class TaskRepository {
    private final TaskDao dao;
    public TaskRepository(Context c){
        dao = AppDb.get(c).dao();
    }

    public LiveData<List<Task>> getAllTasks(){
        return dao.getAll();
    }
    public LiveData<List<Task>> getPendingTasks() {
        return dao.getPendingTasks();
    }
    public LiveData<List<Task>> getCompletedTasks() {
        return dao.getCompletedTasks();
    }
    public void insert(Task t){ Executors.newSingleThreadExecutor().execute(() -> dao.insert(t)); }
    public void update(Task t){ Executors.newSingleThreadExecutor().execute(() -> dao.update(t)); }
    public void delete(Task t){ Executors.newSingleThreadExecutor().execute(() -> dao.delete(t)); }
    public void deleteAll() {
        // Execute database operation on background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            dao.deleteAll();
        });
    }
}
