package yaroslavromanyuta.com.ua.weathertest.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import yaroslavromanyuta.com.ua.weathertest.adapters.CityInfoListAdapter;
import yaroslavromanyuta.com.ua.weathertest.entetiesbd.CityInfoDB;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.TAG;
/**
 * Created by Yaroslav on 10.08.2016.
 */

public class CityInfoListFragment extends ListFragment {

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemSelected(long position);
    }

    public void setData(List<CityInfoDB> cityInfos){
        setListAdapter(new CityInfoListAdapter(cityInfos, getContext()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            onItemClickListener = (OnItemClickListener) getActivity();
        } catch (ClassCastException e ) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnItemClickListener");
        }
        Log.d(TAG, "onCreate() called ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onItemClickListener = (OnItemClickListener) getActivity();
        } catch (ClassCastException e ) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnItemClickListener");
        }

        Log.d(TAG, "onAttach() called" );
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        onItemClickListener.onItemSelected(id);
    }
}
