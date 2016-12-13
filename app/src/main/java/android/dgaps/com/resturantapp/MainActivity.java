package android.dgaps.com.resturantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_cityname;
    Button btn_ok;
    ListView lv_resturants;

    String [] hotel_arr = new String[]{"Bao Jee","Biryan","Karachi Biryani"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_cityname = (EditText) findViewById(R.id.et_cityname);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        lv_resturants = (ListView) findViewById(R.id.lv_resturants);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_cityname.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Enter City name", Toast.LENGTH_SHORT).show();
                }
                else{
                    String city = et_cityname.getText().toString();
                    city = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
                    Toast.makeText(MainActivity.this,city, Toast.LENGTH_SHORT).show();

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,hotel_arr);
                    lv_resturants.setAdapter(adapter);
                }

            }
        });

        lv_resturants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = hotel_arr[i];
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,Items.class);
                startActivity(intent);
            }
        });

    }
}
