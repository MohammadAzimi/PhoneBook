package com.azimi.phonebook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerContactsAdapter extends RecyclerView.Adapter<RecyclerContactsAdapter.ContactViewHolder> {

    private List<Contact> contactList; // Cached copy of contacts
    private Context context;
    private EventHandler mEventHandler;

    public RecyclerContactsAdapter(Context context, EventHandler eventHandler) {
        this.context = context;
        mEventHandler = eventHandler;
        contactList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contactList != null) {
            Contact contact = contactList.get(position);
            holder.contactName.setText(String.format(context.getResources().getString(R.string.contact_name_in_list),
                    contact.getFirstName(), contact.getLastName()));
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

    public Contact getContact(int position){
        return contactList.get(position);
    }

    public void removeItem(int position) {
        contactList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Contact contact, int position) {
        contactList.add(position, contact);
        // notify item added by position
        notifyItemInserted(position);
    }

    public interface EventHandler {
        void onClick(Contact contact, int position);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactImage;
        private TextView contactPhone;
        private TextView contactName;
        View viewBackground, viewForeground;

        private ContactViewHolder(View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contact_image);
            contactName = itemView.findViewById(R.id.text_contact_name);
            contactPhone = itemView.findViewById(R.id.text_contact_phone);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mEventHandler != null) {
                        mEventHandler.onClick(contactList.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }
    }
}
