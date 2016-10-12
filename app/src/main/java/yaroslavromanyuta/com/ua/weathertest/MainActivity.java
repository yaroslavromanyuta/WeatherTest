package yaroslavromanyuta.com.ua.weathertest;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.apiclient.WeatherLoader;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.fragments.CityInfoListFragment;
import yaroslavromanyuta.com.ua.weathertest.fragments.DetailsFragment;
import yaroslavromanyuta.com.ua.weathertest.fragments.DetailsFragment_;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.ARGUMENT;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.KEY_CITY_INFO_ARRAY;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<CityInfo>>, CityInfoListFragment.OnItemClickListener {

    Location location = null;
    ListFragment listFragment = null;
    FragmentManager fragmentManager;
    ArrayList<CityInfo> cityInfoList = null;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        container = (FrameLayout) findViewById(R.id.container);
        if (listFragment == null)
        listFragment = new CityInfoListFragment();
        fragmentManager = getFragmentManager();

        if (savedInstanceState != null) {
            cityInfoList = new Gson().fromJson(savedInstanceState.getString(KEY_CITY_INFO_ARRAY),
                    new TypeToken<List<CityInfo>>() {
                    }.getType());
        }

        if (cityInfoList == null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MainActivityPermissionsDispatcher.updateWithCheck(this);
            } else {
                update();
            }

        }else{
            listFragment.setListAdapter(new CityInfoListAdapter(cityInfoList, this));
        }

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.add(R.id.container, listFragment);
            fragmentTransaction.commit();
        }else{
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.replace(R.id.container, listFragment);
            fragmentTransaction.commit();
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void update(){
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void updateList(){
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
            DetailsFragment newDetailsFragment = new DetailsFragment_();
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
