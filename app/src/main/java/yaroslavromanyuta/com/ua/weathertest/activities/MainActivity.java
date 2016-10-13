package yaroslavromanyuta.com.ua.weathertest.activities;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.Permission;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yaroslavromanyuta.com.ua.weathertest.PrjectUtils;
import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.apiclient.ObservableCreator;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.CityResponse;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.FindResponse;
import yaroslavromanyuta.com.ua.weathertest.fragments.CityInfoListFragment;
import yaroslavromanyuta.com.ua.weathertest.fragments.DetailsFragment;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.KEY_CITY_INFO_ARRAY;
import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

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
        changeFragment(R.id.container, listFragment, false);
        requestPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    protected void catchPermissionResult(Permission permission) {
        switch (permission.name) {
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (!permission.granted){
                    showAlertDialog(getString(R.string.allert_dialog_no_location_permission),
                            (dialog, which) -> update(false));
                } else {
                    update(true);
                }
                showWaitingDialog(getString(R.string.wait_dialog_message), false);
                break;
        }
    }

    void update(boolean isPermissionGranted){
        Log.d(TAG, "update() called with: isPermissionGranted = [" + isPermissionGranted + "]");
        if (isPermissionGranted) {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d(TAG, "update() called with: location = " + location);
        }

        ObservableCreator observableCreator = new ObservableCreator(this);
        observableCreator.createCityObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(CityResponse::getCities)
                .flatMap(Observable::from)
                .flatMap(observableCreator::createFindWeatherObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(findResponse -> (findResponse.getCount()>0))
                .subscribe(responseSubscriber);
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

    private Subscriber<FindResponse> responseSubscriber = new Subscriber<FindResponse>() {
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

            dismissWaitingDialog();
        }

        @Override
        public void onError(Throwable e) {
            listFragment.setEmptyText(getText(R.string.error));
            listFragment.setListAdapter(new CityInfoListAdapter(new ArrayList<CityInfo>(0), MainActivity.this));
            dismissWaitingDialog();
            Log.d(TAG, "onError() called with: e = [" + e + "]");
        }

        @Override
        public void onNext(FindResponse findResponse) {
            Log.d(TAG, "onNext() called with: findResponse = [" + findResponse + "]");
            if (!findResponse.getCityInfo().isEmpty()) {
                cityInfoList.add(PrjectUtils.getSortedCityInfoList(findResponse.getCityInfo(), location).get(0));
            }
        }
    };
}
