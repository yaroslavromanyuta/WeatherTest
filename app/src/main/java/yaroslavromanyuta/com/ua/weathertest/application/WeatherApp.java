package yaroslavromanyuta.com.ua.weathertest.application;


import android.app.Application;

import org.greenrobot.greendao.database.Database;

import yaroslavromanyuta.com.ua.weathertest.entetiesbd.DaoMaster;
import yaroslavromanyuta.com.ua.weathertest.entetiesbd.DaoSession;

/**
 * Created by Yaroslav on 14.10.2016.
 */

public class WeatherApp extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weather-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
