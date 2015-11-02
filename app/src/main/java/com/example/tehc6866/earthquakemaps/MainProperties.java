package com.example.tehc6866.earthquakemaps;

/**
 * Created by TEHC6866 on 30/10/2015.
 */
public class MainProperties {

    private String mag;
    private String place;
    private String time;
    private String updated;
    private String alert;
    private String tsunami;
    private String type;
    private GPoint geoPoint;

        public MainProperties(String mag, String place, String time, String updated, String alert, String tsunami, String type, GPoint geoPoint) {
            this.mag = mag;
            this.place = place;
            this.time = time;
            this.updated = updated;
            this.alert = alert;
            this.tsunami = tsunami;
            this.type = type;
            this.geoPoint = geoPoint;
        }

        public String getMag() {
            return mag;
        }

        public String getPlace() {
            return place;
        }

        public String getTime() {
            return time;
        }

        public String getUpdated() {
            return updated;
        }

        public String getAlert() {
            return alert;
        }

        public String getTsunami() {
            return tsunami;
        }

        public String getType() {
            return type;
        }

        public GPoint getGeoPoint() {
            return geoPoint;
        }
}

