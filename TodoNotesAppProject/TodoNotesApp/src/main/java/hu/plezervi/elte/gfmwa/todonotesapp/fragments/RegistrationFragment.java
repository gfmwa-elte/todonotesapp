package hu.plezervi.elte.gfmwa.todonotesapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;
import hu.plezervi.elte.gfmwa.todonotesapp.GFMWAException;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;
import hu.plezervi.elte.gfmwa.todonotesapp.models.RegInfo;
import hu.plezervi.elte.gfmwa.todonotesapp.net.RegistrationAsyncTask;

/**
 * Created by plezerv on 2013.09.19..
 */
public class RegistrationFragment extends Fragment implements RegistrationAsyncTask.RegistrationAsyncTaskListener {

    public interface RegistrationFragmentListener {
        void registrationSuccessed();
    }

    private RegistrationFragmentListener listener;
    private RegistrationAsyncTask registrationAsyncTask;
    private ProgressDialog pDialog;

    private EditText userNameField;
    private EditText passwordOneField;
    private EditText passwordTwoField;
    private EditText mailField;
    private Button regButton;

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(userNameField.getText().length() == 0 || passwordOneField.getText().length() == 0 ||
                    passwordTwoField.getText().length() == 0 || mailField.getText().length() == 0) regButton.setEnabled(false);
            else {
                if(!passwordOneField.getText().toString().equals(passwordTwoField.getText().toString())) regButton.setEnabled(false);
                else {
                    if(!Utils.isEmailValid(mailField.getText().toString())) regButton.setEnabled(false);
                    else regButton.setEnabled(true);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    public void setRegistrationFragmentListener(RegistrationFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);

        userNameField = (EditText) v.findViewById(R.id.regUserNameField);
        userNameField.addTextChangedListener(watcher);

        passwordOneField = (EditText) v.findViewById(R.id.regPasswordField);
        passwordOneField.addTextChangedListener(watcher);

        passwordTwoField = (EditText) v.findViewById(R.id.regPasswordAgainField);
        passwordTwoField.addTextChangedListener(watcher);

        mailField = (EditText) v.findViewById(R.id.regMailField);
        mailField.addTextChangedListener(watcher);

        regButton = (Button) v.findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegistration();
            }
        });
        regButton.setEnabled(false);

        return v;
    }

    private void startRegistration() {
        try {
            if(getActivity() != null) {
                Resources res = getResources();
                pDialog = ProgressDialog.show(getActivity(), null, res.getString(R.string.registration) + res.getString(R.string.dotdotdot));
                RegInfo info = new RegInfo(userNameField.getText().toString(), passwordOneField.getText().toString(), mailField.getText().toString());
                if(registrationAsyncTask != null) registrationAsyncTask.cancel(true);
                registrationAsyncTask = new RegistrationAsyncTask(getActivity(), this);
                registrationAsyncTask.execute(info);
            }
        } catch (GFMWAException e) {
            // TODO error handling
        }
    }

    @Override
    public void registrationFinished(boolean success, String errorMessage) {
        if(pDialog != null) pDialog.dismiss();
        if(success) {
            if(getActivity() != null)
                Toast.makeText(getActivity(), R.string.registrationsucc, Toast.LENGTH_SHORT).show();
            if(listener != null) listener.registrationSuccessed();
        } else {
            if(getActivity() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.networkerror);
                builder.setNeutralButton(android.R.string.ok, null);
                builder.show();
            }
        }
    }
}
