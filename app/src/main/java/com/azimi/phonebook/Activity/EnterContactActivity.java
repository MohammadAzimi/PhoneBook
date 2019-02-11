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

import java.util.Objects;

public class EnterContactActivity extends AppCompatActivity {

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtPhoneNumber;
    private EditText txtEmail;
    private EditText txtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_contact);

        //Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = findViewById(R.id.toolbar_text_title);
        toolbarTitle.setText(R.string.toolbar_title_add_contact);

        txtFirstName = findViewById(R.id.txt_first_name);
        txtLastName = findViewById(R.id.txt_last_name);
        txtPhoneNumber = findViewById(R.id.txt_phone_number);
        txtEmail = findViewById(R.id.txt_email);
        txtNote = findViewById(R.id.txt_note);

        ImageView buttonOk = findViewById(R.id.toolbar_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    String firstName = txtFirstName.getText().toString();
                    String lastName = txtLastName.getText().toString();
                    String phoneNumber = txtPhoneNumber.getText().toString();
                    String email = txtEmail.getText().toString();
                    String note = txtNote.getText().toString();

                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(Utility.EXTRA_FIRST_NAME, firstName);
                    replyIntent.putExtra(Utility.EXTRA_LAST_NAME, lastName);
                    replyIntent.putExtra(Utility.EXTRA_PHONE_NUMBER, phoneNumber);
                    replyIntent.putExtra(Utility.EXTRA_EMAIL, email);
                    replyIntent.putExtra(Utility.EXTRA_NOTE, note);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        ImageView buttonCancel = findViewById(R.id.toolbar_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });
    }

    private boolean checkRequiredFields() {
        if (txtFirstName.getText().toString().trim().equals("") &&
                txtLastName.getText().toString().trim().equals("")) {
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
