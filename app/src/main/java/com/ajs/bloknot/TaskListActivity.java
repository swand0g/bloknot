package com.ajs.bloknot;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.ajs.bloknot.database.AppDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TaskDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TaskListActivity extends AppCompatActivity {

    public TaskDao dao;
    AppDatabase database;
    SimpleItemRecyclerViewAdapter recyclerViewAdapter;
    List<Task> tasks;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (bundle != null && bundle.containsKey("data")) {
            tasks = bundle.getParcelableArrayList("data");
        } else {
            tasks = new ArrayList<>();
        }
        System.out.println(tasks.size());

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
        recyclerViewAdapter.notifyDataSetChanged();
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

    public void goToTaskCreationActivity(View view) {
        Intent intent = new Intent(this, TaskCreation.class);
        startActivity(intent);
    }

    private void sampleDatabaseProcess() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Task task = new Task("a new task", 1, "no details", new Date());
                dao.insert(task);
                Task newTask = dao.getAll().get(0);
                System.out.println("Retrieved a Task from DB with name: " + newTask.name);
                dao.delete(task);
                System.out.println("Removed previously retrieved Task from DB. New DB size: " + dao.getAll().size());
            }
        });
    }

    @SuppressWarnings("JavaDoc")
    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final TaskListActivity parentActivity;

        // The content of this RecyclerView.
        private final List<Task> values;

        // Boolean indicating if the screen size is large enough for a two-pane system.
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
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
                    context.startActivity(intent);
                }


            }
        };

        /**
         * RecyclerView constructor.
         * @param parent
         * @param items
         * @param twoPane
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
            holder.itemView.setOnClickListener(mOnClickListener);

        }

        @Override
        public int getItemCount() {
            return values.size();
        }

        public void removeAt(int position) {
            values.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, values.size());
        }

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
