package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * The add member screen of the application which provides a form
 * for the user to fill out to add a new household member.
 */
public class AddMemberActivity extends AppCompatActivity {

    /**
     * The member name.
     */
    private String mMemberName;

    /**
     * The member phone number.
     */
    private String mMemberPhone;

    /** The URL where the database resides for adding additional members */
    private final static String MEMBER_ADD_URL =
            "http://cssgate.insttech.washington.edu/~aclanton/project/addMember.php?";

    /** The variable that retrieves the data from the Member_Name text field in .xml file */
    private EditText mMemberNameEditText;

    /** The variable that retrieves the data from the Member_Phone text field in .xml file */
    private EditText mMemberPhoneEditText;

    /**
     * Where the activity is initialized.
     * @param savedInstanceState is the previous instance the program generated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mMemberNameEditText = (EditText) findViewById(R.id.Member_Name);
        mMemberPhoneEditText = (EditText) findViewById(R.id.Member_Phone);

        Button addMemberButton = (Button) findViewById(R.id.add_member_done_button);
        addMemberButton.setOnClickListener(new View.OnClickListener() {

            /**
             * The process that takes place after the addCourseButton is pressed
             * @param v the view of the activity
             */
            @Override
            public void onClick(View v) {
                String url = buildMemberURL(v);
                AddMemberTask task = new AddMemberTask();
                task.execute(new String[]{url.toString()});

                Intent intent = new Intent(AddMemberActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Builds the string at generates at the end of the URL once a member is added.
     * @param v is the view of the activity
     * @return the toString that was generated
     */
    private String buildMemberURL(View v) {

        StringBuilder sb = new StringBuilder(MEMBER_ADD_URL);

        try {

            String name = mMemberNameEditText.getText().toString();
            sb.append("name=");
            sb.append(name);


            String phone = mMemberPhoneEditText.getText().toString();
            sb.append("&phone=");
            sb.append(URLEncoder.encode(phone, "UTF-8"));

            Log.i("Add member", sb.toString());

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
    private class AddMemberTask extends AsyncTask<String, Void, String> {

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
                    response = "Unable to add member, Reason: "
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
