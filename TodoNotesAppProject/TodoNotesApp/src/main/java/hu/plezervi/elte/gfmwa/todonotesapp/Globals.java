package hu.plezervi.elte.gfmwa.todonotesapp;

/**
 * Created by plezerv on 2013.09.20..
 */
public class Globals {

    public static int LOGGED_USER_ID = -1;

    private static final String urlRoot = "http://coelho.mudkip.d-lan.hu:8081";
    private static final String registerUrl = "/register";
    private static final String loginUrl = "/login";
    private static final String todosUrl = "/todos?userid=";

    public static final String LOGTAG = "GFMWATODONOTES";

    public static String getRegisterUrl() {
        return urlRoot + registerUrl;
    }

    public static String getLoginUrl() {
        return urlRoot + loginUrl;
    }

    public static String getTodosUrl(int userId) {
        return urlRoot + todosUrl + userId;
    }

    public static String getTodosUrl(int userId, int limit, int offset) {
        return urlRoot + todosUrl + userId + "&limit=" + limit + "&offset=" + offset;
    }
}
