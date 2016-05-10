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

public class ManageChoresActivity extends AppCompatActivity {

    /** The URL to access the Course table of the database. */
    private static final String CHORE_URL
            = "http://cssgate.insttech.washington.edu/~aclanton/project/projectChoreTest.php?cmd=chore";

    public ManageChoresActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chores);

        DownloadChoresTask task = new DownloadChoresTask();
        task.execute(new String[]{CHORE_URL});
    }

    public void clickAddChore(View view) {
        Intent intent = new Intent(this, AddChoreActivity.class);
        startActivity(intent);
    }

    /**
     * A class that is used to download Members and their information from the
     * web service into the application to be displayed on the home screen.
     */
    private class DownloadChoresTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of chores, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * Executes one of the options below depending on the data recieved.
         * @param result the final statement that was executed
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<Chore> choreList = new ArrayList<Chore>();
            result = Chore.parseChoreJSON(result, choreList);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of members.
            if (!choreList.isEmpty()) {
                for(int i = 0; i < choreList.size(); i++) {
                    Chore choreObject = choreList.get(i);
                    switch (i) {
                        case 0:
                            TextView chore = (TextView) findViewById(R.id.chore_1);
                            chore.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 1:
                            TextView chore2 = (TextView) findViewById(R.id.chore_2);
                            chore2.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 2:
                            TextView chore3 = (TextView) findViewById(R.id.chore_3);
                            chore3.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 3:
                            TextView chore4 = (TextView) findViewById(R.id.chore_4);
                            chore4.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 4:
                            TextView chore5 = (TextView) findViewById(R.id.chore_5);
                            chore5.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 5:
                            TextView chore6 = (TextView) findViewById(R.id.chore_6);
                            chore6.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 6:
                            TextView chore7 = (TextView) findViewById(R.id.chore_7);
                            chore7.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 7:
                            TextView chore8 = (TextView) findViewById(R.id.chore_8);
                            chore8.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 8:
                            TextView chore9 = (TextView) findViewById(R.id.chore_9);
                            chore9.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                        case 9:
                            TextView chore10 = (TextView) findViewById(R.id.chore_10);
                            chore10.setText(choreObject.getmChoreName() + " - " + choreObject.getmChoreFrequency());
                            break;
                    }
                }
            }
        }

    }
}
