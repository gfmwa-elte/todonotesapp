package hu.plezervi.elte.gfmwa.todonotesapp.net;

import android.content.Context;
import hu.plezervi.elte.gfmwa.todonotesapp.Globals;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;
import hu.plezervi.elte.gfmwa.todonotesapp.models.RegInfo;
import org.json.JSONObject;

/**
 * Created by plezerv on 2013.09.24..
 */
public class RegistrationAsyncTask extends BaseAsyncTask<RegInfo, Void, JSONObject> {

    public interface RegistrationAsyncTaskListener {
        void registrationFinished(boolean success, String errorMessage);
    }

    private RegistrationAsyncTaskListener listener;
    private Exception x;

    public RegistrationAsyncTask(Context context, RegistrationAsyncTaskListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(RegInfo... regInfos) {
        if(regInfos.length > 0) {
            try {
                RegInfo info = regInfos[0];
                JSONObject message = new JSONObject();
                message.put("uname", info.getUsername());
                message.put("pwd", info.getPassword());
                message.put("mail", info.getMailaddress());
                String str = doPost(Globals.getRegisterUrl(), message.toString());
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
                listener.registrationFinished(true, null);
            } else {
                JSONObject messages = result.optJSONObject("errorMessages");
                if(messages != null) {
                    String errMsg = messages.optString(context.getString(R.string.langcode), null);
                    listener.registrationFinished(false, errMsg);
                } else {
                    listener.registrationFinished(false, null);
                }
            }
        }
    }

    protected void networkError(int code) {
        if(listener != null) listener.registrationFinished(false, context.getResources().getText(R.string.networkerror).toString());
    }

    protected void error() {
        String msg = x == null ? null : x.getMessage();
        if(listener != null) listener.registrationFinished(false, msg);
    }
}
