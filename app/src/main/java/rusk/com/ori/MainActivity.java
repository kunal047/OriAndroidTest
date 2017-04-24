package rusk.com.ori;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static rusk.com.ori.EditActivity.PREFS_NAME;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView textViewEmail = (TextView) findViewById(R.id.txtViewEmail);
        TextView textViewGender = (TextView) findViewById(R.id.txtViewGender);
        TextView textViewDOB = (TextView) findViewById(R.id.txtViewDOB);
        TextView textViewMobile = (TextView) findViewById(R.id.txtViewMobile);


        SharedPreferences extras = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

//        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String firstname = extras.getString("EXTRA_FIRSTNAME", "Kunal");
            String lastname = extras.getString("EXTRA_LASTNAME", "Chauhan");
            String email = extras.getString("EXTRA_EMAIL", "kunal04@live.com");
            String gender = extras.getString("EXTRA_GENDER", "-");
            String dob = extras.getString("EXTRA_DOB", "-");
            String mob = extras.getString("EXTRA_MOBILE", "9662152651");

            userName.setText(firstname + " " + lastname);
            textViewEmail.setText(email);
            textViewGender.setText(gender);
            textViewDOB.setText(dob);
            textViewMobile.setText(mob);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_edit:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }
}
