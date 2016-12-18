package android.dgaps.com.resturantapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Items extends Activity {

    private ExpandableListView listView;
    private ExpendiblelistviewAdapter listAdapter;
    private List<String> dataHeader;
    private HashMap<String,List<String>>listHash;
    private HashMap<String,String> customMap;
    private HashMap<String,String> priceMap;

    List<String> name;

    TextView company_name;
    ImageView company_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        customMap = new HashMap<>();
        priceMap = new HashMap<>();


        String h_id =   getIntent().getStringExtra("h_id");
        String h_name = getIntent().getStringExtra("h_name");

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        Toast.makeText(this,h_id, Toast.LENGTH_SHORT).show();

        company_name = (TextView) findViewById(R.id.company_name);
        company_name.setText(h_name);

        company_icon = (ImageView) findViewById(R.id.company_icon);
        company_icon.setImageBitmap(bmp);


        listView = (ExpandableListView) findViewById(R.id.expanded_listview);
        listView.setDividerHeight(10);
        //initData();

        requestData("http://iesco.enjoyboss.com/get-rest-menu?rest_id=1");



    }

    private void initData() {



        List<String> starter = new ArrayList<>();
        starter.add("Chicken Soup");
        starter.add("Chicken Chowmein");
        starter.add("Beef Noodles Gaucmole");

        List<String> main = new ArrayList<>();
        main.add("The Noodles Special Sauce");
        main.add("The prawn Noodle Bowl");
        main.add("Honey Chicken With Fried Rice");
        main.add("Avacado Beef Chilli and Noodles");

        customMap.put(starter.get(0),"served with salad, sauce and bread");
        customMap.put(starter.get(1),"served with salad, sauce, bread and coke");
        customMap.put(starter.get(2),"served with chips and sauce");
        customMap.put(main.get(0),"served with all salad, sauce and bread");
        customMap.put(main.get(1),"served with all salad, sauce, bread and coke");
        customMap.put(main.get(2),"served with chips and sauce");
        customMap.put(main.get(3),"served with chips and sauce with free coke");

        priceMap.put(starter.get(0),"12$");
        priceMap.put(starter.get(1),"10$");
        priceMap.put(starter.get(2),"8$");
        priceMap.put(main.get(0),"14$");
        priceMap.put(main.get(1),"20$");
        priceMap.put(main.get(2),"18$");
        priceMap.put(main.get(3),"11$");


        listHash.put(dataHeader.get(0),starter);
        listHash.put(dataHeader.get(1),main);
    }



    private void requestData(String uri) {
        StringRequest request = new StringRequest(uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("menu", s);

                try {
                    JSONObject parent = new JSONObject(s);
                    JSONArray data = parent.getJSONArray("data");
                    for(int i =0;i< data.length();i++){
                        JSONObject obj = data.getJSONObject(i);
                        JSONObject obj_header = obj.getJSONObject("menu");
                        String menu_name = obj_header.getString("name");
                        dataHeader.add(menu_name);

                        JSONArray sub_items = obj.getJSONArray("data");
                        name = new ArrayList<>();
                        for(int j = 0;j<sub_items.length();j++){

                            JSONObject item = sub_items.getJSONObject(j);

                            String item_name = item.getString("name");
                            Log.d("item_name",item_name);
                            name.add(item_name);

                            String price = item.getString("price");
                            Log.d("price",price);
                            priceMap.put(name.get(j),price);



                            listHash.put(dataHeader.get(j),name);

                            Log.d("listhash",listHash.size()+"");
                            Log.d("price",priceMap.size()+"");
                            Log.d("name",name.size()+"");

                        }
                    }


                    listAdapter = new ExpendiblelistviewAdapter(Items.this,dataHeader,listHash,priceMap);
                    listView.setAdapter(listAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (IndexOutOfBoundsException ex){
                    ex.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }


}
