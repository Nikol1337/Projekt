package com.example.projekt;

import android.os.Parcel;
import android.os.Parcelable;

public class Vocabulary implements Parcelable {
    private String word;
    private String translation;

    public Vocabulary(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
    public String getCorrectAnswer(){
        return translation;
    }

    // Implementacja Parcelable
    protected Vocabulary(Parcel in) {
        word = in.readString();
        translation = in.readString();
    }

    public static final Creator<Vocabulary> CREATOR = new Creator<Vocabulary>() {
        @Override
        public Vocabulary createFromParcel(Parcel in) {
            return new Vocabulary(in);
        }

        @Override
        public Vocabulary[] newArray(int size) {
            return new Vocabulary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(translation);
    }
}

