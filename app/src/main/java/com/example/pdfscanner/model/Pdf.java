package com.example.pdfscanner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PDF_TABLE")
public class Pdf {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "path")
    private String pdfFilePath;

    @ColumnInfo(name = "name")
    private String pdfName;

    public Pdf(String pdfFilePath, String pdfName) {
        this.pdfFilePath = pdfFilePath;
        this.pdfName = pdfName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }
}
