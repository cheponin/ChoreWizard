package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddChoreActivity extends AppCompatActivity {

    /**
     * The member name.
     */
    private String mChoreName;

    /**
     * The member phone number.
     */
    private String mChoreFrequency;

    /** The URL where the database resides for adding additional members */
    private final static String CHORE_ADD_URL =
            "http://cssgate.insttech.washington.edu/~aclanton/project/addChore.php?";

    /** The variable that retrieves the data from the Member_Name text field in .xml file */
    private EditText mChoreNameEditText;

    /** The variable that retrieves the data from the Member_Phone text field in .xml file */
    private EditText mChoreFrequencyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);

        mChoreNameEditText = (EditText) findViewById(R.id.Chore_Name);
        mChoreFrequencyEditText = (EditText) findViewById(R.id.Chore_Frequency);

        Button addChoreButton = (Button) findViewById(R.id.add_chore_done_button);
        addChoreButton.setOnClickListener(new View.OnClickListener() {

            /**
             * The process that takes place after the addCourseButton is pressed
             * @param v the view of the activity
             */
            @Override
            public void onClick(View v) {
                String url = buildChoreURL(v);
                AddChoreTask task = new AddChoreTask();
                task.execute(new String[]{url.toString()});

                Intent intent = new Intent(AddChoreActivity.this, ManageChoresActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Builds the string at generates at the end of the URL once a member is added.
     * @param v is the view of the activity
     * @return the toString that was generated
     */
    private String buildChoreURL(View v) {

        StringBuilder sb = new StringBuilder(CHORE_ADD_URL);

        try {

            String chore = mChoreNameEditText.getText().toString();
            sb.append("name=");
            sb.append(chore);


            String frequency = mChoreFrequencyEditText.getText().toString();
            sb.append("&frequency=");
            sb.append(URLEncoder.encode(frequency, "UTF-8"));

            Log.i("Add chore", sb.toString());

        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * A class that is used to push Members and their information from the
     * application onto web server.
     */
    private class AddChoreTask extends AsyncTask<String, Void, String> {

        /**
         * Before the task is executed to set up the task.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Used to perform background computation that is passed back to the previous step.
         * @param urls are the urls that are created for future use
         * @return the stringt that is generated from the urls passed in
         */
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
                    response = "Unable to add chore, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
    }
}
