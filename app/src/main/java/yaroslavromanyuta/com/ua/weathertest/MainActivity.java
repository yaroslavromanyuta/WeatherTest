package yaroslavromanyuta.com.ua.weathertest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.apiClient.WeatherLoader;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.fragments.CityInfoListFragment;

import static  yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<CityInfo>>{

    Location location;
    ListFragment listFragment;
    FragmentManager fragmentManager;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         container = (FrameLayout) findViewById(R.id.container);
        listFragment = new CityInfoListFragment();
        fragmentManager = getFragmentManager();


        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();

        if (savedInstanceState == null) {
            // при первом запуске программы
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            // добавляем в контейнер при помощи метода add()
            fragmentTransaction.add(R.id.container, listFragment);
            fragmentTransaction.commit();
        }else{
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            // добавляем в контейнер при помощи метода add()
            fragmentTransaction.replace(R.id.container, listFragment);
            fragmentTransaction.commit();
        }
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

        listFragment.setListAdapter(new CityInfoListAdapter(cityInfos, this));

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CityInfo>> loader) {

    }
}
