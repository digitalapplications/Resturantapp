package android.dgaps.com.resturantapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by HP on 12/13/2016.
 */

public class customAdapter extends ArrayAdapter {

    List<String> names;
    Context context;
    List<String> path;
    List<String> hotel_id;
    Bitmap bmp;


    private RequestQueue queue;

    public customAdapter(Context context, int resource, List<String> names, List<String> path,List<String> hotel_id) {
        super(context, resource, names);

        this.context = context;
        this.names = names;
        this.path = path;
        this.hotel_id = hotel_id;
        queue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.customlayout, parent, false);
        TextView tvNames = (TextView) view.findViewById(R.id.tv_companyname);
        tvNames.setText(names.get(position));
        final ImageView iv = (ImageView) view.findViewById(R.id.company_logo);
        try {

            ImageRequest request = new ImageRequest(path.get(position),
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap arg0) {
                            iv.setImageBitmap(arg0);

                        }
                    },
                    80, 80,
                    Bitmap.Config.ARGB_8888,

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError arg0) {
                            Log.d("FlowerAdapter", arg0.getMessage());
                        }
                    }
            );
            queue.add(request);

        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        Button btn = (Button) view.findViewById(R.id.menu_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Items.class);
                String h_id = hotel_id.get(position);
                String h_name = names.get(position);

                BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("image",byteArray);
                intent.putExtra("h_id",h_id);
                intent.putExtra("h_name",h_name);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
