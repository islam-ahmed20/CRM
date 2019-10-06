package net.systemsstars.crm.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mac on 11/19/17.
 */

public class ShowToast extends Activity {
    public ShowToast(Context AC, String S)
    {
        Toast toast = Toast.makeText(AC,S, Toast.LENGTH_LONG);
        ViewGroup v=(ViewGroup) toast.getView();
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.parseColor("#039be5"));
        gd.setStroke(2, Color.parseColor("#FFFFFF"));
        gd.setCornerRadius(90.0f);
        v.setBackground(gd);
        TextView messageTextView = (TextView) v.getChildAt(0);
        messageTextView.setTextSize(14);
        messageTextView.setTextColor(Color.parseColor("#FFFFFF"));
        messageTextView.setGravity(Gravity.CENTER);
        messageTextView.setPadding(50,20,50,20);
        toast.show();
    }
}
