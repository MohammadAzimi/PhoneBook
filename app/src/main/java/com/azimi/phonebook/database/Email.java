package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "table_emails",
        primaryKeys = {"contact_id", "id"},
        foreignKeys = {@ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contact_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)})
public class Email {

    @NonNull
    private String id;
    @NonNull
    @ColumnInfo(name = "contact_id")
    private String contactId;
    @NonNull
    private String email;

    public Email(@NonNull String id, @NonNull String contactId, @NonNull String email) {
        this.contactId = contactId;
        this.email = email;
        this.id = id;
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

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id){
        this.id = id;
    }
}
