package yaroslavromanyuta.com.ua.weathertest.apiClient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yaroslavromanyuta.com.ua.weathertest.entities.CityResponse;

/**
 * Created by Yaroslav on 09.08.2016.
 */
public interface CityRetrofitServise {
    @GET("/exec")
    Call<CityResponse> getCities(@Query("id") String id,
                                 @Query("sheet") String sheetName);
}
