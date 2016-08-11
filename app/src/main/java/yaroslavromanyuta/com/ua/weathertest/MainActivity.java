package yaroslavromanyuta.com.ua.weathertest;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.apiClient.WeatherLoader;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.fragments.CityInfoListFragment;
import yaroslavromanyuta.com.ua.weathertest.fragments.DetailsFragment;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.ARGUMENT;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.KEY_CITY_INFO_ARRAY;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<CityInfo>>, CityInfoListFragment.OnItemClickListener {

    Location location = null;
    ListFragment listFragment;
    FragmentManager fragmentManager;
    ArrayList<CityInfo> cityInfoList = null;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (FrameLayout) findViewById(R.id.container);
        listFragment = new CityInfoListFragment();
        fragmentManager = getFragmentManager();

        if (savedInstanceState != null) {
            cityInfoList = new Gson().fromJson(savedInstanceState.getString(KEY_CITY_INFO_ARRAY),
                    new TypeToken<List<CityInfo>>() {
                    }.getType());
        }

        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (cityInfoList == null) {
            getLoaderManager().restartLoader(0, null, this);
            getLoaderManager().getLoader(0).forceLoad();
        }else{
            listFragment.setListAdapter(new CityInfoListAdapter(cityInfoList, this));
        }

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_CITY_INFO_ARRAY,new Gson().toJson(cityInfoList));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Loader<ArrayList<CityInfo>> onCreateLoader(int i, Bundle bundle) {

        return new WeatherLoader(this,location);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<CityInfo>> loader, ArrayList<CityInfo> cityInfos) {

        if (cityInfos == null){
            listFragment.setEmptyText(getText(R.string.error));
            listFragment.setListAdapter(new CityInfoListAdapter(new ArrayList<CityInfo>(0), this));
        }else {
            cityInfoList = cityInfos;
            PrjectUtils.sortList(cityInfoList, location);
            listFragment.setListAdapter(new CityInfoListAdapter(cityInfoList, this));
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CityInfo>> loader) {

    }

    @Override
    public void onItemSelected(int position) {

        DetailsFragment detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFragment != null){
            detailsFragment.newInstanse(new Gson().toJson(cityInfoList.get(position)));
        }else {
            DetailsFragment newDetailsFragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putString(ARGUMENT, new Gson().toJson(cityInfoList.get(position)));
            newDetailsFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.container,newDetailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
