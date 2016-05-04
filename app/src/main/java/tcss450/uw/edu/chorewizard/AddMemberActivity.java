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

    private final static String COURSE_ADD_URL =
            "http://cssgate.insttech.washington.edu/~aclanton/project/addMember.php?";

    private MemberAddListener mListener;

    private EditText mMemberNameEditText;
    private EditText mMemberPhoneEditText;

    public interface MemberAddListener {
        public void addMember(String url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mMemberNameEditText = (EditText) findViewById(R.id.Member_Name);
        mMemberPhoneEditText = (EditText) findViewById(R.id.Member_Phone);

        Button addCourseButton = (Button) findViewById(R.id.add_member_done_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildMemberURL(v);
                AddMemberTask task = new AddMemberTask();
                task.execute(new String[]{url.toString()});

                // Takes you back to the previous fragment by popping the current fragment out.
                //getSupportFragmentManager().popBackStackImmediate()
                Intent intent = new Intent(AddMemberActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

    }

    private class AddMemberTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
                    response = "Unable to add course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
    }

    /**
     * The action of the Done button in the add member screen. Takes
     * the user to the HomeActivity.
     *
     * @param view the view of the activity.
     */
    public void saveMemberInfo(View view) {
//        EditText nameField = (EditText) findViewById(R.id.Member_Name);
//        mMemberName = nameField.getText().toString();
//
//        EditText phoneField = (EditText) findViewById(R.id.Member_Phone);
//        mMemberPhone = phoneField.getText().toString();

        Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtra("member_name", mMemberName);
//        intent.putExtra("member_phone", mMemberPhone);
        startActivity(intent);
    }

    private String buildMemberURL(View v) {

        StringBuilder sb = new StringBuilder(COURSE_ADD_URL);

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
}
