package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "table_emails",
        primaryKeys = {"contact_id", "id"},
        foreignKeys = {@ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contact_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)})
public class Email implements Parcelable {

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

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Ignore
    protected Email(Parcel in) {
        contactId = Objects.requireNonNull(in.readString());
        email = Objects.requireNonNull(in.readString());
        id = Objects.requireNonNull(in.readString());
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactId);
        dest.writeString(email);
        dest.writeString(id);
    }

    @Ignore
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel in) {
            return new Email(in);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };
}