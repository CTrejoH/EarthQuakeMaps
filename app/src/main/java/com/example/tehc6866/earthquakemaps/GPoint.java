package com.example.tehc6866.earthquakemaps;

/**
 * Created by TEHC6866 on 30/10/2015.
 */
public class GPoint {
    private float longitude;
    private float latitude;
    private float depth;

    public GPoint(float longitude, float latitude, float depth) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.depth = depth;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }
}
