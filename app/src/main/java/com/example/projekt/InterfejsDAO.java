package com.example.projekt;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InterfejsDAO {
    @Insert
    void insert(Slowko exampleEntity);

    @Query("SELECT * FROM example_table")
    List<Slowko> getAllData();

    @Query("SELECT id FROM example_table WHERE word=:polskie")
    int getID(String polskie);


    @Query("Select translation FROM example_table WHERE id=:ID")
    String getTranslation(Integer ID);
    @Insert
    void insert(zdjecie_do_slowka zdjecie);

    @Query("SELECT translation FROM example_table LEFT JOIN table_img ON example_table.id = table_img.id WHERE table_img.id IS NULL")
    List<String> getSlowa();


    @Query("SELECT * FROM table_img WHERE id = :slowkoId")
    zdjecie_do_slowka getZdjecieBySlowkoId(long slowkoId);

    @Query("SELECT id FROM table_img")
    List<Integer> getWszystkieID();

    @Query("SELECT url FROM table_img LEFT JOIN example_table ON table_img.id = example_table.id WHERE example_table.word = :nazwa")
    String getUrlPoNazwie(String nazwa);

}

