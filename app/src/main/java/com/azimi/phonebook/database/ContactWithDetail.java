package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;

public class ContactWithDetail {

    @ColumnInfo(name = "id")
    public String contactId;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "type")
    public int phoneType;

    @ColumnInfo(name = "number")
    public String phoneNumber;

    public String email;

    public String note;

}
