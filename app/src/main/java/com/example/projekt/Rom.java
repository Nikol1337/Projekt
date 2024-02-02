package com.example.projekt;

import java.util.List;

public class Rom {
    private String headword;
    private String headword_full;
    private String wordclass;
    private List<Arab> arabs;

    public String getHeadword() {
        return headword;
    }

    public String getHeadwordFull() {
        return headword_full;
    }

    public String getWordclass() {
        return wordclass;
    }

    public List<Arab> getArabs() {
        return arabs;
    }

    @Override
    public String toString() {
        return "Rom{" +
                "headword='" + headword + '\'' +
                ", headword_full='" + headword_full + '\'' +
                ", wordclass='" + wordclass + '\'' +
                ", arabs=" + arabs +
                '}';
    }
}

