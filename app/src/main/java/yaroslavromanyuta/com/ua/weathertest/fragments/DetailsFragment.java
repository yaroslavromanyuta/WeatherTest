package yaroslavromanyuta.com.ua.weathertest.fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.application.WeatherApp;
import yaroslavromanyuta.com.ua.weathertest.entetiesbd.CityInfoDB;

import static yaroslavromanyuta.com.ua.weathertest.ProjectConstants.ARGUMENT;

@EFragment(R.layout.details_fragment)
public class DetailsFragment extends BaseFragment  {

    @ViewById(R.id.txt_city_name) TextView txtCityName;
    @ViewById(R.id.img_icon) ImageView imgIcon;
    @ViewById(R.id.txt_temp) TextView txtTemp;
    @ViewById(R.id.txt_tmp_max) TextView txtTempMax;
    @ViewById(R.id.txt_temp_min) TextView txtTempMin;
    @ViewById(R.id.txt_pressure) TextView txtPressure;
    @ViewById(R.id.txt_humidity) TextView txtHumidity;
    @ViewById(R.id.txt_wind) TextView txtWind;
    @ViewById(R.id.txt_description) TextView txtDescription;

    private CityInfoDB cityInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cityInfo = ((WeatherApp) getActivity().getApplication()).getDaoSession().getCityInfoDBDao()
                .load(getArguments().getLong(ARGUMENT));
    }

    @AfterViews
    protected void initFragmentViews() {

        txtCityName.setText(cityInfo.getName());
        txtDescription.setText(cityInfo.getWeatherDescription());
        txtTemp.setText(String.valueOf(cityInfo.getTemp()));
        txtTempMin.setText(String.valueOf(cityInfo.getTempMin()));
        txtTempMax.setText(String.valueOf(cityInfo.getTempMax()));
        txtHumidity.setText(String.valueOf(cityInfo.getHumidity()));
        txtPressure.setText(String.valueOf(cityInfo.getPressure()));
        txtWind.setText(String.valueOf(cityInfo.getWindSpeed()));
        Picasso.with(getActivity().getBaseContext())
                .load(getString(R.string.icon_url, cityInfo.getWeatherIcon()))
                .into(imgIcon);
    }

    public static DetailsFragment newInstanse (long id){
        DetailsFragment detailsFragment = new DetailsFragment_();
        Bundle args = new Bundle();
        args.putLong(ARGUMENT, id);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

}
