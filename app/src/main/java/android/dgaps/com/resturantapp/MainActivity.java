package android.dgaps.com.resturantapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

//searchby: {city_name, rest_name, rest_category}
//http://iesco.enjoyboss.com/get-list-by?keyword=rawalpindi&searchby=city_name

    Button btn_findfood;
    EditText et_city;


    String [] arr_category = new String[]{"City name","Resturant name","Resturant Category"};
    String [] arr = new String[] {"city_name","rest_name","rest_category"};

    Spinner spinner_category;

    String cityname;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_city = (EditText) findViewById(R.id.et_city);

        spinner_category = (Spinner) findViewById(R.id.spinner_category);

        btn_findfood = (Button) findViewById(R.id.btn_findfood);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,arr_category);
        spinner_category.setAdapter(adapter);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = arr[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_findfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_city.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    cityname = et_city.getText().toString();
                    cityname = cityname.substring(0, 1).toUpperCase() + cityname.substring(1);

                    Intent intent = new Intent(MainActivity.this,Resturants.class);
                    intent.putExtra("cityname",cityname);
                    intent.putExtra("category",category);
                    startActivity(intent);

                }

            }
        });


    }




}
