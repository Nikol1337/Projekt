package com.example.projekt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "example_table")
public class Slowko {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;
    private String translation;

    public Slowko(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    // Reszta kodu, getterów, setterów, itp.

    // Konwersja z obiektu Vocabulary do VocabularyEntity
    public static Slowko fromVocabulary(Vocabulary vocabulary) {
        return new Slowko(vocabulary.getWord(), vocabulary.getTranslation());
    }
}
