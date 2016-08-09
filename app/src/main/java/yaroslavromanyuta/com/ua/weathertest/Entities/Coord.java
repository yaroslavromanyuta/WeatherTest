
package yaroslavromanyuta.com.ua.weathertest.Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Coord {

    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("lat")
    @Expose
    private double lat;

    public Coord() {
    }

    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
