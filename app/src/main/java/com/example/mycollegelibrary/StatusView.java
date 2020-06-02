package com.example.mycollegelibrary;

public class StatusView {
    private String Barcode;
    private String Status;



    public StatusView(String barcode, String status) {
        Barcode = barcode;
        Status = status;
        this.Barcode = barcode;
        this.Status = status;
    }


   public String toString(){
        return "Barcode: " + Barcode + "\r\n" + "Status: " +Status;

    }

    public StatusView() {
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

