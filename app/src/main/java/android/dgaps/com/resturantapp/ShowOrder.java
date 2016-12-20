package android.dgaps.com.resturantapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class ShowOrder extends Activity {


     List<Integer> item_idlist;
     List<Integer> quantity_list;
     List<String> item_namelist;
     List<Integer> pricelist;

    TextView tv_totalamount;
    ListView lv_items;
    Button btn_order;

    int sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showorder);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        item_idlist = new ArrayList<>();
        quantity_list = new ArrayList<>();
        item_namelist = new ArrayList<>();
        pricelist = new ArrayList<>();

        try {

            String hotel_id = getIntent().getStringExtra("hotel_id");
            //Log.d("hotel_id", hotel_id);

            pricelist = getIntent().getIntegerArrayListExtra("pricelist");
            //Log.d("pricelist", pricelist.toString());

            for(int i =0;i<pricelist.size();i++){
                sum +=pricelist.get(i);
            }

            item_namelist = getIntent().getStringArrayListExtra("item_namelist");
            //Log.d("zmaname_list", item_namelist.toString());

            item_idlist = getIntent().getIntegerArrayListExtra("item_idlist");
            //Log.d("item_idlist", item_idlist.toString());

            quantity_list = getIntent().getIntegerArrayListExtra("quantity_list");
            //Log.d("quantity_list", quantity_list.toString());


        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        tv_totalamount = (TextView) findViewById(R.id.totalamount);
        tv_totalamount.setText("Total : "+sum+"/PKR");

        btn_order = (Button) findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Log.d("zmasize",quantity_list.size()+"  "+ item_idlist.size()+"  "+item_namelist.size()+"  "+pricelist.size());

                    Intent intent = new Intent(ShowOrder.this,Placeorder.class);
                    intent.putExtra("hotel_id",Items.hotel_id);

                    intent.putIntegerArrayListExtra("pricelist", (ArrayList<Integer>) pricelist);
                    intent.putStringArrayListExtra("item_namelist", (ArrayList<String>) item_namelist);
                    intent.putIntegerArrayListExtra("quantity_list", (ArrayList<Integer>) quantity_list);
                    intent.putIntegerArrayListExtra("item_idlist", (ArrayList<Integer>) item_idlist);
                    startActivity(intent);


            }
        });

        lv_items = (ListView) findViewById(R.id.lvItems);
        myAdapter adapter = new myAdapter(this,android.R.layout.simple_list_item_1,quantity_list,item_namelist,pricelist);
        lv_items.setAdapter(adapter);

    }


    public class myAdapter extends ArrayAdapter{

        List<Integer> item_idlist;
        List<Integer> quantity_list;
        List<String> item_namelist;
        List<Integer> pricelist;
        Context context;

        public myAdapter(Context context, int resource, List<Integer> quantity_list,List<String> item_namelist, List<Integer> pricelist ) {
            super(context, resource,item_namelist);

            this.context = context;
            this.quantity_list = quantity_list;
            this.item_namelist = item_namelist;
            this.pricelist = pricelist;
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.customlistviewadapter,null);

            TextView custom_quantity = (TextView) view.findViewById(R.id.custom_quantity);
            custom_quantity.setText(quantity_list.get(position)+"");

            TextView custom_name = (TextView) view.findViewById(R.id.custom_name);
            custom_name.setText(item_namelist.get(position));

            TextView custom_price = (TextView) view.findViewById(R.id.custom_price);
            custom_price.setText(pricelist.get(position)+"");



            return view;

        }
    }
}
