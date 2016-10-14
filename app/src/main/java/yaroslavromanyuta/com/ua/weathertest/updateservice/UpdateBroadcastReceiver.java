package yaroslavromanyuta.com.ua.weathertest.updateservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Yaroslav on 14.10.2016.
 */

public class UpdateBroadcastReceiver extends BroadcastReceiver {

    private Observable observable;

    public UpdateBroadcastReceiver(Observer observer) {
        super();

        observable.addObserver(observer);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

}
