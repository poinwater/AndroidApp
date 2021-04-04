package com.example.databasedemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public String name;

    @ColumnInfo(name = "age")
    public int age;

}
