package com.example.projekt;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



public class BookContainer implements Parcelable {
    private List<Vocabulary> vocabularyList = new ArrayList<>();

    public List<Vocabulary> getVocabularyList() {
        return vocabularyList;
    }

    public void setVocabularyList(List<Vocabulary> vocabularyList) {
        this.vocabularyList = vocabularyList;
    }
    @Override
    public String toString(){
       return vocabularyList.get(0).getWord()+ vocabularyList.get(0).getTranslation();}

    public void addWordToVocabulary(String word, String translation) {
        Vocabulary newWord = new Vocabulary(word, translation);
        vocabularyList.add(newWord);
        Log.d("TagInfo",vocabularyList.get(0).getWord()+"addWordmetoda");
    }

    // Implementacja Parcelable
    protected BookContainer(Parcel in) {
        vocabularyList = in.createTypedArrayList(Vocabulary.CREATOR);
    }

    public static final Creator<BookContainer> CREATOR = new Creator<BookContainer>() {
        @Override
        public BookContainer createFromParcel(Parcel in) {
            return new BookContainer(in);
        }

        @Override
        public BookContainer[] newArray(int size) {
            return new BookContainer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(vocabularyList);
    }
}

