package com.azimi.phonebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FakeData.hashContacts.isEmpty()) {
            setContentView(R.layout.activity_contacts_empty);
        } else {
            setContentView(R.layout.activity_contacts);

            ListView listView = findViewById(R.id.list_contacts);
            String[] from = {"name", "phone"};
            int[] to = {R.id.text_contact_name, R.id.text_contact_phone};
            SimpleAdapter adapter = new SimpleAdapter(this, FakeData.hashContacts, R.layout.layout_contact, from, to);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = FakeData.hashContacts.get(position).get("name");
                    String phone = FakeData.hashContacts.get(position).get("phone");
                    showOptions(name, phone);
                }
            });

        }
    }

    private void showOptions(String name, final String phone) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(phone)
                .setCancelable(true)
                .setTitle(name);

        builder1.setPositiveButton(
                "CALL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialNumber(phone);
                    }
                });

        builder1.setNegativeButton(
                "MESSAGE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendSMS(phone);
                    }
                });
        builder1.show();
    }

    public void dialNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void sendSMS(String number) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number)));
    }
}
