
package yaroslavromanyuta.com.ua.weathertest.Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Rain {

    @SerializedName("3h")
    @Expose
    private double _3h;

    public Rain() {
    }

    public Rain(double _3h) {
        this._3h = _3h;
    }

    public double get3h() {
        return _3h;
    }

    public void set3h(double _3h) {
        this._3h = _3h;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
