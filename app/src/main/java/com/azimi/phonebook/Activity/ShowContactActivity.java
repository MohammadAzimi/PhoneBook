package com.azimi.phonebook.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azimi.phonebook.ContactWithDetailViewModel;
import com.azimi.phonebook.R;
import com.azimi.phonebook.Utility;
import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.ContactWithDetail;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;

import java.util.List;
import java.util.Objects;

public class ShowContactActivity extends AppCompatActivity {
    private static final int EDIT_CONTACT_ACTIVITY_REQUEST_CODE = 2;
    private ContactWithDetailViewModel contactVM;
    private Contact cachContact;
    private String contactId;
    private String phoneId;
    private String emailId;

    TextView txtFirstName;
    TextView txtLastName;
    TextView txtPhoneNumber;
    TextView txtEmail;
    TextView txtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        if (contactId == null)
            contactId = getIntent().getStringExtra(Utility.EXTRA_CONTACT_ID);
//        String lastName = getIntent().getStringExtra(Utility.EXTRA_LAST_NAME);
//        String phoneNumber = getIntent().getStringExtra(Utility.EXTRA_PHONE_NUMBER);
//        String email = getIntent().getStringExtra(Utility.EXTRA_EMAIL);
//        String note = getIntent().getStringExtra(Utility.EXTRA_NOTE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageView btnBack = findViewById(R.id.toolbar_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtFirstName = findViewById(R.id.txt_first_name);
        txtLastName = findViewById(R.id.txt_last_name);
        txtPhoneNumber = findViewById(R.id.txt_phone_number);
        txtEmail = findViewById(R.id.txt_email);
        txtNote = findViewById(R.id.txt_note);

//        txtFirstName.setText(firstName);
//        txtLastName.setText(lastName);
//        txtPhoneNumber.setText(phoneNumber);
//        txtEmail.setText(email);
//        txtNote.setText(note);

        contactVM = ViewModelProviders.of(this).get(ContactWithDetailViewModel.class);
        contactVM.getContact(contactId).observe(this, new Observer<List<ContactWithDetail>>() {
            @Override
            public void onChanged(@Nullable List<ContactWithDetail> contact) {
                if (contact == null || contact.isEmpty()) {
                    finish();
                } else {
                    if (contact.get(0).phoneId != null)
                        phoneId = contact.get(0).phoneId;
                    else phoneId = "";
                    if (contact.get(0).emailId != null)
                        emailId = contact.get(0).emailId;
                    else emailId = "";
                    txtFirstName.setText(contact.get(0).firstName);
                    txtLastName.setText(contact.get(0).lastName);
                    txtPhoneNumber.setText(contact.get(0).phoneNumber);
                    txtEmail.setText(contact.get(0).email);
                    txtNote.setText(contact.get(0).note);
                    cachContact = new Contact(contactId,
                            contact.get(0).firstName,
                            contact.get(0).lastName,
                            contact.get(0).note);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_contact_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_contact:
                // TODO: 2019-02-10 share contact
                return true;
            case R.id.edit_contact:
                Intent intent = new Intent(ShowContactActivity.this, EditContactActivity.class);
                intent.putExtra(Utility.EXTRA_CONTACT_ID, contactId);

                //if (!phoneId.equals(""))
                intent.putExtra(Utility.EXTRA_PHONE_ID, phoneId);
                //else intent.putExtra(Utility.EXTRA_PHONE_ID, "");

                //if (!emailId.equals(""))
                intent.putExtra(Utility.EXTRA_EMAIL_ID, emailId);
                //else intent.putExtra(Utility.EXTRA_EMAIL_ID, "");

                intent.putExtra(Utility.EXTRA_FIRST_NAME, txtFirstName.getText().toString());
                intent.putExtra(Utility.EXTRA_LAST_NAME, txtLastName.getText().toString());
                intent.putExtra(Utility.EXTRA_PHONE_NUMBER, txtPhoneNumber.getText().toString());
                intent.putExtra(Utility.EXTRA_EMAIL, txtEmail.getText().toString());
                intent.putExtra(Utility.EXTRA_NOTE, txtNote.getText().toString());
                startActivityForResult(intent, EDIT_CONTACT_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.delete_contact:
                contactVM.deleteContact(cachContact);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Contact contact = new Contact(data.getStringExtra(Utility.EXTRA_CONTACT_ID),
                    data.getStringExtra(Utility.EXTRA_FIRST_NAME),
                    data.getStringExtra(Utility.EXTRA_LAST_NAME),
                    data.getStringExtra(Utility.EXTRA_NOTE));
            //contactViewModel.insert(contact);

            Phone phone = new Phone(data.getStringExtra(Utility.EXTRA_PHONE_ID),
                    contact.getId(),
                    Utility.MOBILE,
                    data.getStringExtra(Utility.EXTRA_PHONE_NUMBER));
            Email email = new Email(data.getStringExtra(Utility.EXTRA_EMAIL_ID),
                    contact.getId(),
                    data.getStringExtra(Utility.EXTRA_EMAIL));
            contactVM.update(contact, phone, email);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.toast_contact_added_successfully,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.toast_contact_added_cancelled,
                    Toast.LENGTH_LONG).show();
        }
    }
}
