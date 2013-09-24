package hu.plezervi.elte.gfmwa.todonotesapp.net;

import android.content.Context;
import hu.plezervi.elte.gfmwa.todonotesapp.GFMWAException;
import hu.plezervi.elte.gfmwa.todonotesapp.Globals;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;
import hu.plezervi.elte.gfmwa.todonotesapp.models.LoginInfo;
import hu.plezervi.elte.gfmwa.todonotesapp.models.Todo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plezerv on 2013.09.24..
 */
public class TodoListAsyncTask extends BaseAsyncTask<Integer, Void, JSONArray> {

    public interface TodoListAsyncTaskListener {
        void todoListDownloaded(List<Todo> todos);
    }

    private TodoListAsyncTaskListener listener;

    public TodoListAsyncTask(Context context, TodoListAsyncTaskListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        try {
            int userId = integers[0].intValue();
            return new JSONArray(doGet(Globals.getTodosUrl(userId)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void process(JSONArray result) {
        if(listener != null) {
            Utils.debug(result.toString());
            List<Todo> todos = new ArrayList<Todo>();
            for(int i = 0; i < result.length(); ++i) {
                try {
                    JSONObject obj = result.getJSONObject(i);
                    todos.add(Todo.parseFromJSON(obj));
                } catch (JSONException e) {// TODO do something}
                }
            }
            Utils.debug(todos.toString());
            listener.todoListDownloaded(todos);
        }
    }

    protected void networkError(int code) {
        if(listener != null) listener.todoListDownloaded(null);
    }

    protected void error() {
        if(listener != null) listener.todoListDownloaded(null);
    }
}
