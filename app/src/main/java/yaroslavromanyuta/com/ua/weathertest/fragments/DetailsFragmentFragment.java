package yaroslavromanyuta.com.ua.weathertest.fragments;

import android.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;

import static  yaroslavromanyuta.com.ua.weathertest.ProjectConstants.*;

public class DetailsFragmentFragment extends Fragment  {

    @BindView(R.id.txt_city_name) TextView txtCityName;
    @BindView(R.id.img_icon) ImageView imgIcon;
    @BindView(R.id.txt_temp) TextView txtTemp;
    @BindView(R.id.txt_tmp_max) TextView txtTempMax;
    @BindView(R.id.txt_temp_min) TextView txtTempMin;
    @BindView(R.id.txt_pressure) TextView txtPressure;
    @BindView(R.id.txt_humidity) TextView txtHumidity;
    @BindView(R.id.txt_wind) TextView txtWind;

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
        ButterKnife.bind(this, view);

        txtCityName.setText(cityInfo.getName());
        txtTemp.setText(String.valueOf(cityInfo.getMain().getTemp()));
        txtTempMin.setText(String.valueOf(cityInfo.getMain().getTempMin()));
        txtTempMax.setText(String.valueOf(cityInfo.getMain().getTempMax()));
        txtHumidity.setText(String.valueOf(cityInfo.getMain().getHumidity()));
        txtPressure.setText(String.valueOf(cityInfo.getMain().getPressure()));
        txtWind.setText(String.valueOf(cityInfo.getWind().getSpeed()));
        Picasso.with(getActivity().getBaseContext())
                .load("http://openweathermap.org/img/w/"+cityInfo.getWeather().get(0).getIcon()+".png")
                .into(imgIcon);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static DetailsFragmentFragment newInstanse (String json){
        DetailsFragmentFragment detailsFragmentFragment = new DetailsFragmentFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT, json);
        detailsFragmentFragment.setArguments(args);
        return detailsFragmentFragment;
    }

}
