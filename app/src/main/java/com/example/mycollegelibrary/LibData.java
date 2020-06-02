package com.example.mycollegelibrary;

public class LibData {
    private String Barcode;
    private String BookName;
    private String Author;



    public LibData(String barcode, String bookName, String author) {
        Barcode = barcode;
        BookName = bookName;
        Author = author;
        this.Barcode = barcode;
        this.BookName = bookName;
        this.Author = author;
    }
    public LibData() {

    }
    public String toString(){
        return "Barcode: " + Barcode + "\r\n" + "Book Name: " +BookName+ "\r\n" +"Author: "+ Author;

    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }


}