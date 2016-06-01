package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.chorewizard.model.Chore;
import tcss450.uw.edu.chorewizard.model.Member;

/**
 * This class is deprecated. Saving it for now in case we need it in version 2.
 */
public class ManageChoresActivity extends AppCompatActivity {

    public ManageChoresActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chores);
    }
}