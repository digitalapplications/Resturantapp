package android.dgaps.com.resturantapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity {

    EditText et_username,et_useraddress,et_phone;
    Button btn_placeorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        et_username = (EditText) findViewById(R.id.et_username);
        et_useraddress = (EditText) findViewById(R.id.et_useraddress);
        et_phone = (EditText) findViewById(R.id.et_userphone);

        btn_placeorder = (Button) findViewById(R.id.btn_placeorder);

        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = et_username.getText().toString();
                String address = et_useraddress.getText().toString();
                String phone = et_phone.getText().toString();

                if(username.equals("") || address.equals("") || phone.equals("")){
                    Toast.makeText(UserInfo.this, "Please fill all the required field", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UserInfo.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
