package yaroslavromanyuta.com.ua.weathertest.activities;

import android.Manifest;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import yaroslavromanyuta.com.ua.weathertest.PrjectUtils;
import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.application.WeatherApp;
import yaroslavromanyuta.com.ua.weathertest.entetiesbd.CityInfoDB;
import yaroslavromanyuta.com.ua.weathertest.entetiesbd.DaoSession;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.fragments.CityInfoListFragment;
import yaroslavromanyuta.com.ua.weathertest.fragments.DetailsFragment;
import yaroslavromanyuta.com.ua.weathertest.updateservice.UpdateBroadcastReceiver;
import yaroslavromanyuta.com.ua.weathertest.updateservice.UpdateService;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.KEY_CITY_INFO_ARRAY;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.REQUEST_CHECK_SETTINGS;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

public class MainActivity extends BaseActivity implements CityInfoListFragment.OnItemClickListener, Observer {

    private Location location = null;
    private ArrayList<CityInfo> cityInfoList = new ArrayList<>(3);

    private CityInfoListFragment listFragment = null;

    private UpdateBroadcastReceiver updateBroadcastReceiver;

    DaoSession daoSession;

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
        Log.d(TAG, "initActivityViews() called");
        super.initActivityViews();
        updateBroadcastReceiver = new UpdateBroadcastReceiver(this);
        registerReceiver(updateBroadcastReceiver,new IntentFilter(UpdateBroadcastReceiver.BROADCAST_UPDATE_ACTION));
        daoSession = ((WeatherApp) getApplication()).getDaoSession();
        listFragment = new CityInfoListFragment();
        changeFragment(R.id.container, listFragment, false);
        showFromDB();
        requestPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    @Override
    protected void catchPermissionResult(Permission permission) {
        Log.d(TAG, "catchPermissionResult() called with: permission = [" + permission + "]");
        switch (permission.name) {
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (!permission.granted){
                    showLocationAllertDialog();
                } else {
                    getLastKnownLocation();
                }
                showWaitingDialog(getString(R.string.wait_dialog_message), false);
                break;
        }
    }

    private void showLocationAllertDialog() {
        Log.d(TAG, "showLocationAllertDialog() called");
        showAlertDialog(getString(R.string.allert_dialog_no_location_permission),
                (dialog, which) -> update());
    }

    private void getLastKnownLocation(){
        Log.d(TAG, "getLastKnownLocation() called");
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);
        final LocationRequest locationRequest = LocationRequest.create()
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_LOW_POWER);
        Subscription subscription = locationProvider.checkLocationSettings(
                new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build()
        )
                .doOnNext(locationSettingsResult -> {
                    Status status = locationSettingsResult.getStatus();
                    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException th) {
                            Log.e("MainActivity", "Error opening settings activity.", th);
                        }
                    }
                })
                .flatMap(new Func1<LocationSettingsResult, Observable<Location>>() {
                    @Override
                    public Observable<Location> call(LocationSettingsResult locationSettingsResult) {
                        return locationProvider.getUpdatedLocation(locationRequest);
                    }
                })
                .subscribe(location1 -> {
                    Log.d(TAG, "onNext() called with: location = [" + location + "]");
                    location = location1;
                    update();
                }
                , throwable -> {
                    showLocationAllertDialog();
                    update();
                }
        );
        addSubscriptionToComposite(subscription);
    }

    private void update(){
        Log.d(TAG, "update() called");

        startService(UpdateService.getLaunchIntent(this, location));
//        ObservableCreator observableCreator = new ObservableCreator(this);
//        Subscription subscription = observableCreator.createCityObservable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .map(CityResponse::getCities)
//                .flatMap(Observable::from)
//                .flatMap(observableCreator::createFindWeatherObservable)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(findResponse -> (findResponse.getCount()>0))
//                .subscribe(responseSubscriber);
//        addSubscriptionToComposite(subscription);
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
    public void onItemSelected(long id) {

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFragment != null){
            detailsFragment.newInstanse(id);
        }else {
            changeFragment(R.id.container, DetailsFragment.newInstanse(id), true);
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        Log.d(TAG, "update() called with: o = [" + o + "], arg = [" + arg + "]");
        showFromDB();
    }

    private void showFromDB() {
        dismissWaitingDialog();
        List<CityInfoDB> weather = daoSession.getCityInfoDBDao().loadAll();
        PrjectUtils.sortDBList(weather, location);
        listFragment.setListAdapter(new CityInfoListAdapter(weather, this));
    }

//    private Subscriber<FindResponse> responseSubscriber = new Subscriber<FindResponse>() {
//        @Override
//        public void onCompleted() {
//            //listFragment.setData(cityInfoList);
//            Log.d(TAG, "onCompleted() called");
//            if (cityInfoList == null){
//                listFragment.setEmptyText(getText(R.string.error));
//                listFragment.setListAdapter(new CityInfoListAdapter(new ArrayList<CityInfo>(0), MainActivity.this));
//            }else {
//                PrjectUtils.sortList(cityInfoList, location);
//                listFragment.setListAdapter(new CityInfoListAdapter(cityInfoList, MainActivity.this));
//            }
//
//            dismissWaitingDialog();
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            listFragment.setEmptyText(getText(R.string.error));
//            listFragment.setListAdapter(new CityInfoListAdapter(new ArrayList<CityInfo>(0), MainActivity.this));
//            dismissWaitingDialog();
//            Log.d(TAG, "onError() called with: e = [" + e + "]");
//        }
//
//        @Override
//        public void onNext(FindResponse findResponse) {
//            Log.d(TAG, "onNext() called with: findResponse = [" + findResponse + "]");
//            if (!findResponse.getCityInfo().isEmpty()) {
//                cityInfoList.add(PrjectUtils.getSortedCityInfoList(findResponse.getCityInfo(), location).get(0));
//            }
//        }
//    };
}
