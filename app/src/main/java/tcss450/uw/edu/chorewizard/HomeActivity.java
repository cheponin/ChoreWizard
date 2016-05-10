package tcss450.uw.edu.chorewizard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import tcss450.uw.edu.chorewizard.authenticate.SignInActivity;
import tcss450.uw.edu.chorewizard.model.Member;

/**
 * The home screen of the application which contains the list of
 * household members and options to add members and chores.
 */
public class HomeActivity extends AppCompatActivity {

    /** The URL to access the Member table of the database. */
    private static final String MEMBER_URL
            = "http://cssgate.insttech.washington.edu/~aclanton/project/projectTest.php?cmd=member";

    /**
     * An empty class constructor.
     */
    public HomeActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DownloadMembersTask task = new DownloadMembersTask();
        task.execute(new String[]{MEMBER_URL});

    }

    /**
     * The action of the Add Member button in the home screen. Takes
     * the user to the AddMemberActivity.
     *
     * @param view the view of the activity.
     */
    public void clickAddMember(View view) {
        Intent intent = new Intent(this, AddMemberActivity.class);
        startActivity(intent);
    }

    public void clickManageChores(View view) {
        Intent intent = new Intent(this, ManageChoresActivity.class);
        startActivity(intent);
    }

    /**
     * The action of the Log Out button in the home screen. Takes
     * the user to the SignInActivity.
     *
     * @param view the view of the activity.
     */
    public void logOut(View view) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                .commit();

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    /**
     * A class that is used to download Members and their information from the
     * web service into the application to be displayed on the home screen.
     */
    private class DownloadMembersTask extends AsyncTask<String, Void, String> {

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
                    response = "Unable to download the list of courses, Reason: "
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

            List<Member> memberList = new ArrayList<Member>();
            result = Member.parseMemberJSON(result, memberList);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of members.
            if (!memberList.isEmpty()) {
                for(int i = 0; i < memberList.size(); i++) {
                    Member memberObject = memberList.get(i);
                    switch (i) {
                        case 0:
                            TextView member = (TextView) findViewById(R.id.member_1);
                            member.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 1:
                            TextView member2 = (TextView) findViewById(R.id.member_2);
                            member2.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 2:
                            TextView member3 = (TextView) findViewById(R.id.member_3);
                            member3.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 3:
                            TextView member4 = (TextView) findViewById(R.id.member_4);
                            member4.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 4:
                            TextView member5 = (TextView) findViewById(R.id.member_5);
                            member5.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 5:
                            TextView member6 = (TextView) findViewById(R.id.member_6);
                            member6.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 6:
                            TextView member7 = (TextView) findViewById(R.id.member_7);
                            member7.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 7:
                            TextView member8 = (TextView) findViewById(R.id.member_8);
                            member8.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 8:
                            TextView member9 = (TextView) findViewById(R.id.member_9);
                            member9.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                        case 9:
                            TextView member10 = (TextView) findViewById(R.id.member_10);
                            member10.setText(memberObject.getName() + " - " + memberObject.getPhone());
                            break;
                    }
                }
            }
        }

    }

}
