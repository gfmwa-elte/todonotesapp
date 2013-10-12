package hu.plezervi.elte.gfmwa.todonotesapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import hu.plezervi.elte.gfmwa.todonotesapp.Globals;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;
import hu.plezervi.elte.gfmwa.todonotesapp.fragments.TodoDetailerFragment;
import hu.plezervi.elte.gfmwa.todonotesapp.fragments.TodoListFragment;
import hu.plezervi.elte.gfmwa.todonotesapp.models.Todo;
import hu.plezervi.elte.gfmwa.todonotesapp.net.TodoListAsyncTask;

import java.util.List;

/**
 * Created by plezerv on 2013.09.24..
 */
public class InsideActivity extends ActionBarActivity implements TodoListFragment.TodoListFragmentListener {

    private TodoListAsyncTask todoListAsyncTask;
    private boolean isSinglePane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        if(findViewById(R.id.insideFragmentLayout) != null) {
            // mobile layout
            isSinglePane = true;
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            TodoListFragment todoListFragment = new TodoListFragment();
            todoListFragment.setTodoListFragmentListener(this);
            transaction.add(R.id.insideFragmentLayout, todoListFragment);
            transaction.commit();
        } else {
            // tablet layout
            isSinglePane = false;
            ((TodoListFragment)getSupportFragmentManager().findFragmentById(R.id.listfragment)).setTodoListFragmentListener(this);
        }
    }

    @Override
    public void todoClicked(Todo todo) {
        if(isSinglePane) {
            TodoDetailerFragment fragment = new TodoDetailerFragment();
            fragment.setTodo(todo);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.insideFragmentLayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            ((TodoDetailerFragment)getSupportFragmentManager().findFragmentById(R.id.detailerfragment)).setTodo(todo);
        }
    }
}
