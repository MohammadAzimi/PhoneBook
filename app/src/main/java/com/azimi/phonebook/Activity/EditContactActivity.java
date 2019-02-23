package com.azimi.phonebook.Activity;

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
import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditContactActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private String contactID;
    private ArrayList<Phone> phones = new ArrayList<>();
    private ArrayList<Email> emails = new ArrayList<>();
    private List<View> phoneViewList = new ArrayList<>();
    private List<View> emailViewList = new ArrayList<>();
    private LinearLayout linearPhone;
    private LinearLayout linearEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_contact);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        contactID = getIntent().getStringExtra(Utility.EXTRA_CONTACT_ID);
        String firstName = getIntent().getStringExtra(Utility.EXTRA_FIRST_NAME);
        String lastName = getIntent().getStringExtra(Utility.EXTRA_LAST_NAME);
        String note = getIntent().getStringExtra(Utility.EXTRA_NOTE);
        phones = getIntent().getParcelableArrayListExtra(Utility.EXTRA_PHONE_NUMBER);
        emails = getIntent().getParcelableArrayListExtra(Utility.EXTRA_EMAIL);

        //Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = findViewById(R.id.toolbar_text_title);
        toolbarTitle.setText(R.string.toolbar_title_edit_contact);

        final EditText txtFirstName = findViewById(R.id.txt_first_name);
        final EditText txtLastName = findViewById(R.id.txt_last_name);
        final EditText txtNote = findViewById(R.id.txt_note);
        linearPhone = findViewById(R.id.linear_phones);
        linearEmail = findViewById(R.id.linear_emails);

        //check whether there is any phone to view or add at least one view
        if (phones.isEmpty()) {
            View phoneView = inflateView(Utility.INFLATE_PHONE);
            EditText txtPhone = phoneView.findViewById(R.id.txt_phone_number);
            Phone phone = new Phone("", contactID, Utility.MOBILE, "");
            txtPhone.setText(phone.getNumber());
            phones.add(phone);
            phoneViewList.add(phoneView);
            linearPhone.addView(phoneView);
        } else {
            for (Phone phone : phones) {
                View phoneView = inflateView(Utility.INFLATE_PHONE);
                EditText txtPhone = phoneView.findViewById(R.id.txt_phone_number);
                txtPhone.setText(phone.getNumber());
                phoneViewList.add(phoneView);
                linearPhone.addView(phoneView);
            }
        }

        //check whether there is any email to view or add at least one view
        if (emails.isEmpty()) {
            View emailView = inflateView(Utility.INFLATE_EMAIL);
            EditText txtEmail = emailView.findViewById(R.id.txt_email);
            Email email = new Email("", contactID, "");
            txtEmail.setText(email.getEmail());
            emails.add(email);
            emailViewList.add(emailView);
            linearEmail.addView(emailView);
        } else {
            for (Email email : emails) {
                View emailView = inflateView(Utility.INFLATE_EMAIL);
                EditText txtEmail = emailView.findViewById(R.id.txt_email);
                txtEmail.setText(email.getEmail());
                emailViewList.add(emailView);
                linearEmail.addView(emailView);
            }
        }

        TextView btnAddPhone = findViewById(R.id.btn_add_phone);
        btnAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View phoneView = inflateView(Utility.INFLATE_PHONE);
                phoneViewList.add(phoneView);
                linearPhone.addView(phoneView);
            }
        });

        TextView btnAddEmail = findViewById(R.id.btn_add_email);
        btnAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View emailView = inflateView(Utility.INFLATE_EMAIL);
                emailViewList.add(emailView);
                linearEmail.addView(emailView);
            }
        });

        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtNote.setText(note);

        ImageView btnOk = findViewById(R.id.toolbar_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields(txtFirstName.getText().toString(), txtLastName.getText().toString())) {

                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(Utility.EXTRA_CONTACT_ID, contactID);
                    replyIntent.putExtra(Utility.EXTRA_FIRST_NAME, txtFirstName.getText().toString().trim());
                    replyIntent.putExtra(Utility.EXTRA_LAST_NAME, txtLastName.getText().toString().trim());
                    replyIntent.putExtra(Utility.EXTRA_NOTE, txtNote.getText().toString().trim());

                    for (int i = 0; i < phoneViewList.size(); i++) {
                        // i = phoneViewListIndex ; So if i+1 > phone.size then it is a new inserted phone number
                        if ((i + 1) > phones.size()) {
                            View phoneView = phoneViewList.get(i);
                            EditText txtPhone = phoneView.findViewById(R.id.txt_phone_number);
                            //TextView txtPhoneType = phoneView.findViewById(R.id.txt_phone_type);
                            if (!txtPhone.getText().toString().equals("")) {
                                Phone phone = new Phone("",
                                        contactID,
                                        Utility.MOBILE,
                                        txtPhone.getText().toString().trim());
                                phones.add(phone);
                            }
                        } else {
                            View phoneView = phoneViewList.get(i);
                            EditText txtPhone = phoneView.findViewById(R.id.txt_phone_number);
                            phones.get(i).setNumber(txtPhone.getText().toString().trim());
                        }
                    }
                    replyIntent.putExtra(Utility.EXTRA_PHONE_NUMBER, phones);

                    for (int i = 0; i < emailViewList.size(); i++) {
                        if ((i + 1) > emails.size()) {
                            View emailView = emailViewList.get(i);
                            EditText txtEmail = emailView.findViewById(R.id.txt_email);
                            if (!txtEmail.getText().toString().equals("")) {
                                Email email = new Email("",
                                        contactID,
                                        txtEmail.getText().toString().trim());
                                emails.add(email);
                            }
                        } else {
                            View emailView = emailViewList.get(i);
                            EditText txtEmail = emailView.findViewById(R.id.txt_email);
                            emails.get(i).setEmail(txtEmail.getText().toString().trim());
                        }
                    }
                    replyIntent.putExtra(Utility.EXTRA_EMAIL, emails);

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

    private View inflateView(int type) {
        if (type == Utility.INFLATE_PHONE) {
            return getLayoutInflater().inflate(R.layout.layout_phone_input, null);
        } else {
            return getLayoutInflater().inflate(R.layout.layout_email_input, null);
        }
    }
}
