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

import com.azimi.phonebook.ContactViewModel;
import com.azimi.phonebook.ContactWithDetailViewModel;
import com.azimi.phonebook.R;
import com.azimi.phonebook.Utility;
import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.ContactWithDetail;

import java.util.List;
import java.util.Objects;

public class ShowContactActivity extends AppCompatActivity {
    private ContactWithDetailViewModel contactVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

        String contactId = getIntent().getStringExtra(Utility.EXTRA_ID);
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

        final TextView txtFirstName = findViewById(R.id.txt_first_name);
        final TextView txtLastName = findViewById(R.id.txt_last_name);
        final TextView txtPhoneNumber = findViewById(R.id.txt_phone_number);
        final TextView txtEmail = findViewById(R.id.txt_email);
        final TextView txtNote = findViewById(R.id.txt_note);

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
                    txtFirstName.setText(contact.get(0).firstName);
                    txtLastName.setText(contact.get(0).lastName);
                    txtPhoneNumber.setText(contact.get(0).phoneNumber);
                    txtEmail.setText(contact.get(0).email);
                    txtNote.setText(contact.get(0).note);
                }
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
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
                // TODO: 2019-02-11 edit contact
                return true;
            case R.id.delete_contact:
                // TODO: 2019-02-11 delete contact
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
