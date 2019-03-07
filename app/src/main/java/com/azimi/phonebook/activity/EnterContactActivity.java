package com.azimi.phonebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azimi.phonebook.R;
import com.azimi.phonebook.Utility;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnterContactActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    private List<View> linearPhonesList = new ArrayList<>();
    private List<View> linearEmailsList = new ArrayList<>();

    private LinearLayout linearPhones;
    private LinearLayout linearEmails;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_contact);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = findViewById(R.id.toolbar_text_title);
        toolbarTitle.setText(R.string.toolbar_title_add_contact);

        linearPhones = findViewById(R.id.linear_phones);
        linearEmails = findViewById(R.id.linear_emails);
        txtFirstName = findViewById(R.id.txt_first_name);
        txtLastName = findViewById(R.id.txt_last_name);
        txtNote = findViewById(R.id.txt_note);

        //EditText txtPhoneNumber = findViewById(R.id.txt_phone_number);
        //EditText txtEmail = findViewById(R.id.txt_email);
        TextView btnAddPhone = findViewById(R.id.btn_add_phone);
        TextView btnAddEmail = findViewById(R.id.btn_add_email);

        View phoneView = inflateView(Utility.INFLATE_PHONE);
        linearPhones.addView(phoneView);
        linearPhonesList.add(phoneView);

        View emailView = inflateView(Utility.INFLATE_EMAIL);
        linearEmails.addView(emailView);
        linearEmailsList.add(emailView);

        btnAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View linearPhone = inflateView(Utility.INFLATE_PHONE);
                linearPhonesList.add(linearPhone);
                linearPhones.addView(linearPhone);
            }
        });

        btnAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View linearEmail = inflateView(Utility.INFLATE_EMAIL);
                linearEmailsList.add(linearEmail);
                linearEmails.addView(linearEmail);
            }
        });

        ImageView buttonOk = findViewById(R.id.toolbar_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    String firstName = txtFirstName.getText().toString().trim();
                    String lastName = txtLastName.getText().toString().trim();
                    String note = txtNote.getText().toString();

                    ArrayList<Phone> phones = new ArrayList<>();
                    for (View layoutPhone : linearPhonesList) {
                        EditText txtPhone = layoutPhone.findViewById(R.id.txt_phone_number);
                        if (!txtPhone.getText().toString().equals("")) {
                            Phone phone = new Phone(firstName + lastName + txtPhone.getText().toString().trim(),
                                    firstName + lastName,
                                    Utility.MOBILE, // TODO: 2019-02-23 add real data
                                    txtPhone.getText().toString().trim());
                            phones.add(phone);
                        }
                    }
                    ArrayList<Email> emails = new ArrayList<>();
                    for (View layoutEmail : linearEmailsList) {
                        EditText txtEmail = layoutEmail.findViewById(R.id.txt_email);
                        if (!txtEmail.getText().toString().equals("")) {
                            Email email = new Email(firstName + lastName + txtEmail.getText().toString().trim(),
                                    firstName + lastName,
                                    txtEmail.getText().toString().trim());
                            emails.add(email);
                        }
                    }

                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(Utility.EXTRA_FIRST_NAME, firstName);
                    replyIntent.putExtra(Utility.EXTRA_LAST_NAME, lastName);
                    replyIntent.putExtra(Utility.EXTRA_PHONE_NUMBER, phones);
                    replyIntent.putExtra(Utility.EXTRA_EMAIL, emails);
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

        Button btnAddAnotherField = findViewById(R.id.btn_add_field);
        btnAddAnotherField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019-02-19 add dialog to do actions
                Bundle bundle = new Bundle();
                bundle.putString(Utility.FIREBASE_EXT_ADD_FIELD, "true");
                mFirebaseAnalytics.logEvent(Utility.FIREBASE_EXT_ADD_FIELD, bundle);
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

    private View inflateView(int type) {
        if (type == Utility.INFLATE_PHONE) {
            return getLayoutInflater().inflate(R.layout.layout_phone_input, null);
        } else {
            return getLayoutInflater().inflate(R.layout.layout_email_input, null);
        }
    }
}
