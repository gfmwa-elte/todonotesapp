package hu.plezervi.elte.gfmwa.todonotesapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import hu.plezervi.elte.gfmwa.todonotesapp.Globals;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.models.Todo;
import hu.plezervi.elte.gfmwa.todonotesapp.net.TodoListAsyncTask;

import java.util.List;

/**
 * Created by plezerv on 2013.09.24..
 */
public class InsideActivity extends ActionBarActivity implements TodoListAsyncTask.TodoListAsyncTaskListener {

    private TodoListAsyncTask todoListAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        todoListAsyncTask = new TodoListAsyncTask(this, this);
        todoListAsyncTask.execute(Globals.LOGGED_USER_ID);
    }

    @Override
    public void todoListDownloaded(List<Todo> todos) {
        ((TextView)findViewById(R.id.insideText)).setText(todos.toString());
    }
}
