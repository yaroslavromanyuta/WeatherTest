package yaroslavromanyuta.com.ua.weathertest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import yaroslavromanyuta.com.ua.weathertest.R;
import yaroslavromanyuta.com.ua.weathertest.entities.CityInfo;

/**
 * Created by Yaroslav on 10.08.2016.
 */
public class CityInfoListAdapter extends BaseAdapter {

    Context context;
    ArrayList<CityInfo> cityInfos;

    public CityInfoListAdapter(ArrayList<CityInfo> cityInfos, Context context) {
        this.cityInfos = cityInfos;
        this.context = context;
    }

    static class ViewHolder{
        protected TextView name;
        protected ImageView icon;
        protected TextView temp;
    }


    @Override
    public int getCount() {
        return cityInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return cityInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.city_info_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
            viewHolder.name = (TextView) view.findViewById(R.id.txt_city_name);
            viewHolder.temp = (TextView) view.findViewById(R.id.txt_temp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        CityInfo cityInfo = (CityInfo) getItem(i);
        viewHolder.temp.setText(String.valueOf(cityInfo.getMain().getTemp()));
        viewHolder.name.setText(cityInfo.getName());
        Picasso.with(context)
                .load(context.getString(R.string.icon_url)+cityInfo.getWeather().get(0).getIcon()+".png")
                .into(viewHolder.icon);

        return view;
    }
}
