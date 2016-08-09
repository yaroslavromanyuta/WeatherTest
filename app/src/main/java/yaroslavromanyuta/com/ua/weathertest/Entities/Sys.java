
package yaroslavromanyuta.com.ua.weathertest.Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Sys {

    @SerializedName("country")
    @Expose
    private String country;

    public Sys() {
    }

    public Sys(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
