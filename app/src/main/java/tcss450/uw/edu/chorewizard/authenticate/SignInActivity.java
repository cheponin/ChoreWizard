package tcss450.uw.edu.chorewizard.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tcss450.uw.edu.chorewizard.HomeActivity;
import tcss450.uw.edu.chorewizard.R;

/**
 * The sign in screen of the application which holds the login
 * fragment that allows the user to log in to the system.
 */
public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginInteractionListener,
        LoginFragment.OnFragmentInteractionListener {

    /** The shared preferences of the application. */
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        } else {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public void login(String userId, String pwd) {

        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();


        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
