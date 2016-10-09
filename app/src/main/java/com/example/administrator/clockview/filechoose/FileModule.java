package com.example.administrator.clockview.filechoose;

import android.support.annotation.NonNull;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.09.26 13:35
 */

public class FileModule {

    public static final int FILE_TYPE_DOC = 1;
    //其他后缀的文件
    public static final int FILE_TYPE_FILE = 2;
    public static final int FILE_TYPE_FOLDER = 3;
    public static final int FILE_TYPE_MP3 = 4;
    public static final int FILE_TYPE_PIC = 5;
    public static final int FILE_TYPE_TXT = 6;
    public static final int FILE_TYPE_VIDEO = 7;

    private int floor = 0;
    private String name;
    private String fatherNname = "";
    private boolean isOpen = false;
    private int fileType = FILE_TYPE_FOLDER;

    public FileModule(int floor, @NonNull String name, String fatherNname,int fileType) {
        this.floor = floor;
        this.name = name;
        this.fileType=fileType;
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

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
}
