package android.dgaps.com.resturantapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by HP on 12/13/2016.
 */

public class customAdapter extends ArrayAdapter{

    String [] names = new String[]{"Item 1","Item 2","Item 3"};
    Context context;
    String [] price = new String[]{"100/item","200/item","400/item"};

    public customAdapter(Context context, int resource, String names[]) {
        super(context, resource, names);

        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.customlayout, parent, false);
        TextView tvNames = (TextView) view.findViewById(R.id.custom_name);
        tvNames.setText(names[position]);

        TextView tvprice = (TextView) view.findViewById(R.id.custom_price);
        tvprice.setText(price[position]);

        ToggleButton tb = (ToggleButton) view.findViewById(R.id.custom_button);


        return view;
    }
}
