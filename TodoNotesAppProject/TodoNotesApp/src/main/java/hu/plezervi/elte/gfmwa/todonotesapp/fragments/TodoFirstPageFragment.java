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
public class TodoFirstPageFragment extends Fragment {

    private TextView titleTextView;
    private TextView textTextView;
    private Todo todo;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        titleTextView = (TextView) v.findViewById(R.id.fragmentFirstTodoTitleView);
        textTextView = (TextView) v.findViewById(R.id.fragmentFirstTodoTextView);
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
            titleTextView.setText(todo.getTitle());
            textTextView.setText(todo.getText());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("todo", todo);
    }
}
