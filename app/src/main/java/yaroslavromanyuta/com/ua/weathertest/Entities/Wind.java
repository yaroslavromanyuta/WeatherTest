
package yaroslavromanyuta.com.ua.weathertest.Entities;


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
    private int deg;

    public Wind() {
    }

    public Wind(double speed, double gust, int deg) {
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

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
