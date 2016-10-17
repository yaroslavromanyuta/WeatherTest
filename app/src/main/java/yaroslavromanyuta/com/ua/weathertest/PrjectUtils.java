package yaroslavromanyuta.com.ua.weathertest;

import android.location.Location;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.entetiesbd.CityInfoDB;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.CityInfo;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

/**
 * Created by Yaroslav on 11.08.2016.
 */
public class PrjectUtils {

    public static void sortList(List<CityInfo> cityInfoList, final Location location){

        if (location!=null) {

            Collections.sort(cityInfoList, (cityInfo, t1) -> {
                Location location1 = new Location("p1");
                location1.setLatitude(cityInfo.getCoord().getLat());
                location1.setLongitude(cityInfo.getCoord().getLon());

                Location location2 = new Location("p2");
                location2.setLatitude(t1.getCoord().getLat());
                location2.setLongitude(t1.getCoord().getLon());

                return (int) (location.distanceTo(location1) - location.distanceTo(location2));
            });
        }
    }

    public static void sortDBList(List<CityInfoDB> cityInfoDBList, final Location location){

        if (location!=null) {

            Collections.sort(cityInfoDBList, (cityInfoDB, t1) -> {
                Location location1 = new Location("p1");
                location1.setLatitude(cityInfoDB.getCordLat());
                location1.setLongitude(cityInfoDB.getCordLon());

                Location location2 = new Location("p2");
                location2.setLatitude(t1.getCordLat());
                location2.setLongitude(t1.getCordLon());

                Log.d(TAG, "sortDBList() called with: city1 " + cityInfoDB.getName() + "dist to location = " + location.distanceTo(location1)
                        + " city2 "+ t1.getName()+ "dist to location = " + location.distanceTo(location2)
                        + " dist = " + (location.distanceTo(location1) - location.distanceTo(location2)));

                return (int) (location.distanceTo(location1) - location.distanceTo(location2));
            });
        }
    }

    public static List<CityInfo> getSortedCityInfoList(List<CityInfo> cityInfoList, final Location location){

        if (location!=null) {

            Collections.sort(cityInfoList, (cityInfo, t1) -> {
                Location location1 = new Location("p1");
                location1.setLatitude(cityInfo.getCoord().getLat());
                location1.setLongitude(cityInfo.getCoord().getLon());

                Location location2 = new Location("p2");
                location2.setLatitude(t1.getCoord().getLat());
                location2.setLongitude(t1.getCoord().getLon());

                return (int) (location.distanceTo(location1) - location.distanceTo(location2));
            });
        }

        return cityInfoList;
    }
}
