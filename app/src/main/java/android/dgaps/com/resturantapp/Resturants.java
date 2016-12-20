package android.dgaps.com.resturantapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Resturants extends AppCompatActivity {

    List<String> names;//{"The Noodle", "Submay", "Dunkin Donuts", "Just Food"};
    List<String> path;
    List<String> hotel_id;

    Integer[] imgid = new Integer[]{R.drawable.noodles, R.drawable.subway, R.drawable.dunkon};
    ListView lv;

    String getCityname;
    String getCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturants);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        names = new ArrayList<>();
        path = new ArrayList<>();
        hotel_id = new ArrayList<>();
        getCityname = getIntent().getStringExtra("cityname");
        getCategory = getIntent().getStringExtra("category");


        Log.d("parameter",getCityname+"  "+ getCategory);


        if(isOnline()){
            requestData("http://iesco.enjoyboss.com/get-list-by?keyword="+getCityname+"&searchby="+getCategory);
        }
        else{
            Toast.makeText(this, "Network Problem", Toast.LENGTH_SHORT).show();

        }

        Toast.makeText(this, getCityname, Toast.LENGTH_SHORT).show();
        lv = (ListView) findViewById(R.id.lv_resturants);
        lv.setDividerHeight(10);


    }


    private void requestData(String uri) {

        StringRequest request = new StringRequest(uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("response", s);

                try {
                    JSONObject parent = new JSONObject(s);
                    String result = parent.getString("result");

                     if(result.equals("success")){
                         JSONArray data = parent.getJSONArray("data");

                         for(int i = 0 ;i<data.length();i++){
                             JSONObject child = data.getJSONObject(i);

                             String str = child.getString("name");
                             String logo = child.getString("logo");
                             String id = child.getString("id");

                             Log.d("id",id);


                             names.add(str);
                             path.add(logo);
                             hotel_id.add(id);

                         }
                         customAdapter adapter = new customAdapter(Resturants.this, android.R.layout.simple_list_item_1, names, path,hotel_id);
                         lv.setAdapter(adapter);

                     }

                    else{
                         Toast.makeText(Resturants.this, "No hotel found", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(Resturants.this,MainActivity.class);
                         startActivity(intent);
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (ArrayIndexOutOfBoundsException ex){
                    ex.printStackTrace();
                }

                Log.d("size",names.size()+"");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }
}
