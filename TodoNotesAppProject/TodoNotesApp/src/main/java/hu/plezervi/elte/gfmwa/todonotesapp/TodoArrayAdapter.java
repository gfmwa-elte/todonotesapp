package hu.plezervi.elte.gfmwa.todonotesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import hu.plezervi.elte.gfmwa.todonotesapp.models.Todo;

import java.util.List;

/**
* Created by plezerv on 2013.09.28..
*/
public class TodoArrayAdapter extends ArrayAdapter<Todo> {

    static class TodoViewHolder {
        TextView titleView;
        TextView dateView;
    }

    private List<Todo> todos;

    public TodoArrayAdapter(Context context, List<Todo> todos) {
        super(context, 0, todos);
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View v = null;
        TodoViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_todo, null);
            holder = new TodoViewHolder();
            holder.titleView = (TextView) v.findViewById(R.id.todoTitleView);
            holder.dateView = (TextView) v.findViewById(R.id.todoDateView);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (TodoViewHolder) v.getTag();
        }
        Todo todo = todos.get(position);
        holder.titleView.setText(todo.getTitle());
        holder.dateView.setText(todo.getModified().toString());
        return v;
    }
}
