package com.vikar.work.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MapMarker {
    /*@javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    public long Id;

    private double latitude;
    private double longitude;

    private String streetname;
    private long streetnumber;
    private int ZIP;

    private String title;

    public MapMarker() {
    }

    public MapMarker(String streetname, long streetnumber) {
        this.streetname = streetname;
        this.streetnumber = streetnumber;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public long getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(long streetnumber) {
        this.streetnumber = streetnumber;
    }

    public int getZIP() {
        return ZIP;
    }

    public void setZIP(int ZIP) {
        this.ZIP = ZIP;
    }
}
