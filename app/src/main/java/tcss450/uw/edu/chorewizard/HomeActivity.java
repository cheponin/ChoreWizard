package tcss450.uw.edu.chorewizard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
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
import tcss450.uw.edu.chorewizard.data.AssignedChoreDB;
import tcss450.uw.edu.chorewizard.model.AssignedChore;
import tcss450.uw.edu.chorewizard.model.Chore;
import tcss450.uw.edu.chorewizard.model.Member;

/**
 * The home screen of the application which contains the list of
 * household members and options to add members and chores.
 */
public class HomeActivity extends AppCompatActivity {

    private List<Member> mMemberList;
    private List<Chore> mChoreList;
    private List<AssignedChore> mAssignedChoreList;
    private AssignedChoreDB mAssignedChoreDB;

    /** The URL to access the Member table of the database. */
    private static final String MEMBER_URL
            = "http://cssgate.insttech.washington.edu/~aclanton/project/projectTest.php?cmd=member";

    /** The URL to access the Course table of the database. */
    private static final String CHORE_URL
            = "http://cssgate.insttech.washington.edu/~aclanton/project/projectChoreTest.php?cmd=chore";
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

        DownloadChoresTask choreTask = new DownloadChoresTask();
        choreTask.execute(new String[]{CHORE_URL});

        if (mAssignedChoreDB == null) {
            mAssignedChoreDB = new AssignedChoreDB(HomeActivity.this);
        }
        if (mAssignedChoreList == null) {
            mAssignedChoreList = mAssignedChoreDB.getAssignedChores();
        }
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

    public void clickAddChore(View view) {
        Intent intent = new Intent(this, AddChoreActivity.class);
        startActivity(intent);
    }

    public void clickSendNotifications(View view) {
        if (mMemberList != null || !mMemberList.isEmpty()) {
            String messageText = "Hey, don't forget to do your chore for the week! :)";
            String phoneNumber = "";

            for (int i = 0; i < mMemberList.size(); i++) {
                phoneNumber = mMemberList.get(i).getPhone();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", messageText);
                startActivity(intent);
            }
        }
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

            if (memberList != null) {
                if (!memberList.isEmpty()) {
                    if (mChoreList != null) {
                        if (!mChoreList.isEmpty()) {
                            if (mAssignedChoreDB == null) {
                                mAssignedChoreDB = new AssignedChoreDB(HomeActivity.this);
                            }

                            // Also, add to the local database
                            int size = Math.min(mChoreList.size(), mMemberList.size());
                            for (int i = 0; i < size; i++) {
                                Chore chore = mChoreList.get(i);
                                Member member = mMemberList.get(i);
                                mAssignedChoreDB.insertAssignedChore(chore.getmChoreName(),
                                        member.getName());
                            }

                        }
                    }
                }
            }
            // Everything is good, show the list of members.
            if (!memberList.isEmpty()) {
                mMemberList = memberList;
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

            // Everything is good, show the list of chores.
            if (!choreList.isEmpty()) {
                mChoreList = choreList;
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

    public static class AssignedChoreDBHelper extends SQLiteOpenHelper {

        private final String CREATE_ASSIGNEDCHORE_SQL;

        private final String DROP_ASSIGNEDCHORE_SQL;

        public AssignedChoreDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_ASSIGNEDCHORE_SQL = context.getString(R.string.CREATE_ASSIGNEDCHORE_SQL);
            DROP_ASSIGNEDCHORE_SQL = context.getString(R.string.DROP_ASSIGNEDCHORE_SQL);
            System.out.println("Did the DB Helper get created?");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_ASSIGNEDCHORE_SQL);
            System.out.println("Does this code run??");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_ASSIGNEDCHORE_SQL);
            onCreate(sqLiteDatabase);
        }
    }


}
