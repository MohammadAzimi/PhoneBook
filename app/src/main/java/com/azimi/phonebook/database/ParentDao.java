package com.azimi.phonebook.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

public interface ParentDao {

    @Insert
    void insert(ParentEntity item);

    @Delete
    void delete(ParentEntity item);

    @Update
    void update(ParentEntity item);

}
