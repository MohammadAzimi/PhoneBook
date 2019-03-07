package com.azimi.phonebook.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azimi.phonebook.Adapter.RecyclerViewPhoneEmail;
import com.azimi.phonebook.ContactWithDetailViewModel;
import com.azimi.phonebook.EnsureDeleteDialogFragment;
import com.azimi.phonebook.R;
import com.azimi.phonebook.Utility;
import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.ContactWithDetail;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowContactActivity extends AppCompatActivity implements EnsureDeleteDialogFragment.OnDelete{
    private static final int EDIT_CONTACT_ACTIVITY_REQUEST_CODE = 2;
    private ContactWithDetailViewModel contactVM;
    private Contact cashedContact;
    private String contactId;

    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtNote;

    private ArrayList<Phone> phoneArrayList = new ArrayList<>();
    private ArrayList<Email> emailArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        if (contactId == null)
            contactId = getIntent().getStringExtra(Utility.EXTRA_CONTACT_ID);

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
        txtNote = findViewById(R.id.txt_note);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        final RecyclerViewPhoneEmail adaptor = new RecyclerViewPhoneEmail(this);
        recyclerView.setAdapter(adaptor);

        contactVM = ViewModelProviders.of(this).get(ContactWithDetailViewModel.class);
        contactVM.getContactWithDetails(contactId).observe(this, new Observer<List<ContactWithDetail>>() {
            @Override
            public void onChanged(@Nullable List<ContactWithDetail> contactWithDetails) {
                if (contactWithDetails == null || contactWithDetails.isEmpty()) {
                    finish();
                } else {
                    phoneArrayList.clear();
                    emailArrayList.clear();
                    for (ContactWithDetail mContact : contactWithDetails) {
                        if (mContact.phoneId != null && mContact.phoneNumber != null) {

                            Phone phone = new Phone(mContact.phoneId, mContact.contactId, mContact.phoneType, mContact.phoneNumber);
                            boolean exist = false;
                            for (int i = 0; i < phoneArrayList.size(); i++) {
                                if (phone.getId().equals(phoneArrayList.get(i).getId())) {
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist)
                                phoneArrayList.add(phone);
                        }
                        if (mContact.emailId != null && mContact.email != null) {

                            Email email = new Email(mContact.emailId, mContact.contactId, mContact.email);
                            boolean exist = false;
                            for (int i = 0; i < emailArrayList.size(); i++) {
                                if (email.getId().equals(emailArrayList.get(i).getId())) {
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist)
                                emailArrayList.add(email);
                        }
                    }
                    txtFirstName.setText(contactWithDetails.get(0).firstName);
                    txtLastName.setText(contactWithDetails.get(0).lastName);
                    txtNote.setText(contactWithDetails.get(0).note);
                    adaptor.setData(phoneArrayList, emailArrayList);

                    cashedContact = new Contact(contactId,
                            contactWithDetails.get(0).firstName,
                            contactWithDetails.get(0).lastName,
                            contactWithDetails.get(0).note);
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
                intent.putExtra(Utility.EXTRA_FIRST_NAME, txtFirstName.getText().toString());
                intent.putExtra(Utility.EXTRA_LAST_NAME, txtLastName.getText().toString());
                intent.putExtra(Utility.EXTRA_NOTE, txtNote.getText().toString());
                intent.putExtra(Utility.EXTRA_PHONE_NUMBER, phoneArrayList);
                intent.putExtra(Utility.EXTRA_EMAIL, emailArrayList);
                startActivityForResult(intent, EDIT_CONTACT_ACTIVITY_REQUEST_CODE);
                return true;

            case R.id.delete_contact:
                deleteContact();
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

            phoneArrayList = data.getParcelableArrayListExtra(Utility.EXTRA_PHONE_NUMBER);
            emailArrayList = data.getParcelableArrayListExtra(Utility.EXTRA_EMAIL);
            contactVM.update(contact, phoneArrayList, emailArrayList);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.toast_contact_edited_successfully,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.toast_contact_edited_cancelled,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void deleteContact(){
        EnsureDeleteDialogFragment bottomSheetFragment = EnsureDeleteDialogFragment.newInstance();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void delete() {
        contactVM.deleteContact(cashedContact);
    }
}
