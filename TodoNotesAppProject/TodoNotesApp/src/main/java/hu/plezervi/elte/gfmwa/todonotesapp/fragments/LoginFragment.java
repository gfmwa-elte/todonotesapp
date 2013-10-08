package hu.plezervi.elte.gfmwa.todonotesapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.activities.InsideActivity;
import hu.plezervi.elte.gfmwa.todonotesapp.models.LoginInfo;
import hu.plezervi.elte.gfmwa.todonotesapp.net.LoginAsyncTask;

/**
 * Created by plezerv on 2013.09.19..
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginAsyncTask.LoginAsyncTaskListener {

    public interface LoginFragmentListener {
        void getAFreeAccountButtonPressed();
        void loginSuccessed();
    }

    private LoginFragmentListener listener;
    private TextView getAFreeButton;
    private EditText userNameField;
    private EditText passwordField;
    private Button loginButton;

    private ProgressDialog pDialog;
    private LoginAsyncTask loginAsyncTask;

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(userNameField.getText().length() == 0 || passwordField.getText().length() == 0) loginButton.setEnabled(false);
            else loginButton.setEnabled(true);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    public void setLoginFragmentListener(LoginFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        getAFreeButton = (TextView) v.findViewById(R.id.registrationTextView);
        getAFreeButton.setOnClickListener(this);

        userNameField = (EditText) v.findViewById(R.id.loginUserNameField);
        userNameField.addTextChangedListener(watcher);

        passwordField = (EditText) v.findViewById(R.id.loginPasswordField);
        passwordField.addTextChangedListener(watcher);

        loginButton = (Button) v.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        loginButton.setEnabled(false);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registrationTextView: if(listener != null) listener.getAFreeAccountButtonPressed(); break;
            case R.id.loginButton: startLogin(); break;
        }
    }

    private void startLogin() {
        if(getActivity() != null) {
            Resources res = getResources();
            pDialog = ProgressDialog.show(getActivity(), null, res.getString(R.string.login) + res.getString(R.string.dotdotdot));
            if(loginAsyncTask != null) loginAsyncTask.cancel(true);
            loginAsyncTask = new LoginAsyncTask(getActivity(), this);
            LoginInfo info = new LoginInfo(userNameField.getText().toString(), passwordField.getText().toString());
            loginAsyncTask.execute(info);
        }
    }

    @Override
    public void loginFinished(boolean success, String errorMsg) {
        if(pDialog != null) pDialog.dismiss();
        if(success) {
            if(listener != null) listener.loginSuccessed();
        } else {
            if(getActivity() != null) {
                String msg = errorMsg == null ? getResources().getString(R.string.loginerror) : errorMsg;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.error);
                builder.setMessage(msg);
                builder.setNeutralButton(android.R.string.ok, null);
                builder.show();
            }
        }
    }

}
