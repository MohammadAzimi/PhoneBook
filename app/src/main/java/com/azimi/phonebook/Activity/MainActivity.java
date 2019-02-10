package com.azimi.phonebook.Activity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.azimi.phonebook.Adapter.RecyclerViewContacts;
import com.azimi.phonebook.ContactViewModel;
import com.azimi.phonebook.FakeData;
import com.azimi.phonebook.R;
import com.azimi.phonebook.Utility;
import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ContactViewModel contactViewModel;
    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(MainActivity.this, EnterContactActivity.class),
                        NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        final RecyclerViewContacts adapter = new RecyclerViewContacts(this,
                new RecyclerViewContacts.EventHandler() {
                    @Override
                    public void onClick(Contact contact, int position) {
                        Intent intent = new Intent(MainActivity.this, ShowContactActivity.class);
                        intent.putExtra(Utility.EXTRA_ID, contact.getId());
                        startActivity(intent);
                    }
                });
        recyclerView.setAdapter(adapter);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                adapter.setContactList(contacts);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Contact contact = new Contact(data.getStringExtra(Utility.EXTRA_FIRST_NAME),
                    data.getStringExtra(Utility.EXTRA_LAST_NAME),
                    data.getStringExtra(Utility.EXTRA_NOTE));
            //contactViewModel.insert(contact);

            Phone phone = new Phone(contact.getId(),
                    Utility.MOBILE,
                    data.getStringExtra(Utility.EXTRA_PHONE_NUMBER));
            Email email = new Email(contact.getId(),
                    data.getStringExtra(Utility.EXTRA_EMAIL));
            contactViewModel.insert(contact, phone, email);

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

    private void showDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("ADD CONTACT")
                .setCancelable(true)
                .setIcon(R.drawable.ic_launcher_foreground);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_dialog_input, null);
        builder1.setView(dialogView);

        final EditText name = dialogView.findViewById(R.id.contact_name);
        final EditText phone = dialogView.findViewById(R.id.contact_phone);

        builder1.setPositiveButton(
                "ADD",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addContactByHashMap(name.getText().toString(), phone.getText().toString());
                        //addContact(name.getText().toString(), phone.getText().toString());
                    }
                });

        builder1.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        builder1.show();
    }

    private void addContact(String name, String phone) {

//        Contact contact = new Contact(name, phone);
//        FakeData.contacts.add(contact);
//        Toast.makeText(MainActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
    }

    private void addContactByHashMap(String name, String phone) {

        HashMap<String, String> contact = new HashMap<>();
        contact.put("name", name);
        contact.put("phone", phone);
        FakeData.hashContacts.add(contact);
        Toast.makeText(MainActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
    }
}
