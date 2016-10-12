package yaroslavromanyuta.com.ua.weathertest.apiclient;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import yaroslavromanyuta.com.ua.weathertest.entities.FindResponse;

/**
 * Created by Yaroslav on 09.08.2016.
 */
public interface OpenWeatherRetrofitService {
    @GET("find")
    Observable<FindResponse> findCityWeather(@Query("q") String city,
                                             @Query("units") String unitsFormat,
                                             @Query("lang") String lang,
                                             @Query("appid") String apiKey);

}
