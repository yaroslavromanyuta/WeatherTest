package yaroslavromanyuta.com.ua.weathertest.apiclient;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import yaroslavromanyuta.com.ua.weathertest.entities.CityResponse;

/**
 * Created by Yaroslav on 09.08.2016.
 */
public interface CityRetrofitService {
    @GET("exec")
    Observable<CityResponse> getCities(@Query("id") String id,
                                       @Query("sheet") String sheetName);
}
