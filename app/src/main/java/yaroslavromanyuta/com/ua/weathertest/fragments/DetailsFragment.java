package yaroslavromanyuta.com.ua.weathertest.fragments;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yaroslavromanyuta.com.ua.weathertest.MainActivity;
import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;

import static  yaroslavromanyuta.com.ua.weathertest.ProjectConstants.*;

@EFragment(R.layout.details_fragment)
public class DetailsFragment extends Fragment  {

    @ViewById(R.id.txt_city_name) TextView txtCityName;
    @ViewById(R.id.img_icon) ImageView imgIcon;
    @ViewById(R.id.txt_temp) TextView txtTemp;
    @ViewById(R.id.txt_tmp_max) TextView txtTempMax;
    @ViewById(R.id.txt_temp_min) TextView txtTempMin;
    @ViewById(R.id.txt_pressure) TextView txtPressure;
    @ViewById(R.id.txt_humidity) TextView txtHumidity;
    @ViewById(R.id.txt_wind) TextView txtWind;
    @ViewById(R.id.txt_description) TextView txtDescription;

    CityInfo cityInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cityInfo = new Gson().fromJson(getArguments().getString(ARGUMENT), CityInfo.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, null);

        cityInfo = new Gson().fromJson(getArguments().getString(ARGUMENT), CityInfo.class);

/*        txtCityName.setText(cityInfo.getName());
        txtDescription.setText(cityInfo.getWeather().get(0).getDescription());
        txtTemp.setText(String.valueOf(cityInfo.getMain().getTemp()));
        txtTempMin.setText(String.valueOf(cityInfo.getMain().getTempMin()));
        txtTempMax.setText(String.valueOf(cityInfo.getMain().getTempMax()));
        txtHumidity.setText(String.valueOf(cityInfo.getMain().getHumidity()));
        txtPressure.setText(String.valueOf(cityInfo.getMain().getPressure()));
        txtWind.setText(String.valueOf(cityInfo.getWind().getSpeed()));
        Picasso.with(getActivity().getBaseContext())
                .load(getString(R.string.icon_url)+cityInfo.getWeather().get(0).getIcon()+".png")
                .into(imgIcon);

        return view;
*/
        return null;
    }

    @AfterViews
    void afterViews(){

        txtCityName.setText(cityInfo.getName());
        txtDescription.setText(cityInfo.getWeather().get(0).getDescription());
        txtTemp.setText(String.valueOf(cityInfo.getMain().getTemp()));
        txtTempMin.setText(String.valueOf(cityInfo.getMain().getTempMin()));
        txtTempMax.setText(String.valueOf(cityInfo.getMain().getTempMax()));
        txtHumidity.setText(String.valueOf(cityInfo.getMain().getHumidity()));
        txtPressure.setText(String.valueOf(cityInfo.getMain().getPressure()));
        txtWind.setText(String.valueOf(cityInfo.getWind().getSpeed()));
        Picasso.with(getActivity().getBaseContext())
                .load(getString(R.string.icon_url)+cityInfo.getWeather().get(0).getIcon()+".png")
                .into(imgIcon);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static DetailsFragment newInstanse (String json){
        DetailsFragment detailsFragment = new DetailsFragment_();
        Bundle args = new Bundle();
        args.putString(ARGUMENT, json);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

}
