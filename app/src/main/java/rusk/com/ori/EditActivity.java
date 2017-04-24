package rusk.com.ori;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "";
    TextView textViewCalendar;
    TextView textViewGenderOption;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final EditText editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextMobileNo = (EditText) findViewById(R.id.editTextMobileNo);

        final TextView textViewGenderOption = (TextView) findViewById(R.id.textViewGenderOption);
        final TextView textViewCalendar = (TextView) findViewById(R.id.textViewCalendar);

        Button buttonSave = (Button) findViewById(R.id.btnSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                if (editTextFirstName.getText().toString().trim().matches("")) {
                    Toast.makeText(EditActivity.this, "First Name cannot be empty", Toast.LENGTH_LONG).show();
                } else if (editTextLastName.getText().toString().trim().matches("")) {
                    Toast.makeText(EditActivity.this, "Last Name cannot be empty", Toast.LENGTH_LONG).show();
                } else if (editTextEmail.getText().toString().trim().matches("")) {
                    Toast.makeText(EditActivity.this, "Email cannot be empty", Toast.LENGTH_LONG).show();
                } else if (editTextMobileNo.getText().toString().length() != 10) {
                    Toast.makeText(EditActivity.this, "Mobile No. has to be of 10 digits", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor extras = settings.edit();

                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
//                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_FIRSTNAME", editTextFirstName.getText().toString().trim());
                    extras.putString("EXTRA_LASTNAME", editTextLastName.getText().toString().trim());
                    extras.putString("EXTRA_EMAIL", editTextEmail.getText().toString().trim());
                    extras.putString("EXTRA_MOBILE", editTextMobileNo.getText().toString().trim());

                    if (textViewGenderOption.getText().toString().contains("Optional")) {
                        extras.putString("EXTRA_GENDER", "-");
                    } else {
                        extras.putString("EXTRA_GENDER", textViewGenderOption.getText().toString());
                    }
                    if (textViewCalendar.getText().toString().contains("Optional")) {
                        extras.putString("EXTRA_DOB", "-");
                    } else {
                        extras.putString("EXTRA_DOB", textViewCalendar.getText().toString());
                    }

//                    intent.putExtras(extras);
                    extras.commit();
                    startActivity(intent);
                }
            }
        });


    }


    public void onClick(View v) {
        if (v.getId() == R.id.textViewCalendar) {
            textViewCalendar = (TextView) findViewById(R.id.textViewCalendar);
            Log.d(TAG, "onClick: clicked ************************** ");
//            Toast.makeText(EditActivity.this, "You clicked cal button", Toast.LENGTH_LONG).show();
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                                 @Override
                                                                                 public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int date) {
                                                                                     textViewCalendar.setText(date + "/" + (month + 1) + "/" + year);

                                                                                     // Do whatever you want when the date is selected.
                                                                                 }
                                                                             }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setYearRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR)); // You can add your value for YEARS_IN_THE_FUTURE.
            datePickerDialog.show(getFragmentManager(), "Birth Date");
        }

        if (v.getId() == R.id.textViewGenderOption) {

            showRadioButtonDialog();

        }
    }

    private void showRadioButtonDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(EditActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        List<String> stringList = new ArrayList<>();  // here is list
        stringList.add("Male");
        stringList.add("Female");

        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        for (int i = 0; i < stringList.size(); i++) {
            RadioButton rb = new RadioButton(EditActivity.this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rb.setPadding(50, 15, 50, 15);
            rg.addView(rb);
        }
        dialog.show();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                textViewGenderOption = (TextView) findViewById(R.id.textViewGenderOption);
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        Log.e("selected RadioButton->", btn.getText().toString());
                        dialog.dismiss();
                        textViewGenderOption.setText(btn.getText().toString());

                    }
                }
            }
        });


    }


}