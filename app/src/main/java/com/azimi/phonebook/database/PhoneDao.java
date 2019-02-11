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
public interface PhoneDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Phone phone);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Phone phone);

    @Query("SELECT * FROM table_phone WHERE contact_id = :contactId")
    LiveData<List<Phone>> getPhonesByContactId(String contactId);

    @Delete
    void delete(Phone phone);

    @Query("SELECT * FROM table_phone WHERE id = :phoneID")
    Phone getPhoneById(String phoneID);
}
