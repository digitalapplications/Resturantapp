package android.dgaps.com.resturantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 12/14/2016.
 */

public class ExpendiblelistviewAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private HashMap<String,String> customMap;
    private HashMap<String,String> pricemap;


    public ExpendiblelistviewAdapter(Context ctx,List<String>listDataHeader,HashMap<String, List<String>> listHashMap,HashMap<String,String>pricemap) {
        this.ctx = ctx;
        this.listHashMap = listHashMap;
        this.listDataHeader = listDataHeader;
        this.pricemap = pricemap;
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
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        final String childtext = (String) getChild(i,i1);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_items,null);
        }

        TextView tvname = (TextView) view.findViewById(R.id.tv_name);
        tvname.setText(childtext);

        TextView tvdescription = (TextView) view.findViewById(R.id.tv_description);


        TextView tvprice = (TextView) view.findViewById(R.id.tv_price);

        for(String str : pricemap.keySet()){
            tvprice.setText(pricemap.get(childtext));
        }

        ToggleButton tb_addtocart = (ToggleButton) view.findViewById(R.id.tb_addtocart);


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
