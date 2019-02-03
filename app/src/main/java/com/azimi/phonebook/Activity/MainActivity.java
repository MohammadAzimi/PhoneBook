package com.azimi.phonebook.Activity;


import android.content.DialogInterface;
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
import com.azimi.phonebook.Contact;
import com.azimi.phonebook.FakeData;
import com.azimi.phonebook.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        RecyclerViewContacts adapter = new RecyclerViewContacts(this);
        recyclerView.setAdapter(adapter);

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

        Contact contact = new Contact(name, phone);
        FakeData.contacts.add(contact);
        Toast.makeText(MainActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
    }

    private void addContactByHashMap(String name, String phone) {

        HashMap<String, String> contact = new HashMap<>();
        contact.put("name", name);
        contact.put("phone", phone);
        FakeData.hashContacts.add(contact);
        Toast.makeText(MainActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
    }
}
