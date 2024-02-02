package com.example.projekt;

import android.os.Build;
import android.text.Html;

public class Translation {
    private String source;
    private String target;

    public String getText() {
        return source;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
    public String getCleanedSource(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString();
        }
        return source;
    }
    public String getCleanedTarget() {
        // usuń znaczniki HTML z target
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(target, Html.FROM_HTML_MODE_LEGACY).toString();
        }
        return target;
    }
    @Override
    public String toString() {
        return "Translation{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
