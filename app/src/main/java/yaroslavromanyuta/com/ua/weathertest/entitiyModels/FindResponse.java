
package yaroslavromanyuta.com.ua.weathertest.entitiyModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class FindResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("list")
    @Expose
    private List<CityInfo> cityInfo = new ArrayList<CityInfo>();


    public FindResponse() {
    }

    public FindResponse(String message, String cod, int count, java.util.List<CityInfo> cityInfo) {
        this.message = message;
        this.cod = cod;
        this.count = count;
        this.cityInfo = cityInfo;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public java.util.List<CityInfo> getCityInfo() {
        return cityInfo;
    }


    public void setCityInfo(java.util.List<CityInfo> cityInfo) {
        this.cityInfo = cityInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
