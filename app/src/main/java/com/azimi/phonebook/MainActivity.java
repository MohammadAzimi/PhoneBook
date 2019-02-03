package com.azimi.phonebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddContact = findViewById(R.id.btn_add_contact);
        Button buttonShowContacts = findViewById(R.id.btn_show_contacts);

        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        buttonShowContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContactsActivity.class));
            }
        });
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
