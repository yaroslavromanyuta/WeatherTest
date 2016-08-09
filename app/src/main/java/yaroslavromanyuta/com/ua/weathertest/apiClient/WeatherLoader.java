package yaroslavromanyuta.com.ua.weathertest.apiClient;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.entities.City;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.entities.FindResponse;

import static  yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

/**
 * Created by Yaroslav on 09.08.2016.
 */
public class WeatherLoader extends AsyncTaskLoader<ArrayList<CityInfo>> {

    Context context;
    Location location;

    public WeatherLoader(Context context, Location location) {
        super(context);
        this.context = context;
        this.location = location;
    }

    @Override
    public ArrayList<CityInfo> loadInBackground() {
        ResponseGetter responseGetter = new ResponseGetter(context);

        ArrayList<CityInfo> cityInfoArrayList = new ArrayList<>();

        List<City> cities = null;
        try {
            cities = responseGetter.getCities().getCities();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "loadInBackground: getCities Exeption -> ", e );
        }

        if (cities!=null){
            for (City city :
                    cities) {

                List<CityInfo> cityInfos = null;

                try {
                    cityInfos = responseGetter.findWeatherByCity(city).getCityInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert cityInfos != null;
                if (cityInfos.size() > 1) {

                    Log.d(TAG, "loadInBackground: moreThanOne " + city.toString());

                    Collections.sort(cityInfos, new Comparator<CityInfo>() {
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

                cityInfoArrayList.add(cityInfos.get(0));


            }
        }

        return cityInfoArrayList;
    }
}
