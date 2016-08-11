package yaroslavromanyuta.com.ua.weathertest;

import android.location.Location;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;

/**
 * Created by Yaroslav on 11.08.2016.
 */
public class PrjectUtils {
    public static void sortList(List<CityInfo> cityInfoList, final Location location){

        if (location!=null) {

            Collections.sort(cityInfoList, new Comparator<CityInfo>() {
                @Override
                public int compare(CityInfo cityInfo, CityInfo t1) {
                    Location location1 = new Location("p1");
                    location1.setLatitude(cityInfo.getCoord().getLat());
                    location1.setLongitude(cityInfo.getCoord().getLon());

                    Location location2 = new Location("p2");
                    location2.setLatitude(t1.getCoord().getLat());
                    location2.setLongitude(t1.getCoord().getLon());

                    return (int) (location.distanceTo(location1) - location.distanceTo(location2));
                }
            });
        }
    }
}
