
package yaroslavromanyuta.com.ua.weathertest.entitiyModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Wind {

    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("gust")
    @Expose
    private double gust;
    @SerializedName("deg")
    @Expose
    private double deg;

    public Wind() {
    }

    public Wind(double speed, double gust, double deg) {
        this.speed = speed;
        this.gust = gust;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
