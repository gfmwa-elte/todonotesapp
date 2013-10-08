package hu.plezervi.elte.gfmwa.todonotesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;
import hu.plezervi.elte.gfmwa.todonotesapp.fragments.LoginFragment;
import hu.plezervi.elte.gfmwa.todonotesapp.fragments.RegistrationFragment;

public class MainActivity extends ActionBarActivity implements LoginFragment.LoginFragmentListener, RegistrationFragment.RegistrationFragmentListener {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) Utils.debug(savedInstanceState.toString());
        loginFragment = new LoginFragment();
        loginFragment.setLoginFragmentListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mobileMainFragmentLayout, loginFragment);
        transaction.commit();
    }

    @Override
    public void getAFreeAccountButtonPressed() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RegistrationFragment regFragment = new RegistrationFragment();
        regFragment.setRegistrationFragmentListener(this);
        transaction.replace(R.id.mobileMainFragmentLayout, regFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void loginSuccessed() {
        startActivity(new Intent(this, InsideActivity.class));
        finish();
    }

    @Override
    public void registrationSuccessed() {
        FragmentManager man = getSupportFragmentManager();
        man.popBackStack();
    }
}
