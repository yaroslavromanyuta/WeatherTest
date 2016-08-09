
package yaroslavromanyuta.com.ua.weathertest.Entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;


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
    private List<FindResponseListItem> findResponseListItem = new ArrayList<FindResponseListItem>();


    public FindResponse() {
    }

    public FindResponse(String message, String cod, int count, java.util.List<FindResponseListItem> findResponseListItem) {
        this.message = message;
        this.cod = cod;
        this.count = count;
        this.findResponseListItem = findResponseListItem;
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

    public java.util.List<FindResponseListItem> getFindResponseListItem() {
        return findResponseListItem;
    }


    public void setFindResponseListItem(java.util.List<FindResponseListItem> findResponseListItem) {
        this.findResponseListItem = findResponseListItem;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
