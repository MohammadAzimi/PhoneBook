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
public interface EmailDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Email email);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Email email);

    @Query("SELECT * FROM table_emails WHERE contact_id = :contactId")
    LiveData<List<Email>> getEmailsByContactId(String contactId);

    @Delete
    void delete(Email email);

    @Query("SELECT * FROM table_emails WHERE id = :emailId")
    Email getEmailById(String emailId);
}
