package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nameVal = extras.getString("member_name");
            String phoneVal = extras.getString("member_phone");

            TextView member = (TextView) findViewById(R.id.member_1);
            member.setText(nameVal + " - " + phoneVal);

        }
    }

    // Takes user to AddMemberActivity
    public void clickAddMember(View view) {
        Intent intent = new Intent(this, AddMemberActivity.class);
        startActivity(intent);
    }

}
