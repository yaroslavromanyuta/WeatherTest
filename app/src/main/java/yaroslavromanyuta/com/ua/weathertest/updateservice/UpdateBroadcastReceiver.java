package yaroslavromanyuta.com.ua.weathertest.updateservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;

/**
 * Created by Yaroslav on 14.10.2016.
 */

public class UpdateBroadcastReceiver extends BroadcastReceiver {

    public static final String BROADCAST_UPDATE_ACTION = "ACTION_DATABASE_UPDATED";

    private Observable observable;

    public static Intent getLaunchIntent(){
        Intent intent = new Intent(BROADCAST_UPDATE_ACTION);
        return intent;
    }

    public UpdateBroadcastReceiver(Observer observer) {
        super();
        observable = new Observable(){
            @Override
            public void notifyObservers(Object arg) {
                setChanged();
                super.notifyObservers(arg);
            }
        };
        observable.addObserver(observer);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        observable.notifyObservers();
    }

}
