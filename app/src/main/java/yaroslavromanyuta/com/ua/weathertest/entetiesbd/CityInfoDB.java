package yaroslavromanyuta.com.ua.weathertest.entetiesbd;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Yaroslav on 14.10.2016.
 */

@Entity
public class CityInfoDB {

    @Id
    private long id;

    private String name;
    private double cordLon;
    private double cordLat;
    private double temp;
    private int humidity;
    private double pressure;
    private double tempMin;
    private double tempMax;
    private long dt;
    private double windSpeed;
    private double windGust;
    private double windDeg;
    private String country;
    private double rain3h;
    private int cloudsAll;
    private double snow3h;
    private int weatherId;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;

    @Generated(hash = 1099974940)
    public CityInfoDB(long id, String name, double cordLon, double cordLat,
            double temp, int humidity, double pressure, double tempMin,
            double tempMax, long dt, double windSpeed, double windGust,
            double windDeg, String country, double rain3h, int cloudsAll,
            double snow3h, int weatherId, String weatherMain,
            String weatherDescription, String weatherIcon) {
        this.id = id;
        this.name = name;
        this.cordLon = cordLon;
        this.cordLat = cordLat;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.dt = dt;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.windDeg = windDeg;
        this.country = country;
        this.rain3h = rain3h;
        this.cloudsAll = cloudsAll;
        this.snow3h = snow3h;
        this.weatherId = weatherId;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
    }

    @Generated(hash = 1201673776)
    public CityInfoDB() {
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getCordLon() {
        return this.cordLon;
    }
    public void setCordLon(double cordLon) {
        this.cordLon = cordLon;
    }
    public double getCordLat() {
        return this.cordLat;
    }
    public void setCordLat(double cordLat) {
        this.cordLat = cordLat;
    }
    public double getTemp() {
        return this.temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public int getHumidity() {
        return this.humidity;
    }
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    public double getPressure() {
        return this.pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public double getTempMin() {
        return this.tempMin;
    }
    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }
    public double getTempMax() {
        return this.tempMax;
    }
    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }
    public long getDt() {
        return this.dt;
    }
    public void setDt(long dt) {
        this.dt = dt;
    }
    public double getWindSpeed() {
        return this.windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public double getWindGust() {
        return this.windGust;
    }
    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }
    public double getWindDeg() {
        return this.windDeg;
    }
    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public double getRain3h() {
        return this.rain3h;
    }
    public void setRain3h(double rain3h) {
        this.rain3h = rain3h;
    }
    public int getCloudsAll() {
        return this.cloudsAll;
    }
    public void setCloudsAll(int cloudsAll) {
        this.cloudsAll = cloudsAll;
    }
    public double getSnow3h() {
        return this.snow3h;
    }
    public void setSnow3h(double snow3h) {
        this.snow3h = snow3h;
    }
    public int getWeatherId() {
        return this.weatherId;
    }
    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }
    public String getWeatherMain() {
        return this.weatherMain;
    }
    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }
    public String getWeatherDescription() {
        return this.weatherDescription;
    }
    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
    public String getWeatherIcon() {
        return this.weatherIcon;
    }
    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }







}
