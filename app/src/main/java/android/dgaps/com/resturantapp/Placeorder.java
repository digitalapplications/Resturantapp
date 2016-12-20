package android.dgaps.com.resturantapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 12/19/2016.
 */

public class Placeorder extends Activity {
    List<Integer> item_idlist;
    List<Integer> quantity_list;
    List<String> item_namelist;
    List<Integer> pricelist;

    EditText et_name,et_address,et_phone,et_email;
    Button btn_placeorder;

    Dialog dialog;


    int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        item_idlist = new ArrayList<>();
        quantity_list = new ArrayList<>();
        item_namelist = new ArrayList<>();
        pricelist = new ArrayList<>();

        try {

            String hotel_id = getIntent().getStringExtra("hotel_id");
            Log.d("hotel_id", hotel_id);

            pricelist = getIntent().getIntegerArrayListExtra("pricelist");
            Log.d("pricelist", pricelist.toString());

            for(int i =0;i<pricelist.size();i++){
                sum +=pricelist.get(i);
            }

            item_namelist = getIntent().getStringArrayListExtra("item_namelist");
            Log.d("zmaname_list", item_namelist.toString());

            item_idlist = getIntent().getIntegerArrayListExtra("item_idlist");
            Log.d("item_idlist", item_idlist.toString());

            quantity_list = getIntent().getIntegerArrayListExtra("quantity_list");
            Log.d("quantity_list", quantity_list.toString());


        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        JSONArray array = new JSONArray();

        for(int i =0;i<item_idlist.size();i++){

            JSONObject obj = new JSONObject();
            try {
                obj.put("id",item_idlist.get(i)+"");
                obj.put("qty",quantity_list.get(i)+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            array.put(obj);
        }

        final JSONObject mainobj = new JSONObject();
        try {
            mainobj.put("items",array);
            mainobj.put("rest_id",Items.hotel_id);




        } catch (JSONException e) {
            e.printStackTrace();
        }




        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_placeorder = (Button) findViewById(R.id.btn_placeorder);

        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_name.getText().toString().equals("") || et_phone.getText().toString().equals("") || et_address.getText().toString().equals("") ||et_email.getText().toString().equals("")){
                    Toast.makeText(Placeorder.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        mainobj.put("full_name",et_name.getText().toString());
                        mainobj.put("phone",et_phone.getText().toString());
                        mainobj.put("address",et_address.getText().toString());
                        mainobj.put("email",et_email.getText().toString());

                        Log.d("mainobj",mainobj.toString());

                        dialog = new Dialog(Placeorder.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.confirmation_customdialog);
                        ListView listView = (ListView) dialog.findViewById(R.id.confirmation_listview);
                       myAdapter adapter = new myAdapter(Placeorder.this,android.R.layout.simple_list_item_1,quantity_list,item_namelist,pricelist);
                        listView.setAdapter(adapter);

                        Button btn_confirm = (Button) dialog.findViewById(R.id.btn_confirm);
                        btn_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                backgroundTask task = new backgroundTask();
                                task.execute(mainobj.toString());

                            }
                        });

                        Button btn_dismiss = (Button) dialog.findViewById(R.id.btn_dismiss);
                        btn_dismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public class backgroundTask extends AsyncTask<String,Void,String>{

        String placeorder_url = "http://iesco.enjoyboss.com/place-order";
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Placeorder.this);
            pd.setMessage("Placing order.....");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {

            String item = params[0];
            try {
                URL url = new URL(placeorder_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("obj","UTF-8")+"="+URLEncoder.encode(item,"UTF-8");
                bw.write(data);
                bw.flush();
                bw.close();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                is.close();
                return "order place successfully....";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            Toast.makeText(Placeorder.this, s, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
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