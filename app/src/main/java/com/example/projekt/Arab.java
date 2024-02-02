package com.example.projekt;

import java.util.List;

public class Arab {
    private String header;
    private List<Translation> translations;

    public String getHeader() {
        return header;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    @Override
    public String toString() {
        return "Arab{" +
                "header='" + header + '\'' +
                ", translations=" + translations +
                '}';
    }
}

