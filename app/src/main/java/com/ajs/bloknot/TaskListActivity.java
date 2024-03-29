package com.ajs.bloknot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.ajs.bloknot.database.AppDatabase;
import com.ajs.bloknot.database.DatabaseDelete;
import com.ajs.bloknot.database.DatabaseDelete.DbDeleteInterface;
import com.ajs.bloknot.database.DatabaseFetch.DbFetchInterface;
import com.ajs.bloknot.database.DatabaseInsert;
import com.ajs.bloknot.database.DatabaseInsert.DbInsertInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TaskDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TaskListActivity extends AppCompatActivity implements DbFetchInterface, DbInsertInterface, DbDeleteInterface {

    public TaskDao dao;
    AppDatabase database;
    SimpleItemRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Task> tasks;
    Bundle savedInstanceState;

    public static int theme = R.style.Matrix;
    public static final int CREATE_TASK = 1;
    public static final int UPDATE_TASK = 2;
    public static final int VIEW_OR_MODIFY_TASK = 3;
    public static final String TASK_LIST = "taskList";
    public static final String NEXT_TASK_ID = "nextTaskId";
    public static final String NEW_TASK = "newTask";
    public static final String TASK_FOR_UPDATE = "taskForDeletion";
    public static final String TASK_FOR_DETAIL_VIEW = "taskForDetailView";
    public static final String THEME = "theme";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getIntent().getExtras().containsKey(THEME)) {
            theme = getIntent().getExtras().getInt(THEME);
            setTheme(theme);
        }
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Fetch loaded data.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(TASK_LIST)) {
            tasks = bundle.getParcelableArrayList(TASK_LIST);
        } else {
            tasks = new ArrayList<>();
        }
        if (getIntent().getExtras().containsKey(TASK_LIST)) {
            tasks = getIntent().getParcelableArrayListExtra(TASK_LIST);
        }

        // Setup RecyclerView.
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        // Setup database.
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db").build();
        dao = database.taskDao();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, tasks, mTwoPane);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     * Transitions App into the activity for the creation of new Tasks
     */
    public void goToTaskCreationActivity(View view) {
        Intent intent = new Intent(this, TaskCreation.class);
        intent.putExtra(NEXT_TASK_ID, tasks.size());
        startActivityForResult(intent, CREATE_TASK);
    }

    /**
     * Callback for the completion of a database SELECT.
     */
    @Override
    public void completeFetch(List<Task> fetchResults) {
        tasks = new ArrayList<>(fetchResults);
    }

    /**
     * Callback for the completion of a database INSERT.
     */
    @Override
    public void completeInsert(TaskDao taskDao) {
        System.out.println("Inserted new Task into " + taskDao);
    }

    /**
     * Callback for the completion of a database DELETE.
     */
    @Override
    public void completeDelete(TaskDao taskDao) {
        System.out.println("Deleted Task from " + taskDao);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (data != null && data.getExtras() != null) {
            switch (resultCode) {
                case CREATE_TASK:
                    if (data.getExtras().containsKey(NEW_TASK)) {
                        Task newTask = data.getExtras().getParcelable(NEW_TASK);
                        createTask(newTask);
                    }
                case UPDATE_TASK:
                    if (data.getExtras().containsKey(TASK_FOR_UPDATE)) {
                        Task taskForUpdate = data.getExtras().getParcelable(TASK_FOR_UPDATE);
                        updateTask(taskForUpdate);
                    }
            }
            recyclerViewAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Insert a new Task into the database
     */
    public void createTask(Task task) {
        tasks.add(task);
        new DatabaseInsert(this).execute(task);
        System.out.println("Created new Task: " + task);
    }

    /**
     * Delete a Task from the database
     */
    public void deleteTask(Task task) {
        tasks.remove(task);
        new DatabaseDelete(this).execute(task);
        System.out.println("Deleted: " + task);
    }

    /**
     * Updates the Task by removing and re-inserting
     */
    private void updateTask(Task task) {
        deleteTask(task);
        createTask(task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * OnClick for the Menu, allows toggling of the theme. The two options are Matrix and Aether
     */
    public void toggleTheme(MenuItem menuItem) {

        // Toggle the theme
        if (theme == R.style.Matrix) {
            theme = R.style.Aether;
        } else {
            theme = R.style.Matrix;
        }

        // Restart the Application, sending the correct Task list so that it is not loaded from the
        // older SplashScreen's delivered data.
        Intent intent = getIntent();
        intent.putExtra(THEME, theme);
        intent.putParcelableArrayListExtra(TASK_LIST, tasks);
        finish();
        startActivity(intent);

    }

    /**
     * Custom implementation of RecyclerView
     */
    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final TaskListActivity parentActivity;

        // The content of this RecyclerView.
        private final List<Task> values;

        // Boolean indicating if the screen size is large enough for a two-pane system.
        private final boolean mTwoPane;

        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task item = (Task) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(TaskDetailFragment.ARG_ITEM_ID, item.id);
                    TaskDetailFragment fragment = new TaskDetailFragment();
                    fragment.setArguments(arguments);
                    parentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, item.id);
                    intent.putExtra(TASK_FOR_DETAIL_VIEW, item);
                    parentActivity.startActivityForResult(intent, VIEW_OR_MODIFY_TASK);
                }


            }
        };

        /**
         * RecyclerView constructor.
         */
        SimpleItemRecyclerViewAdapter(TaskListActivity parent, List<Task> items, boolean twoPane) {
            values = items;
            parentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.idView.setText(String.valueOf(values.get(position).id));
            holder.contentView.setText(values.get(position).name);
            holder.itemView.setTag(values.get(position));
            holder.itemView.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return values.size();
        }

        /**
         * Delete a Task
         */
        void removeAt(int position) {

            // Delete from main database
            parentActivity.deleteTask(values.get(position));

            // Update UI
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, values.size());

        }

        /**
         * Functionality for CheckBox that allows removal of Tasks
         */
        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView idView;
            final TextView contentView;
            final CheckBox checkBox;

            ViewHolder(View view) {
                super(view);
                idView = view.findViewById(R.id.id_text);
                contentView = view.findViewById(R.id.content);
                checkBox = view.findViewById(R.id.check_box);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        removeAt(getAdapterPosition());
                    }
                });
            }

        }

    }

}
