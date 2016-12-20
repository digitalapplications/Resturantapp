package android.dgaps.com.resturantapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 12/14/2016.
 */

public class ExpendiblelistviewAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private HashMap<String,String> pricemap;
    private  HashMap<String,String> idmap;


    List<Integer> item_idlist;
    List<Integer> quantity_list;
    List<Integer> pricelist;
    List<String> item_namelist;





    public ExpendiblelistviewAdapter(Context ctx,List<String>listDataHeader,HashMap<String, List<String>> listHashMap,HashMap<String,String>pricemap,HashMap<String,String> idmap) {
        this.ctx = ctx;
        this.listHashMap = listHashMap;
        this.listDataHeader = listDataHeader;
        this.pricemap = pricemap;
        this.idmap = idmap;

        item_idlist = new ArrayList<>();
        quantity_list = new ArrayList<>();
        item_namelist = new ArrayList<>();
        pricelist = new ArrayList<>();

    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String headerTitle = (String) getGroup(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_group,null);
        }

        TextView tvheader = (TextView) view.findViewById(R.id.listheader);
        tvheader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {

        final String childtext = (String) getChild(i,i1);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_items,null);
        }

        TextView tvname = (TextView) view.findViewById(R.id.tv_name);
        tvname.setText(childtext);

        TextView tvdescription = (TextView) view.findViewById(R.id.tv_description);


        final TextView tvprice = (TextView) view.findViewById(R.id.tv_price);

        for(String str : pricemap.keySet()){
            tvprice.setText(pricemap.get(childtext));
        }

        ToggleButton tb_addtocart = (ToggleButton) view.findViewById(R.id.tb_addtocart);
        tb_addtocart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    final Dialog dialog = new Dialog(ctx);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.customdialog);

                    final EditText et_quantity = (EditText) dialog.findViewById(R.id.et_quantity);

                    Button btn_done = (Button) dialog.findViewById(R.id.btn_done);
                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int sum = 0;
                            if(et_quantity.getText().toString().equals("")){
                                Toast.makeText(ctx, "Please Enter a value", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                int quantity = Integer.parseInt(et_quantity.getText().toString());
                                int price = Integer.parseInt(tvprice.getText().toString());

                                int total = quantity*price;
                                dialog.dismiss();
                                Toast.makeText(ctx, quantity+" price = "+total, Toast.LENGTH_SHORT).show();

                                quantity_list.add(quantity);
                                item_idlist.add(Integer.parseInt(idmap.get(childtext)));
                                item_namelist.add(childtext);
                                pricelist.add(total);

                                for(int i = 0; i<pricelist.size(); i++){
                                   sum =+pricelist.get(i);
                                }




                            }
                        }
                    });

                    Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }

                else if (!b){
                    for(int k =0;k<item_namelist.size();k++){

                        String removed = item_namelist.get(k);
                        int index = k;
                        if(removed.equals(childtext)){
                            quantity_list.remove(k);
                            item_namelist.remove(k);
                            item_idlist.remove(k);
                            pricelist.remove(k);
                        }


                    }
                }
            }
        });


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }



    public  void sendData(){

        if(item_namelist.size()>0){

          //  Log.d("zmasize",quantity_list.size()+"  "+ item_idlist.size()+"  "+item_namelist.size()+"  "+pricelist.size());

            Intent intent = new Intent(ctx,ShowOrder.class);
            intent.putExtra("hotel_id",Items.hotel_id);

            intent.putIntegerArrayListExtra("pricelist", (ArrayList<Integer>) pricelist);
            intent.putStringArrayListExtra("item_namelist", (ArrayList<String>) item_namelist);
            intent.putIntegerArrayListExtra("quantity_list", (ArrayList<Integer>) quantity_list);
            intent.putIntegerArrayListExtra("item_idlist", (ArrayList<Integer>) item_idlist);
            ctx.startActivity(intent);
        }
        else{
            Toast.makeText(ctx, "Please select some item", Toast.LENGTH_SHORT).show();
        }

    }
}
