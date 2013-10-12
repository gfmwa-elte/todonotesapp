package hu.plezervi.elte.gfmwa.todonotesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.models.Todo;

/**
* Created by plezerv on 2013.09.28..
*/
public class TodoSecondPageFragment extends Fragment {

    private TextView userIdTextView;
    private TextView todoIdTextView;
    private TextView todoDateTextView;
    private Todo todo;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        userIdTextView = (TextView) v.findViewById(R.id.fragmentSecondUserIdTextView);
        todoIdTextView = (TextView) v.findViewById(R.id.fragmentSecondTodoIdTextView);
        todoDateTextView = (TextView) v.findViewById(R.id.fragmentSecondTodoDateTextView);
        if(savedInstanceState != null) {
            todo = (Todo)savedInstanceState.getSerializable("todo");
        }
        return v;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(todo != null) {
            userIdTextView.setText(todo.getUserId() + "");
            todoIdTextView.setText(todo.getId() + "");
            todoDateTextView.setText(todo.getModified().toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("todo", todo);
    }
}
