package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Objects;


@Entity(tableName = "table_phone",
        primaryKeys = {"contact_id", "id"},
        foreignKeys = {@ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contact_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)})
public class Phone implements Parcelable {

    @NonNull
    private String id;
    @NonNull
    @ColumnInfo(name = "contact_id")
    private String contactId;

    @NonNull
    @ColumnInfo(name = "number")
    private String number;

    private int type;


    public Phone(@NonNull String id, @NonNull String contactId, int type, @NonNull String number) {
        this.contactId = contactId;
        this.type = type;
        this.number = number;
        this.id = id;
    }

    @NonNull
    public String getContactId() {
        return contactId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Ignore
    protected Phone(Parcel in) {
        contactId = Objects.requireNonNull(in.readString());
        type = in.readInt();
        number = Objects.requireNonNull(in.readString());
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
        dest.writeInt(type);
        dest.writeString(number);
        dest.writeString(id);
    }

    @Ignore
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Phone> CREATOR = new Parcelable.Creator<Phone>() {

        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };
}