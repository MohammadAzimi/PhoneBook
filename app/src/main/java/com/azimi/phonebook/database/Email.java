package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "table_emails",
        primaryKeys = {"contact_id", "email"},
        foreignKeys = {@ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contact_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)})
public class Email {

    @NonNull
    @ColumnInfo(name = "contact_id")
    private String contactId;
    @NonNull
    private String email;

    public Email(@NonNull String contactId, @NonNull String email) {
        this.contactId = contactId;
        this.email = email;
    }

    @NonNull
    public String getContactId() {
        return contactId;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }
}
