package yaroslavromanyuta.com.ua.weathertest.apiclient;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.entities.City;
import yaroslavromanyuta.com.ua.weathertest.entities.CityResponse;
import yaroslavromanyuta.com.ua.weathertest.entities.FindResponse;


/**
 * Created by Yaroslav on 09.08.2016.
 */
public class ObservableCreator {

    private Context context;

    public ObservableCreator(Context context) {
        this.context = context;
    }

    public Observable<CityResponse> createCityObservable(){
        CityRetrofitService cityRetrofitService;

        Gson gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.city_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        cityRetrofitService = retrofit.create(CityRetrofitService.class);
        Observable<CityResponse> call = cityRetrofitService.getCities(context.getString(R.string.city_sheet_id),
                context.getString(R.string.city_sheet_name));

        return call.cache();
    }

    public Observable<FindResponse> createFindWeatherObservable(City city){
        OpenWeatherRetrofitService openWeatherRetrofitService;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.openweather_base_url))
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        openWeatherRetrofitService = retrofit.create(OpenWeatherRetrofitService.class);
        Observable<FindResponse> call = openWeatherRetrofitService.findCityWeather(city.getCityName(),
                context.getResources().getString(R.string.metric),
                Locale.getDefault().getLanguage(),
                context.getString(R.string.api_key));


        return call.cache();
    }
}
