
package yaroslavromanyuta.com.ua.weathertest.entities;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;


public class CityResponse {

    @SerializedName("cities")
    @Expose
    private List<City> cities = new ArrayList<City>();


    public CityResponse() {
    }


    public CityResponse(List<City> cities) {
        this.cities = cities;
    }


    public List<City> getCities() {
        return cities;
    }


    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
