package yaroslavromanyuta.com.ua.weathertest.apiClient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.entities.City;
import yaroslavromanyuta.com.ua.weathertest.entities.CityResponse;
import yaroslavromanyuta.com.ua.weathertest.entities.FindResponse;

import static  yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;


/**
 * Created by Yaroslav on 09.08.2016.
 */
public class ResponseGetter {

    Context context;

    public ResponseGetter(Context context) {
        this.context = context;
    }

    public CityResponse getCities() throws IOException {
        CityRetrofitServise cityRetrofitServise;

        Gson gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.city_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        cityRetrofitServise = retrofit.create(CityRetrofitServise.class);
        Call<CityResponse> call = cityRetrofitServise.getCities(context.getString(R.string.city_sheet_id),
                context.getString(R.string.city_sheet_name));



        Response<CityResponse> response = null;
        response = call.execute();

        Log.d(TAG, "getCities: URL" + response.raw());

        Log.d(TAG, "getCities: " + "response created. HTTP status code= " + response.code() + "; message= " + response.message() +
                "; success = " + response.isSuccessful() );
        Log.d(TAG, "getCities() returned: " + response);

        return response.body();
    }

    public FindResponse findWeatherByCity (City city) throws IOException {
        OpenWeatherRetrofitServise openWeatherRetrofitServise;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.openweather_base_url))
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        openWeatherRetrofitServise = retrofit.create(OpenWeatherRetrofitServise.class);
        Call<FindResponse> call = openWeatherRetrofitServise.findCityWeather(city.getCityName(),
                context.getResources().getString(R.string.metric),
                Locale.getDefault().getLanguage(),
                context.getString(R.string.api_key));

        Response<FindResponse> response = null;
        response = call.execute();

        Log.d(TAG, "findWeatherByCity: lang == " + Locale.getDefault().getLanguage());
        Log.d(TAG, "findWeatherByCity: " + "response created. HTTP status code= " + response.code() + "; message= " + response.message() +
                "; success = " + response.isSuccessful() );
        Log.d(TAG, "findWeatherByCity() returned: " + response);

        return response.body();
    }
}
