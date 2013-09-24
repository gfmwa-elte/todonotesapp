package hu.plezervi.elte.gfmwa.todonotesapp.models;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by plezerv on 2013.09.24..
 */
public class Todo {
    private int id;
    private int userId;
    private String title;
    private String text;
    private Date modified;

    public Todo(int i, int u, String ttle, String txt, Date m) {
        this.id = i;
        this.userId = u;
        this.title = ttle;
        this.text = txt;
        this.modified = m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", modified=" + modified +
                '}';
    }

    public static Todo parseFromJSON(JSONObject obj) {
        int id = obj.optInt("id");
        int userId = obj.optInt("userId");
        String title = obj.optString("title");
        String text = obj.optString("text");
        Date date = null;
        return new Todo(id, userId, title, text, date);
    }
}
