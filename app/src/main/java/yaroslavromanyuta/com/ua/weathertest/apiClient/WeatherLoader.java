package yaroslavromanyuta.com.ua.weathertest.apiclient;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.PrjectUtils;
import yaroslavromanyuta.com.ua.weathertest.entities.City;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;

import static  yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

/**
 * Created by Yaroslav on 09.08.2016.
 */
public class WeatherLoader extends AsyncTaskLoader<ArrayList<CityInfo>> {

    private Context context;
    private Location location;

    public WeatherLoader(Context context, Location location) {
        super(context);
        this.context = context;
        this.location = location;
    }

    @Override
    public ArrayList<CityInfo> loadInBackground() {
        ResponseGetter responseGetter = new ResponseGetter(context);



        List<City> cities = null;
        try {
            cities = responseGetter.getCities().getCities();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "loadInBackground: getCities Exeption -> ", e );
        }

        ArrayList<CityInfo> cityInfoArrayList = null;
        if (cities!=null) {
            cityInfoArrayList = new ArrayList<>(cities.size());
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

                    PrjectUtils.sortList(cityInfos,location);
                }

                if (cityInfos.size()>0) {
                    cityInfos.get(0).setName(city.getCityName());
                    cityInfoArrayList.add(cityInfos.get(0));
                }


            }
        }

        return cityInfoArrayList;
    }
}
