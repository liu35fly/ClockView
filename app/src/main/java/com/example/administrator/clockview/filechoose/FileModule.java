package com.example.administrator.clockview.filechoose;

import android.support.annotation.NonNull;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.09.26 13:35
 */

public class FileModule {
    private int floor = 0;
    private String name;
    private String fatherNname = "";
    private boolean isOpen = false;

    public FileModule(int floor, @NonNull String name, String fatherNname) {
        this.floor = floor;
        this.name = name;
        if (fatherNname == null) {
            return;
        }
        this.fatherNname = fatherNname;
    }

    public int getFloor() {
        return floor;
    }

    public String getName() {
        return name;
    }

    public String getFatherNname() {
        return fatherNname;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
