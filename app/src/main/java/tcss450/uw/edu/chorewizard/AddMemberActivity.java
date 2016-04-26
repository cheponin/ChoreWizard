package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddMemberActivity extends AppCompatActivity {

    private String mMemberName;
    private String mMemberPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);


    }

    public void saveMemberInfo(View view) {
        EditText nameField = (EditText) findViewById(R.id.Member_Name);
        mMemberName = nameField.getText().toString();

        EditText phoneField = (EditText) findViewById(R.id.Member_Phone);
        mMemberPhone = phoneField.getText().toString();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


}
