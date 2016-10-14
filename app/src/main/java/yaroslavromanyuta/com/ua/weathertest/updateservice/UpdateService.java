package yaroslavromanyuta.com.ua.weathertest.updateservice;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import yaroslavromanyuta.com.ua.weathertest.PrjectUtils;
import yaroslavromanyuta.com.ua.weathertest.apiclient.ObservableCreator;
import yaroslavromanyuta.com.ua.weathertest.application.WeatherApp;
import yaroslavromanyuta.com.ua.weathertest.entetiesbd.DaoSession;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.CityInfo;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.CityResponse;
import yaroslavromanyuta.com.ua.weathertest.entitiyModels.FindResponse;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

/**
 * Created by Yaroslav on 14.10.2016.
 */

public class UpdateService extends Service{

    public static final String KEY_INTENT_LOCATION = "KEY_LOCATION";
    public static final String ACTION_START_UPDATE = "START_UPDATE";

    private DaoSession daoSession;
    private List<CityInfo> cityInfoList;
    private Location location;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static Intent getLaunchIntent(@Nullable Location location){

        Intent intent = new Intent(ACTION_START_UPDATE);

        if (location != null) {
            intent.putExtra(KEY_INTENT_LOCATION, location);
        }

        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        daoSession = ((WeatherApp) getApplication()).getDaoSession();
        location = intent.getParcelableExtra(KEY_INTENT_LOCATION);

        update();

        return super.onStartCommand(intent, flags, startId);
    }

    void update(){
        Log.d(TAG, "update() called");
        ObservableCreator observableCreator = new ObservableCreator(this);
        Subscription subscription = observableCreator.createCityObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(CityResponse::getCities)
                .flatMap(Observable::from)
                .flatMap(observableCreator::createFindWeatherObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(findResponse -> (findResponse.getCount()>0))
                .subscribe(responseSubscriber);
        compositeSubscription.add(subscription);
    }



    private Subscriber<FindResponse> responseSubscriber = new Subscriber<FindResponse>() {
        @Override
        public void onCompleted() {

            Log.d(TAG, "onCompleted() called");

            DatabaseUpdater.updateDatabase(cityInfoList, daoSession);

            stopSelf();
        }

        @Override
        public void onError(Throwable e) {
            stopSelf();
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
