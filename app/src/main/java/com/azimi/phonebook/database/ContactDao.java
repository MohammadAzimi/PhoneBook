package com.azimi.phonebook.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Contact contact);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Contact contact);

    @Query("SELECT * FROM table_contacts")
    LiveData<List<Contact>> getAllContacts();

    @Delete
    void delete(Contact contact);

    @Query("SELECT table_contacts.id, table_contacts.first_name, table_contacts.last_name," +
            " table_phone.type, table_phone.number, table_emails.email, table_contacts.note" +
            " FROM table_contacts" +
            " INNER JOIN table_phone ON id = table_phone.contact_id" +
            " INNER JOIN table_emails ON id = table_emails.contact_id" +
            " WHERE id = :contactId")
    LiveData<List<ContactWithDetail>> getContactsWithDetail(String contactId);

//    @Query("SELECT * " +
//            "FROM table_contacts " +
//            "WHERE table_contacts.id = ( SELECT MAX(table_contacts.id) FROM table_contacts)")
//    Contact getLastContact();
}
