package yaroslavromanyuta.com.ua.weathertest.updateservice;

import java.util.Collection;

import yaroslavromanyuta.com.ua.weathertest.entetiesbd.CityInfoDB;
import yaroslavromanyuta.com.ua.weathertest.entetiesbd.DaoSession;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.CityInfo;

/**
 * Created by Yaroslav on 14.10.2016.
 */

public class DatabaseUpdater {

    public static void updateDatabase(Collection<CityInfo> cityInfos, DaoSession daoSession){

        daoSession.getCityInfoDBDao().deleteAll();

        for (CityInfo cityInfo :
                cityInfos) {

            CityInfoDB cityInfoDb = new CityInfoDB();

            cityInfoDb.setId(cityInfo.getId());
            cityInfoDb.setName(cityInfo.getName());
            cityInfoDb.setDt(cityInfo.getDt());
            cityInfoDb.setCloudsAll(cityInfo.getClouds().getAll());
            cityInfoDb.setCordLat(cityInfo.getCoord().getLat());
            cityInfoDb.setCordLon(cityInfo.getCoord().getLon());
            cityInfoDb.setHumidity(cityInfo.getMain().getHumidity());
            cityInfoDb.setPressure(cityInfo.getMain().getPressure());
            cityInfoDb.setTemp(cityInfo.getMain().getTemp());
            cityInfoDb.setTempMin(cityInfo.getMain().getTempMin());
            cityInfoDb.setTempMax(cityInfo.getMain().getTempMax());
            cityInfoDb.setSnow3h(cityInfo.getSnow().get3h());
            cityInfoDb.setRain3h(cityInfo.getRain().get3h());
            cityInfoDb.setWeatherDescription(cityInfo.getWeather().get(0).getDescription());
            cityInfoDb.setWeatherId(cityInfo.getWeather().get(0).getId());
            cityInfoDb.setWeatherMain(cityInfo.getWeather().get(0).getMain());
            cityInfoDb.setWeatherIcon(cityInfo.getWeather().get(0).getIcon());
            cityInfoDb.setWindSpeed(cityInfo.getWind().getSpeed());
            cityInfoDb.setWindDeg(cityInfo.getWind().getDeg());
            cityInfoDb.setWindGust(cityInfo.getWind().getGust());
            cityInfoDb.setCountry(cityInfo.getSys().getCountry());

            daoSession.getCityInfoDBDao().save(cityInfoDb);
        }



        // TODO: 14.10.2016 send intent to broadcast to notify observer
    }

}
