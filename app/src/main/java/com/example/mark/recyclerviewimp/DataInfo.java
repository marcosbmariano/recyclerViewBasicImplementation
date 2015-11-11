package com.example.mark.recyclerviewimp;

/**
 * Created by mark on 11/10/15.
 */
public class DataInfo {

    private int iconId;
    private String title;

    public DataInfo( int iconId, String title){
        this.iconId = iconId;
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
