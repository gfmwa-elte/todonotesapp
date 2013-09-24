package hu.plezervi.elte.gfmwa.todonotesapp.net;

import android.content.Context;
import hu.plezervi.elte.gfmwa.todonotesapp.Globals;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.models.LoginInfo;
import hu.plezervi.elte.gfmwa.todonotesapp.models.RegInfo;
import org.json.JSONObject;

/**
 * Created by plezerv on 2013.09.24..
 */
public class LoginAsyncTask extends BaseAsyncTask<LoginInfo, Void, JSONObject> {

    public interface LoginAsyncTaskListener {
        void loginFinished(boolean success, String errorMsg);
    }

    private LoginAsyncTaskListener listener;
    private Exception x;

    public LoginAsyncTask(Context context, LoginAsyncTaskListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(LoginInfo... loginInfos) {
        if(loginInfos.length > 0) {
            try {
                LoginInfo info = loginInfos[0];
                JSONObject message = new JSONObject();
                message.put("uname", info.getUsername());
                message.put("pwd", info.getPassword());
                String str = doPost(Globals.getLoginUrl(), message.toString());
                if(str == null) return null;
                else return new JSONObject(str);
            } catch (Exception e) {
                x = e;
                return null;
            }
        }
        return null;
    }

    @Override
    protected void process(JSONObject result) {
        if(listener != null) {
            boolean error = result.optBoolean("error", true);
            if(!error) {
                JSONObject obj = result.optJSONObject("userInfo");
                if(obj != null) {
                    int userId = obj.optInt("userId", -1);
                    Globals.LOGGED_USER_ID = userId;
                    listener.loginFinished(userId != -1, null);
                } else {
                    listener.loginFinished(false, null);
                }
            } else {
                listener.loginFinished(false, null);
            }
        }
    }

    protected void networkError(int code) {
        if(listener != null) listener.loginFinished(false, context.getResources().getText(R.string.networkerror).toString());
    }

    protected void error() {
        String msg = x == null ? null : x.getMessage();
        if(listener != null) listener.loginFinished(false, msg);
    }
}
