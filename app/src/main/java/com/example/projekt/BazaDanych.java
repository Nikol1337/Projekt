package com.example.projekt;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Slowko.class, zdjecie_do_slowka.class}, version = 2)
public abstract class BazaDanych extends RoomDatabase {
    public abstract InterfejsDAO interfejsDAO();

}



