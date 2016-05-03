package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

/**
 * The add member screen of the application which provides a form
 * for the user to fill out to add a new household member.
 */
public class AddMemberActivity extends AppCompatActivity {

    /** The member name. */
    private String mMemberName;

    /** The member phone number. */
    private String mMemberPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
    }

    /**
     * The action of the Done button in the add member screen. Takes
     * the user to the HomeActivity.
     *
     * @param view the view of the activity.
     */
    public void saveMemberInfo(View view) {
        EditText nameField = (EditText) findViewById(R.id.Member_Name);
        mMemberName = nameField.getText().toString();

        EditText phoneField = (EditText) findViewById(R.id.Member_Phone);
        mMemberPhone = phoneField.getText().toString();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("member_name", mMemberName);
        intent.putExtra("member_phone", mMemberPhone);
        startActivity(intent);
    }

}
