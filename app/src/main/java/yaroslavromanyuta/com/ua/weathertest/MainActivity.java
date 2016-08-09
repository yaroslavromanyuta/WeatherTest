package yaroslavromanyuta.com.ua.weathertest;

import android.app.LoaderManager;
import android.content.Loader;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.apiClient.WeatherLoader;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;

import static  yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<CityInfo>>{

    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    public Loader<ArrayList<CityInfo>> onCreateLoader(int i, Bundle bundle) {

        return new WeatherLoader(this,location);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<CityInfo>> loader, ArrayList<CityInfo> cityInfos) {

        for (CityInfo cityInfo:
                cityInfos) {
            Log.d(TAG, "onLoadFinished: " + cityInfo.toString());

        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CityInfo>> loader) {

    }
}
