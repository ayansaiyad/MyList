package com.example.mylist;

// RecyclerView components
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
// For view handling
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
// For non-null annotation
import androidx.annotation.NonNull;
// For date formatting
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * RecyclerView Adapter for displaying tasks
 * Uses ListAdapter with DiffUtil for efficient updates
 * Handles item click events and task completion toggling
 */
public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {

    // Interface for handling item clicks
    public interface OnTaskClickListener {
        void onTaskClick(Task task); // For editing task
        void onTaskToggle(Task task); // For toggling completion
        void onTaskDelete(Task task); // For deleting task
    }

    // Click listener instance
    private OnTaskClickListener listener;

    // Date formatter for displaying dates
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    /**
     * Constructor
     * @param listener Click event listener
     */
    public TaskAdapter(OnTaskClickListener listener) {
        super(DIFF_CALLBACK); // Use DiffUtil for efficient updates
        this.listener = listener;
    }

    /**
     * DiffUtil callback for calculating differences between old and new lists
     * Enables efficient RecyclerView updates
     */
    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            // Items are the same if they have the same ID
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            // Check if all content is the same
            return oldItem.title.equals(newItem.title) &&
                    oldItem.description.equals(newItem.description) &&
                    oldItem.dueDate.equals(newItem.dueDate) &&
                    oldItem.done == newItem.done;
        }
    };

    /**
     * Create new ViewHolder instance
     * Called when RecyclerView needs a new item view
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    /**
     * Bind data to ViewHolder
     * Called when RecyclerView displays an item
     */
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = getItem(position);
        holder.bind(currentTask);
    }

    /**
     * ViewHolder class for task items
     * Holds references to UI components in each list item
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // UI components
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView dueDateTextView;
        private CheckBox completionCheckBox;

        /**
         * Constructor - finds and stores UI component references
         * @param itemView The item's root view
         */
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find UI components by ID
            titleTextView = itemView.findViewById(R.id.text_title);
            descriptionTextView = itemView.findViewById(R.id.text_description);
            dueDateTextView = itemView.findViewById(R.id.text_due_date);
            completionCheckBox = itemView.findViewById(R.id.checkbox_completion);

            // Set up click listeners
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onTaskClick(getItem(position));
                }
            });

            // Long click for delete
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onTaskDelete(getItem(position));
                }
                return true; // Consume the long click
            });

            // Checkbox click for toggling completion
            completionCheckBox.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onTaskToggle(getItem(position));
                }
            });
        }

        /**
         * Bind task data to UI components
         * @param task Task to display
         */
        public void bind(Task task) {
            titleTextView.setText(task.title);
            descriptionTextView.setText(task.description);
            dueDateTextView.setText(dateFormat.format(task.dueDate));
            completionCheckBox.setChecked(task.done);

            // Visual styling based on completion status
            if (task.done) {
                titleTextView.setAlpha(0.6f); // Fade completed tasks
                descriptionTextView.setAlpha(0.6f);
            } else {
                titleTextView.setAlpha(1.0f); // Full opacity for pending tasks
                descriptionTextView.setAlpha(1.0f);
            }
        }
    }
}
