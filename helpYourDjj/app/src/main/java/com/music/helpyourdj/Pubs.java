package com.music.helpyourdj;

public class Pubs {
    String website;
    String namePub;
    String imagePub;
    String adress;
   String latitude;
   String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Pubs(String website, String namePub, String imagePub, String adress) {
        this.website = website;
        this.namePub = namePub;
        this.imagePub = imagePub;
        this.adress = adress;
    }

    public Pubs() {

    }

    public String getImagePub() {
        return imagePub;
    }

    public void setImagePub(String imagePub) {
        this.imagePub = imagePub;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNamePub() {
        return namePub;
    }

    public void setNamePub(String namePub) {
        this.namePub = namePub;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }


}
