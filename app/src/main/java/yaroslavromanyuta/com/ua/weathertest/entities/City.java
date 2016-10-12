
package yaroslavromanyuta.com.ua.weathertest.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class City {

    @SerializedName("city_name")
    @Expose
    private String cityName;


    public City() {
    }


    public City(String cityName) {
        this.cityName = cityName;
    }


    public String getCityName() {
        return cityName;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
