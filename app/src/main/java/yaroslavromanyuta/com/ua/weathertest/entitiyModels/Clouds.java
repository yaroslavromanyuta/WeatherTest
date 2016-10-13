
package yaroslavromanyuta.com.ua.weathertest.entitiyModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Clouds {

    @SerializedName("all")
    @Expose
    private int all;

    public Clouds() {
    }

    public Clouds(int all) {
        this.all = all;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
