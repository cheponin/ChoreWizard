package tcss450.uw.edu.chorewizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddChoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);
    }

    public void saveChoreInfo(View view) {
        Intent intent = new Intent(this, ManageChoresActivity.class);
        startActivity(intent);
    }
}
