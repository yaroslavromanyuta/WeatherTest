package yaroslavromanyuta.com.ua.weathertest.fragments;

import android.app.ListFragment;
import android.content.Context;

import java.util.ArrayList;

import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;

/**
 * Created by Yaroslav on 10.08.2016.
 */
public class CityInfoListFragment extends ListFragment {

    public void setData(ArrayList<CityInfo> cityInfos, Context context){
        setListAdapter(new CityInfoListAdapter(cityInfos, context));
    }
}
