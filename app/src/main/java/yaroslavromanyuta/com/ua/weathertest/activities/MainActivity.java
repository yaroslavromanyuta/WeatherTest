package yaroslavromanyuta.com.ua.weathertest.activities;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yaroslavromanyuta.com.ua.weathertest.PrjectUtils;
import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.apiclient.ObservableCreator;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.entities.CityResponse;
import yaroslavromanyuta.com.ua.weathertest.entities.FindResponse;
import yaroslavromanyuta.com.ua.weathertest.fragments.CityInfoListFragment;
import yaroslavromanyuta.com.ua.weathertest.fragments.DetailsFragment;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.KEY_CITY_INFO_ARRAY;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

@RuntimePermissions
public class MainActivity extends BaseActivity implements CityInfoListFragment.OnItemClickListener {

    Location location = null;
    ArrayList<CityInfo> cityInfoList = new ArrayList<>(3);

    CityInfoListFragment listFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void attachActivityViews() {
        super.attachActivityViews();
    }

    @Override
    protected void initActivityViews() {
        super.initActivityViews();
        listFragment = new CityInfoListFragment();
        changeFragment(R.id.container, listFragment, true);
        update();
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void update(){
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        ObservableCreator observableCreator = new ObservableCreator(this);
        observableCreator.createCityObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(CityResponse::getCities)
                .flatMap(Observable::from)
//                .subscribe(city -> Log.d(TAG, "update() called " + city));
                .flatMap(observableCreator::createFindWeatherObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(findResponse -> (findResponse.getCount()>0))
                .subscribe(new Subscriber<FindResponse>() {
                    @Override
                    public void onCompleted() {
                        //listFragment.setData(cityInfoList);
                        Log.d(TAG, "onCompleted() called");
                        if (cityInfoList == null){
                            listFragment.setEmptyText(getText(R.string.error));
                            listFragment.setListAdapter(new CityInfoListAdapter(new ArrayList<CityInfo>(0), MainActivity.this));
                        }else {
                            PrjectUtils.sortList(cityInfoList, location);
                            listFragment.setListAdapter(new CityInfoListAdapter(cityInfoList, MainActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(FindResponse findResponse) {
                        Log.d(TAG, "onNext() called with: findResponse = [" + findResponse + "]");
                        if (!findResponse.getCityInfo().isEmpty()) {
                            cityInfoList.add(PrjectUtils.getSortedCityInfoList(findResponse.getCityInfo(), location).get(0));
                        }
                    }
                });
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void updateList(){
        ObservableCreator observableCreator = new ObservableCreator(this);

        observableCreator.createCityObservable()
                .map(CityResponse::getCities)
                .flatMap(cities -> Observable.from(cities))
                .flatMap(city -> observableCreator.createFindWeatherObservable(city))
                .subscribe(new Subscriber<FindResponse>() {
                    @Override
                    public void onCompleted() {
                        listFragment.setData(cityInfoList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FindResponse findResponse) {
                        cityInfoList.add(PrjectUtils.getSortedCityInfoList(findResponse.getCityInfo(),location).get(0));
                    }
                });

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
    public void onItemSelected(int position) {

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFragment != null){
            detailsFragment.newInstanse(new Gson().toJson(cityInfoList.get(position)));
        }else {
            changeFragment(R.id.container, DetailsFragment.newInstanse(new Gson().toJson(cityInfoList.get(position))), true);
        }
    }
}
