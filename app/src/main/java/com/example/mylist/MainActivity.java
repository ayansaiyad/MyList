package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

// UI components
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

// ViewModel
import androidx.lifecycle.ViewModelProvider;

// For dialogs
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

// For date picker
import com.google.android.material.datepicker.MaterialDatePicker;

// Java/Android basics
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

/// shows the list of user's pending tasks
public class MainActivity extends AppCompatActivity {

    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // session check
        SessionPrefs session = new SessionPrefs(this);
        if (!session.isLoggedIn()) {
            // Not logged in, redirect to login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        // UI initilization
        setContentView(R.layout.activity_main); // ← the XML from earlier
        // logout button
        Button logoutBtn = findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(v -> {
            // Destroy login session
            // SessionPrefs session = new SessionPrefs(this);
            session.logout();

            // Return to login screen
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // Prevent back navigation to main
        });

        // 1. Setup RecyclerView to display all pending tasks
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Setup Adapter and attach listeners
        adapter = new TaskAdapter(new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task) {
                showTaskDialog(task); // ← edit task
            }

            @Override
            public void onTaskToggle(Task task) {
                taskViewModel.toggleTaskCompletion(task); // toggle done/undone
            }

            @Override
            public void onTaskDelete(Task task) {
                taskViewModel.deleteTask(task);
                Snackbar.make(recyclerView, "Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> taskViewModel.addTask(task))
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);

        // 3. Set up ViewModel with LiveData observation
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getPendingTasks().observe(this, tasks -> {
            adapter.submitList(tasks); // automatically updates UI
        });

        // 4. Setup FloatingActionButton (add new task)
        fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(view -> showTaskDialog(null));
    }

    /**
     * Opens a dialog to create or edit a task
     * 
     * @param task if null → new task, else edit existing
     */
    private void showTaskDialog(Task task) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.task_dialog, null);

        EditText titleEt = dialogView.findViewById(R.id.edit_task_title);
        EditText descEt = dialogView.findViewById(R.id.edit_task_description);
        Button dateBtn = dialogView.findViewById(R.id.btn_select_due_date);
        CheckBox doneCb = dialogView.findViewById(R.id.checkbox_done);

        final long[] selectedDate = { System.currentTimeMillis() }; // default NOW

        // If editing, populate fields
        if (task != null) {
            titleEt.setText(task.title);
            descEt.setText(task.description);
            doneCb.setChecked(task.done);
            selectedDate[0] = task.dueDate.getTime();
        }

        // Date picker using MaterialDatePicker
        dateBtn.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(selectedDate[0])
                    .build();
            picker.show(getSupportFragmentManager(), "date_picker");
            picker.addOnPositiveButtonClickListener(selection -> {
                selectedDate[0] = selection;
                dateBtn.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection)));

            });
        });

        // Create AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle(task == null ? "New Task" : "Edit Task")
                .setPositiveButton(task == null ? "Save" : "Update", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(dlg -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                String title = titleEt.getText().toString().trim();
                String desc = descEt.getText().toString().trim();
                boolean isDone = doneCb.isChecked();

                if (title.isEmpty()) {
                    titleEt.setError("Title required!");
                    return;
                }

                if (task == null) {
                    Task newTask = new Task(title, desc, new Date(selectedDate[0]), isDone);
                    taskViewModel.addTask(newTask);
                } else {
                    task.title = title;
                    task.description = desc;
                    task.dueDate = new Date(selectedDate[0]);
                    task.done = isDone;
                    taskViewModel.updateTask(task);
                }

                dialog.dismiss();
            });
        });

        dialog.show();
    }

}
