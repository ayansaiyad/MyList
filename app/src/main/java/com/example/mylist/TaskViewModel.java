package com.example.mylist;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.app.Application;
import java.util.List;

//maintain and expose task data to the UI ,surviving configuration changes
public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository repo;
    public LiveData<List<Task>> tasks;
    public TaskViewModel(@NonNull Application a){
        super(a);
        repo = new TaskRepository(a);
        tasks = repo.getAllTasks();
    }
    public LiveData<List<Task>> getAllTasks() {
        return tasks;
    }
    public LiveData<List<Task>> getCompletedTasks() {
        return repo.getCompletedTasks();
    }

    public LiveData<List<Task>> getPendingTasks() {
        return repo.getPendingTasks();
    }


    public void addTask(Task task) {
        repo.insert(task);
    }
    public void updateTask(Task task) {
        repo.update(task);
    }
    public void toggleTaskCompletion(Task task) {
        task.done = !task.done; // Flip the completion status
        repo.update(task); // Update in database
    }
    public void deleteTask(Task task) {
        repo.delete(task);
    }
    public void deleteAllTasks() {
        repo.deleteAll();
    }
}
