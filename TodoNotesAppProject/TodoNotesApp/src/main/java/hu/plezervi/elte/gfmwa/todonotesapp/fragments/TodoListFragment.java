package hu.plezervi.elte.gfmwa.todonotesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import hu.plezervi.elte.gfmwa.todonotesapp.Globals;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.TodoArrayAdapter;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;
import hu.plezervi.elte.gfmwa.todonotesapp.models.Todo;
import hu.plezervi.elte.gfmwa.todonotesapp.net.TodoListAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
* Created by plezerv on 2013.09.28..
*/
public class TodoListFragment extends ListFragment implements TodoListAsyncTask.TodoListAsyncTaskListener {

    public interface TodoListFragmentListener {
        void todoClicked(Todo todo);
    }

    private TodoListAsyncTask listAsyncTask;
    private TodoArrayAdapter adapter;
    private TodoListFragmentListener listener;
    private List<Todo> todos;

    public void setTodoListFragmentListener(TodoListFragmentListener listener) {
        this.listener = listener;
    }

    public void onSaveInstanceState (Bundle outState) {
        outState.putSerializable("todolist", (ArrayList<Todo>)todos);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todolist, container, false);
        if(savedInstanceState != null) {
            todos = (ArrayList<Todo>)savedInstanceState.getSerializable("todolist");
        } else {
            todos = null;
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(todos != null) {
            todoListDownloaded(todos);
        } else {
            if(getActivity() != null) {
                listAsyncTask = new TodoListAsyncTask(getActivity() ,this);
                listAsyncTask.execute(Globals.LOGGED_USER_ID);
            }
        }
    }

    @Override
    public void todoListDownloaded(List<Todo> todos) {
        this.todos = todos;
        if(getActivity() != null) {
            adapter = new TodoArrayAdapter(getActivity(), todos);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        if(listener != null) listener.todoClicked(todos.get(position));
    }
}
