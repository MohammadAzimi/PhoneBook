package com.azimi.phonebook.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azimi.phonebook.R;
import com.azimi.phonebook.Utility;
import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;

import java.util.Objects;

public class EditContactActivity extends AppCompatActivity {

    private String contactID;
    private String phoneID;
    private String emailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_contact);

        contactID = getIntent().getStringExtra(Utility.EXTRA_CONTACT_ID);
        phoneID = getIntent().getStringExtra(Utility.EXTRA_PHONE_ID);
        emailID = getIntent().getStringExtra(Utility.EXTRA_EMAIL_ID);

        String firstName = getIntent().getStringExtra(Utility.EXTRA_FIRST_NAME);
        String lastName = getIntent().getStringExtra(Utility.EXTRA_LAST_NAME);
        String phoneNumber = getIntent().getStringExtra(Utility.EXTRA_PHONE_NUMBER);
        String email = getIntent().getStringExtra(Utility.EXTRA_EMAIL);
        String note = getIntent().getStringExtra(Utility.EXTRA_NOTE);

        //Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = findViewById(R.id.toolbar_text_title);
        toolbarTitle.setText(R.string.toolbar_title_edit_contact);

        final EditText txtFirstName = findViewById(R.id.txt_first_name);
        final EditText txtLastName = findViewById(R.id.txt_last_name);
        final EditText txtPhoneNumber = findViewById(R.id.txt_phone_number);
        final EditText txtEmail = findViewById(R.id.txt_email);
        final EditText txtNote = findViewById(R.id.txt_note);

        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtPhoneNumber.setText(phoneNumber);
        txtEmail.setText(email);
        txtNote.setText(note);

        ImageView btnOk = findViewById(R.id.toolbar_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields(txtFirstName.getText().toString(), txtLastName.getText().toString())) {

                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(Utility.EXTRA_CONTACT_ID, contactID);

//                if (!txtPhoneNumber.getText().toString().equals("") && phoneID.equals("")) {
//                    replyIntent.putExtra(Utility.EXTRA_PHONE_ID, contactID + txtPhoneNumber.getText().toString());
//                } else if (!phoneID.equals("") || (phoneID.equals("") && txtPhoneNumber.getText().toString().equals(""))) {
//                    replyIntent.putExtra(Utility.EXTRA_PHONE_ID, phoneID);
//                }
                    replyIntent.putExtra(Utility.EXTRA_PHONE_ID, phoneID);
//                if (!txtEmail.getText().toString().equals("") && emailID.equals(""))
//                    replyIntent.putExtra(Utility.EXTRA_EMAIL_ID, contactID + txtEmail.getText().toString());
//                else if (!emailID.equals("") || (emailID.equals("") && txtEmail.getText().toString().equals(""))) {
//                    replyIntent.putExtra(Utility.EXTRA_EMAIL_ID, emailID);
//                }
                    replyIntent.putExtra(Utility.EXTRA_EMAIL_ID, emailID);
                    replyIntent.putExtra(Utility.EXTRA_FIRST_NAME, txtFirstName.getText().toString().trim());
                    replyIntent.putExtra(Utility.EXTRA_LAST_NAME, txtLastName.getText().toString().trim());
                    replyIntent.putExtra(Utility.EXTRA_PHONE_NUMBER, txtPhoneNumber.getText().toString().trim());
                    replyIntent.putExtra(Utility.EXTRA_EMAIL, txtEmail.getText().toString().trim());
                    replyIntent.putExtra(Utility.EXTRA_NOTE, txtNote.getText().toString().trim());
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        ImageView btnCancel = findViewById(R.id.toolbar_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private boolean checkRequiredFields(String firstName, String lastName) {
        if (firstName.trim().equals("") && lastName.trim().equals("")) {
            Toast.makeText(this,
                    this.getResources().getString(R.string.name_is_blank),
                    Toast.LENGTH_LONG)
                    .show();
            return false;
            // TODO: 2019-02-09 check other fields validity
        } else {
            return true;
        }
    }
}
