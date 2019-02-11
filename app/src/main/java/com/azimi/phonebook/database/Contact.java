package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


@Entity(tableName = "table_contacts",
        primaryKeys = {"id"})
public class Contact {


    //@PrimaryKey(autoGenerate = true)
    //@ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    //private int id;
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    private String note;


    public Contact(@NonNull String id, String firstName, String lastName, String note) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.note = note;
    }

//    public Contact(String firstName, String lastName){
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }

//    public int getId() {
//        return this.id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String fullName) {
        this.id = fullName;
    }
}
