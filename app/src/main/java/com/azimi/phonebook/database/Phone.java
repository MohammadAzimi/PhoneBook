package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;


@Entity(tableName = "table_phone",
        primaryKeys = {"contact_id", "id"},
        foreignKeys = {@ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contact_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)})
public class Phone {

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

    public void setId(@NonNull String id){
        this.id = id;
    }
}
