package com.azimi.phonebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ParentEntity {

    @PrimaryKey()
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    protected int parentId;
}
