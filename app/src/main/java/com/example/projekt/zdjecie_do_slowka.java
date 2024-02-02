package com.example.projekt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_img")
public class zdjecie_do_slowka {

    @PrimaryKey
    public long id;
    public String url;

    public zdjecie_do_slowka(long id,String url){
        this.id = id;
        this.url = url;
    }


}
