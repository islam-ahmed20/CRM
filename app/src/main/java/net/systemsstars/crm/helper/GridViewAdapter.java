package net.systemsstars.crm.helper;

/**
 * Created by mac on 12/26/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.systemsstars.crm.R;
import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Projects in array
    ArrayList<String> Projects  = new ArrayList<>();
    
    int pos = 0;
    // Constructor
    public GridViewAdapter(Context c, ArrayList<String> projects){
        Projects = projects;
        mContext = c;
    }

    @Override
    public int getCount() {
        return Projects.size();
    }

    @Override
    public Object getItem(int position) {
        return Projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final TextView projectTV = new TextView(mContext);
        GridView.LayoutParams LP = new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT);
        //LP.setMargins(15,5,15,5);
        projectTV.setLayoutParams(LP);
        projectTV.setPadding(20, 10, 20, 10);
        projectTV.setBackground(mContext.getResources().getDrawable(R.drawable.round_btn_large));
        projectTV.setGravity(Gravity.CENTER);
        projectTV.setSingleLine();
        projectTV.setText(Projects.get(position));
        projectTV.setTextSize(12);
        projectTV.setTextColor(Color.parseColor("#FFFFFF"));

        return projectTV;
    }
}
