package android.dgaps.com.resturantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Items extends AppCompatActivity {

    String [] names = new String[]{"Item 1","Item 2","Item 3"};
    ListView lv_menuitem;

    Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        lv_menuitem = (ListView) findViewById(R.id.lv_menuitems);
        customAdapter adapter = new customAdapter(this,android.R.layout.simple_list_item_1,names);
        lv_menuitem.setAdapter(adapter);


        btn_checkout = (Button) findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Items.this, UserInfo.class);
                startActivity(intent);
            }
        });

    }
}
