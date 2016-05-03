package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tcss450.uw.edu.chorewizard.authenticate.SignInActivity;

/**
 * The welcome screen of the app which serves as a tutorial for
 * the user.
 *
 * @version April 19, 2016
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    /**
     * The action of the Ok button in the welcome screen. Takes
     * the user to the SignInActivity.
     *
     * @param view the view of the activity.
     */
    public void clickOk(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

}
