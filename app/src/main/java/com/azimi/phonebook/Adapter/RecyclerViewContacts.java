package com.azimi.phonebook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azimi.phonebook.Contact;
import com.azimi.phonebook.R;

import java.util.List;

public class RecyclerViewContacts extends RecyclerView.Adapter<RecyclerViewContacts.ContactViewHolder> {

    private final LayoutInflater mInflater;
    private List<Contact> contactList; // Cached copy of contacts

    public RecyclerViewContacts(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.layout_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contactList != null) {
            Contact contact = contactList.get(position);
            holder.contactName.setText(contact.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.contactName.setText(R.string.contact_list_empty);
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setContactList(List<Contact> contacts) {
        this.contactList = contacts;
        notifyDataSetChanged();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactImage;
        private TextView contactPhone;
        private TextView contactName;

        private ContactViewHolder(View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contact_image);
            contactName = itemView.findViewById(R.id.text_contact_name);
            contactPhone = itemView.findViewById(R.id.text_contact_phone);
        }
    }
}
