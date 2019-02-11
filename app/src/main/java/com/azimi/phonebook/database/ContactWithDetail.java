package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.Nullable;

public class ContactWithDetail {

    @ColumnInfo(name = "id")
    public String contactId;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @Nullable
    @ColumnInfo(name = "phone_id")
    public String phoneId;

    @ColumnInfo(name = "type")
    public int phoneType;

    @Nullable
    @ColumnInfo(name = "number")
    public String phoneNumber;

    @Nullable
    @ColumnInfo(name = "email_id")
    public String emailId;

    @Nullable
    public String email;

    public String note;

}
